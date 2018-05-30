package com.bailuyiting.sso.service.component;

import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.module.wechat.inter.WeChatPayNotifyProcess;
import com.bailuyiting.module.wechat.until.WeChatParameterUntils;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;
/**
 * 订单微信支付回调处理器
 */
@Component
public class OrderWeChatPayNotifyProcessImpl implements WeChatPayNotifyProcess {

    @Autowired
    private ParkingOrderBaseService parkingOrderBaseService;

    @Override
    public void success(Map<String, String> resultMap) {
        ParkingOrderBase one = this.parkingOrderBaseService.getBaseJapRepository().findOne(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
        if(one==null){//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
            throw new RuntimeException("订单不存在");
        }
        switch (one.getOrderStatus()){//状态（1=已预约，2=已确认，3=作废）
            case 4:throw new RuntimeException("订单已经确定");
        }
        //判断是否是补单类型
        BigDecimal oldPrice=one.getPrice();
        BigDecimal newPrice = new BigDecimal(WeChatParameterUntils.getPayPriceWhenPayNotifySuccess(resultMap));
        if(oldPrice==null){
            one.setPrice(newPrice);//设置支付金额
        }
        else {
            one.setPrice(newPrice.add(oldPrice));//设置支付金额 相加
        }
        one.setOrderStatus(3);//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        one.setModifyTime(DateUtils.formatNow());//设置此次支付的时间
        //保存订单
        this.parkingOrderBaseService.getBaseJapRepository().save(one);

    }
    @Override
    public void failure(Map<String, String> resultMap) {
      //保持不变
    }
}
