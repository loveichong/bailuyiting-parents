package com.bailuyiting.module.wechat.inter;

import java.util.Map;

/**
 * 微信支付回调处理器接口 要使用微信支付必须实现这两个接口
 */
public interface WeChatPayNotifyProcess {
    void success(Map<String, String> resultMap);
    void failure(Map<String, String> resultMap);
}
