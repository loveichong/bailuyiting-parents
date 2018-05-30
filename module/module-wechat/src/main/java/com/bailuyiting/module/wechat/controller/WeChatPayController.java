package com.bailuyiting.module.wechat.controller;

import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.module.wechat.config.bean.WeChatPayPropertyBean;
import com.bailuyiting.module.wechat.entity.WeChatPayLog;
import com.bailuyiting.module.wechat.entity.WechatTradePagePayRequest;
import com.bailuyiting.module.wechat.entity.WxPaySendData;
import com.bailuyiting.module.wechat.inter.WeChatPayNotifyProcess;
import com.bailuyiting.module.wechat.service.WeChatPayLogService;
import com.bailuyiting.module.wechat.until.WeChatParameterUntils;
import com.bailuyiting.module.wechat.until.WeChatUtils;
import com.bailuyiting.module.wechat.until.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
/**
 * 微信支付组件
 */
@Component
public class WeChatPayController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WeChatPayLogService weChatPayLogService;
    @Autowired
    private WeChatPayPropertyBean weChatPayPropertyBean;

    /**
     *APP统一下预支付订单
     **/
    public Map<String,Object> unifiedOrder(WechatTradePagePayRequest payRequest) {
        //查看订单是否已经申请过支付
        WeChatPayLog log = this.weChatPayLogService.getBaseJapRepository().findByOrderId(payRequest.getTradeNo());
        if(log==null){
            //记录日志
            log = new WeChatPayLog();
            log.setCreateTime(DateUtils.formatNow());//记录申请支付时间
            log.setOrderId(payRequest.getTradeNo());//记录订单号
            log.setOrderPrice(payRequest.getNeedWechatFee());//记录申请支付金额
            log.setPayStatus(1);//设置状态.正在向微信申请支付
        }
       else if(log.getPayStatus()!=1){
            throw new RuntimeException("此订单不能申请支付，可能已经支付过，或者订单无效");
       }
        Map<String,Object> resultMap=null;
        WxPaySendData paySendData = new WxPaySendData();
        //构建微信支付请求参数集合
        paySendData.setAppId(this.weChatPayPropertyBean.getAppID());//appID
        paySendData.setMchId(this.weChatPayPropertyBean.getMchID());//商户Id
        paySendData.setNonceStr(WeChatUtils.getRandomStr(32));//随机字符串
        paySendData.setBody(payRequest.getBody());//请求体
        paySendData.setOutTradeNo(payRequest.getTradeNo());//订单ID 唯一
        paySendData.setSpBillCreateIp(this.weChatPayPropertyBean.getSpBillCreateIp()); //用户端实际ip
        paySendData.setNotifyUrl(this.weChatPayPropertyBean.getNotifyIp() + payRequest.getNotifyUrl());//回调地址
        paySendData.setTradeType(payRequest.getTradeType());//支付类型
        paySendData.setTotalFee(payRequest.getNeedWechatFee().multiply(new BigDecimal(100)).toBigInteger().intValue());//支付金额 注意 单位为分
        logger.info("payRequest:"+payRequest.toString());
        logger.info("用于签名参数:"+paySendData.toString());
        SortedMap<String,Object> params = buildParamMap(paySendData);
        String sign=WeChatUtils.getSign(params,this.weChatPayPropertyBean.getKey());
        logger.info("签名结果："+sign);
        paySendData.setSign(sign);
        //将请求参数对象转换成xml
        String reqXml = WeChatUtils.sendDataToXml(paySendData);
        logger.info("reqXml:"+reqXml);
        try {
            //发送请求
            byte[] xmlData = reqXml.getBytes();
            URL url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");//微信支付URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content_Type","text/xml");
            urlConnection.setRequestProperty("Content-length",String.valueOf(xmlData.length));
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.write(xmlData);
            outputStream.flush();
            outputStream.close();
            resultMap = WeChatUtils.parseXml(urlConnection.getInputStream());
            //申请微信支付成功 记录日志
            this.weChatPayLogService.getBaseJapRepository().save(log);
        } catch (Exception e) {
            //throw new ServiceException("微信支付统一下单异常",e);
            //申请微信支付失败 记录日志
            log.setPayStatus(4);//申请微信支付失败
            log.setFailureMessage(e.getMessage());//记录失败原因
            this.weChatPayLogService.getBaseJapRepository().save(log);
            //抛出错误
            throw new RuntimeException(e.getMessage());
        }
        return resultMap;
    }
    /**
     * 微信支付回调方法封装
     * 需要传入自定义回调处理器
     * @param request
     * @param response
     * @param process
     */
    public void finishPayment(HttpServletRequest request, HttpServletResponse response,WeChatPayNotifyProcess process) {
        try
        {
            Map<String, String> resultMap = new HashMap();
            InputStream inputStream = request.getInputStream();
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputStream);
            Element root = document.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext())
            {

                Element e = (Element)it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XMLUtil.getChildrenText(children);
                }
                resultMap.put(k, v);
            }
            logger.info("resultMap:" + resultMap.toString());
            inputStream.close();
            String returnCode = resultMap.get("return_code");
            if ("SUCCESS".equals(returnCode))
            {
                WeChatPayLog log = this.weChatPayLogService.getBaseJapRepository().findByOrderId(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
                if(log==null){
                    //给微信返回结果
                    String xml = setXml("FAIL", (String)resultMap.get("return_msg"));
                    response.getWriter().print(xml);
                    this.logger.info("无此订单号");
                    return;
                }
                //防止微信多次调用回调接口
                if(log.getPayStatus()==2){
                    String xml = setXml("SUCCESS", "OK");
                    response.getWriter().print(xml);
                    System.out.println("付款成功");
                    return;
                }
                log.setNotifyTime(DateUtils.formatNow());//回调时间
                log.setPayPrice(new BigDecimal(WeChatParameterUntils.getPayPriceWhenPayNotifySuccess(resultMap)).divide(new BigDecimal(100)));//支付金额 原单位分，除以100等于元
                log.setTransactionId(WeChatParameterUntils.getTransactionIdWhenPayNotifySuccess(resultMap));//商务号
                log.setSuccessMessage(WeChatParameterUntils.getResultCodeWhenPayNotify(resultMap));//结果信息
                log.setSuccessCode(WeChatParameterUntils.getResultCodeWhenPayNotify(resultMap));//结果代码
                log.setPayStatus(2);//支付成功
                this.weChatPayLogService.getBaseJapRepository().save(log);
                //支付成功扩展接口
                    process.success(resultMap);
               //给微信返回结果
                String xml = setXml("SUCCESS", "OK");
                response.getWriter().print(xml);
                System.out.println("付款成功");
            }
            else
            {
                //保存微信支付失败日志
                WeChatPayLog log = this.weChatPayLogService.getBaseJapRepository().findByOrderId(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
                if(log==null){
                    //给微信返回结果
                    String xml = setXml("FAIL", (String)resultMap.get("return_msg"));
                    response.getWriter().print(xml);
                    this.logger.info("无此订单号");
                    return;
                }
                //防止微信多次调用回调接口
                if(log.getPayStatus()==2){
                    String xml = setXml("SUCCESS", "OK");
                    response.getWriter().print(xml);
                    System.out.println("付款成功");
                    return;
                }
                if(log.getPayStatus()==3){
                    String xml = setXml("FAIL", (String)resultMap.get("return_msg"));
                    response.getWriter().print(xml);
                    return;
                }
                log.setNotifyTime(DateUtils.formatNow());//回调时间
                log.setFailureMessage(WeChatParameterUntils.getResultCodeWhenPayNotify(resultMap));//结果信息
                log.setFailureCode(WeChatParameterUntils.getResultCodeWhenPayNotify(resultMap));//结果代码
                log.setPayStatus(3);//支付失败
                this.weChatPayLogService.getBaseJapRepository().save(log);
                //自定义失败处理器
                process.failure(resultMap);
                //给微信返回结果
                String xml = setXml("fail", (String)resultMap.get("return_msg"));
                response.getWriter().print(xml);
                this.logger.info("付款失败");
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }
    /**
     * 构建统一下单参数map 用于生成签名
     * @param data
     * @return SortedMap<String,Object>
     */
    public  SortedMap<String,Object> buildParamMap(WxPaySendData data) {
        SortedMap<String,Object> paramters = new TreeMap<String, Object>();
        if (null != data){
            if (StringUtils.isNotEmpty(data.getAppId())){
                paramters.put("appid",data.getAppId());
            }
            if (StringUtils.isNotEmpty(data.getAttach())){
                paramters.put("attach",data.getAttach());
            }
            if (StringUtils.isNotEmpty(data.getBody())){
                paramters.put("body",data.getBody());
            }
            if (StringUtils.isNotEmpty(data.getDetail())){
                paramters.put("detail",data.getDetail());
            }
            if (StringUtils.isNotEmpty(data.getDeviceInfo())){
                paramters.put("device_info",data.getDeviceInfo());
            }
            if (StringUtils.isNotEmpty(data.getFeeType())){
                paramters.put("fee_type",data.getFeeType());
            }
            if (StringUtils.isNotEmpty(data.getGoodsTag())){
                paramters.put("goods_tag",data.getGoodsTag());
            }
            if (StringUtils.isNotEmpty(data.getLimitPay())){
                paramters.put("limit_pay",data.getLimitPay());
            }
            if (StringUtils.isNotEmpty(data.getMchId())){
                paramters.put("mch_id",data.getMchId());
            }
            if (StringUtils.isNotEmpty(data.getNonceStr())){
                paramters.put("nonce_str",data.getNonceStr());
            }
            if (StringUtils.isNotEmpty(data.getNotifyUrl())){
                paramters.put("notify_url",data.getNotifyUrl());
            }
            if (StringUtils.isNotEmpty(data.getOpenId())){
                paramters.put("openid",data.getOpenId());
            }
            if (StringUtils.isNotEmpty(data.getOutTradeNo())){
                paramters.put("out_trade_no",data.getOutTradeNo());
            }
            if (StringUtils.isNotEmpty(data.getSign())){
                paramters.put("sign",data.getSign());
            }
            if (StringUtils.isNotEmpty(data.getSpBillCreateIp())){
                paramters.put("spbill_create_ip",data.getSpBillCreateIp());
            }
            if (StringUtils.isNotEmpty(data.getTimeStart())){
                paramters.put("time_start",data.getTimeStart());
            }
            if (StringUtils.isNotEmpty(data.getTimeExpire())){
                paramters.put("time_expire",data.getTimeExpire());
            }
            if (StringUtils.isNotEmpty(data.getProductId())){
                paramters.put("product_id",data.getProductId());
            }
            if (data.getTotalFee()>0){
                paramters.put("total_fee",data.getTotalFee());
            }
            if (StringUtils.isNotEmpty(data.getTradeType())){
                paramters.put("trade_type",data.getTradeType());
            }
            //申请退款参数
            if (StringUtils.isNotEmpty(data.getTransactionId())){
                paramters.put("transaction_id",data.getTransactionId());
            }
            if (StringUtils.isNotEmpty(data.getOutRefundNo())){
                paramters.put("out_refund_no",data.getOutRefundNo());
            }
            if (StringUtils.isNotEmpty(data.getOpUserId())){
                paramters.put("op_user_id",data.getOpUserId());
            }
            if (StringUtils.isNotEmpty(data.getRefundFeeType())){
                paramters.put("refund_fee_type",data.getRefundFeeType());
            }
            if (null != data.getRefundFee() && data.getRefundFee()>0){
                paramters.put("refund_fee",data.getRefundFee());
            }
        }
        return paramters;
    }
    //通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }


}