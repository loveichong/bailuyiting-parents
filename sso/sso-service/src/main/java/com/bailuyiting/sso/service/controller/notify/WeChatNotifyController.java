package com.bailuyiting.sso.service.controller.notify;

import com.bailuyiting.module.wechat.controller.WeChatPayController;
import com.bailuyiting.sso.service.component.BalanceWeChatPayNotifyProcessImpl;
import com.bailuyiting.sso.service.component.OrderWeChatPayAndConfirmNotifyProcessImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "微信支付回调接口，不对外开放",description = "订单微信支付回调接口，不对外开放")
@RestController
@RequestMapping(value = "notify/weChat")
public class WeChatNotifyController {
    @Autowired
    private WeChatPayController weChatPayController;
    @Autowired
    private OrderWeChatPayAndConfirmNotifyProcessImpl orderWeChatPayNotifyProcess;
    @Autowired
    private OrderWeChatPayAndConfirmNotifyProcessImpl orderWeChatPayAndConfirmNotifyProcess;
    @Autowired
    private BalanceWeChatPayNotifyProcessImpl balanceWeChatPayNotifyProcess;
    /**
     * 订单支付成功不确认订单回调
     * @param request
     * @param response
     */
    @PostMapping(value ="/order")
    public void  OrderWeChatNotify(HttpServletRequest request, HttpServletResponse response) {
        this.weChatPayController.finishPayment(request,response,orderWeChatPayNotifyProcess);
    }
    /**
     * 订单支付成功不确认订单回调
     * @param request
     * @param response
     */
    @PostMapping(value ="/order/confirm")
    public void  OrderWeChatAndConfirmNotify(HttpServletRequest request, HttpServletResponse response) {
        this.weChatPayController.finishPayment(request,response,orderWeChatPayAndConfirmNotifyProcess);
    }
    /**
     * 钱包充值成功回调
     * @param request
     * @param response
     */
    @PostMapping(value ="/balance")
    public void  BalanceWeChatNotify(HttpServletRequest request, HttpServletResponse response) {
        this.weChatPayController.finishPayment(request,response,balanceWeChatPayNotifyProcess);
    }

}
