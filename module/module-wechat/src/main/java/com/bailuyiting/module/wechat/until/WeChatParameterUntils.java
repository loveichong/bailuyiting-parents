package com.bailuyiting.module.wechat.until;

import java.util.Map;

public class WeChatParameterUntils {

   public static String getOrderIdWhenPayNotifySuccess(Map<String, String> resultMap){
        return resultMap.get("out_trade_no");
    }
    public static String getResultCodeWhenPayNotify(Map<String, String> resultMap){
        return resultMap.get("result_code");
    }
    public static String getResultMessageWhenPayNotify(Map<String, String> resultMap){
        return resultMap.get("return_msg");
    }
    public static String getTransactionIdWhenPayNotifySuccess(Map<String, String> resultMap){
        return resultMap.get("transaction_id");
    }
    public static String getPayPriceWhenPayNotifySuccess(Map<String, String> resultMap){
        return resultMap.get("total_fee");
    }
}
