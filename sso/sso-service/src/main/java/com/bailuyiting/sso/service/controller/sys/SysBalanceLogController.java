package com.bailuyiting.sso.service.controller.sys;

import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.module.wechat.config.bean.WeChatPayPropertyBean;
import com.bailuyiting.module.wechat.controller.WeChatPayController;
import com.bailuyiting.module.wechat.entity.WechatTradePagePayRequest;
import com.bailuyiting.module.wechat.until.WeChatUtils;
import com.bailuyiting.sso.service.service.SysBalanceLogService;
import com.bailuyiting.sso.service.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(value = "我的钱包-API",description = "我的钱包-API")
@RestController
@RequestMapping(value = "api/balance/")
public class SysBalanceLogController {
    @Autowired
    private SysBalanceLogService sysBalanceLogService;
    @Autowired
    private WeChatPayController weChatPayController;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private WeChatPayPropertyBean weChatPayPropertyBean;
    @ApiOperation(value = "钱包微信充值",httpMethod ="POST",notes = "钱包微信充值")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "price",value = "充值金额",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "tradeType",value = "交易类型(前端自己此查找微信支付文档填写交易类型，保证无误)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "weChat")
    public Map<String,Object> AddSideParkingReport(@RequestParam BigDecimal price,@RequestParam String tradeType, HttpServletRequest req) {
        //MVC判空
        //查询账户
        SysUser account = this.sysUserService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if (account == null ) {
            return ResultUtils.errorByUserDefine("600", "账户无效，不能充值");
        }
        //生成预支付订单
        SysBalanceLog log = new SysBalanceLog();
        log.setCreateTime(DateUtils.formatNow());//生成时间
        log.setBalanceType(1);//微信充值
        log.setSysAccount(SQYRequestUntils.getAccount(req));//设置账户
        log.setBalanceStatus(0);//申请充值
        log.setUsePrice(price);//设置充值金额
        SysBalanceLog save = this.sysBalanceLogService.getBaseJapRepository().save(log);
        //申请微信支付
        WechatTradePagePayRequest payRequest = new WechatTradePagePayRequest();
        payRequest.setBody("白鹭易停-钱包微信充值");
        payRequest.setNeedWechatFee(price);//设置需要支付的金额 元
        payRequest.setTradeNo(save.getId());//设置订单ID
        payRequest.setTradeType(tradeType);//填写交易类型
        payRequest.setNotifyUrl("/notify/weChat/balance");//设置回调地址 后缀即可
        //申请微信支付
        Map<String, Object> resultMap = this.weChatPayController.unifiedOrder(payRequest);
        //充值成功 充值失败
        return WeChatUtils.weChatPayReturn(resultMap,weChatPayPropertyBean);
    }
    @ApiOperation(value = "查看账户钱包所有使用记录",httpMethod ="GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping (value = "")
    public Map<String,Object> getBalanceForHistory(@RequestParam Integer page,@RequestParam Integer size, HttpServletRequest req) {
        List<SysBalanceLog> result = this.sysBalanceLogService.getBaseJapRepository().findBySysAccount(SQYRequestUntils.getAccount(req), new PageRequest(page, size, Sort.Direction.DESC, "createTime"));
        return ResultUtils.success(result);
    }
}
