package com.bailuyiting.module.wechat.until;

import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.module.wechat.config.bean.WeChatPayPropertyBean;
import com.bailuyiting.module.wechat.entity.AuthToken;
import com.bailuyiting.module.wechat.entity.WxPaySendData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class WeChatUtils {

    /**
     * 根据code获取微信授权access_token
     */
    public static AuthToken getTokenByAuthCode(String code, String app_id, String app_secret){
        AuthToken authToken=null;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL("https://api.weixin.qq.com/sns/oauth2/access_token?"+"appid="+ app_id+"&secret="+ app_secret+"&code="+code+"&grant_type=authorization_code");
            URLConnection uc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine ;
            while((inputLine=in.readLine())!=null){
                json.append(inputLine);
            }
            in.close();
            //将json字符串转成javaBean
            ObjectMapper om = new ObjectMapper();
            System.out.println("返回值："+json.toString());
            authToken = om.readValue(json.toString(),AuthToken.class);
        } catch (Exception e) {
        	System.out.println("获得token失败");
            e.printStackTrace();
        }
        return authToken;
    }

    /**
     * 获取微信签名
     * @param map 请求参数集合
     * @return 微信请求签名串
     */
    public static String getSign(SortedMap<String,Object> map,String key){
        StringBuffer sb = new StringBuffer();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //参数中sign、key不参与签名加密
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        System.out.println("原值："+sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 解析微信服务器发来的请求
     * @param inputStream
     * @return 微信返回的参数集合
     */
    public static SortedMap<String,Object> parseXml(InputStream inputStream) {
        SortedMap<String,Object> map = new TreeMap();
        try {
            //获取request输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //得到xml根元素
            Element root = document.getRootElement();
            //得到根元素所有节点
            List<Element> elementList = root.elements();
            //遍历所有子节点
            for (Element element:elementList){
                map.put(element.getName(),element.getText());
            }
            //释放资源
            inputStream.close();
        } catch (Exception e) {
            //throw new ServiceException("微信工具类:解析xml异常",e);
        }
        return map;
    }

    /**
     * 扩展xstream,使其支持name带有"_"的节点
     */
    public static XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_","_")));

    /**
     * 请求参数转换成xml
     * @param data
     * @return xml字符串
     */
    public static String sendDataToXml(WxPaySendData data){
        xStream.autodetectAnnotations(true);
        xStream.alias("xml", WxPaySendData.class);
        return xStream.toXML(data);
    }

    /**
     *  获取当前时间戳
     * @return 当前时间戳字符串
     */
    public static String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis()/1000);
    }

    /**
     * 获取指定长度的随机字符串
     * @param length
     * @return 随机字符串
     */
    public static String getRandomStr(int length){
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 向前端返回微信支付申请结果
     * @param resultMap
     * @param bean
     * @return
     */
    public static Map<String,Object> weChatPayReturn(Map<String, Object> resultMap, WeChatPayPropertyBean bean){
        String returnCode = (String)resultMap.get("return_code");
        String resultCode = (String)resultMap.get("result_code");
        //申请支付订单成功
        if (("SUCCESS".equals(resultCode)) && ("SUCCESS".equals(returnCode)))
        {
            String appId = (String) resultMap.get("appid");//微信公众号AppId
            String timeStamp = WeChatUtils.getTimeStamp();//当前时间戳
            String prepayId = (String)resultMap.get("prepay_id");//统一下单返回的预支付id
            String nonceStr = WeChatUtils.getRandomStr(20);//不长于32位的随机字符串
            SortedMap<String,Object> signMap = new TreeMap();//自然升序map
            signMap.put("appid",appId);
            signMap.put("partnerid",bean.getMchID());
            signMap.put("package","Sign=WXPay");
            signMap.put("noncestr",nonceStr);
            signMap.put("timestamp",timeStamp);
            signMap.put("prepayid",prepayId);
            signMap.put("sign",WeChatUtils.getSign(signMap,bean.getKey()));//签名
            return ResultUtils.success(signMap);
        }
        //申请支付订单失败
        //记录失败原因
        return ResultUtils.errorByUserDefine("600",resultMap.get("return_code")+":"+resultMap.get("return_msg")+":"+resultMap.get("err_code_des"));
    }

}