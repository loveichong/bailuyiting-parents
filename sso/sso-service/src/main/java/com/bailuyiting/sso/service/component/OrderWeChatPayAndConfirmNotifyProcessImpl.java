package com.bailuyiting.sso.service.component;

import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.module.wechat.inter.WeChatPayNotifyProcess;
import com.bailuyiting.module.wechat.until.WeChatParameterUntils;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单微信支付回调处理器
 */
@Component
public class OrderWeChatPayAndConfirmNotifyProcessImpl implements WeChatPayNotifyProcess {

    @Autowired
    private ParkingOrderBaseService parkingOrderBaseService;

    @Override
    public void success(Map<String, String> resultMap) {
        ParkingOrderBase one = this.parkingOrderBaseService.getBaseJapRepository().findOne(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
        if(one==null){
            throw new RuntimeException("订单不存在");
        }
        switch (one.getOrderStatus()){//状态（1=已预约，2=已确认，3=作废）
            case 2:throw new RuntimeException("订单已经确定");
            case 3:throw new RuntimeException("订单作废");
        }
        one.setModifyTime(DateUtils.formatNow());//修改时间
        one.setOrderStatus(2);//状态（1=已预约，2=已确认，3=作废）
        one.setPrice(new BigDecimal(WeChatParameterUntils.getPayPriceWhenPayNotifySuccess(resultMap)));//支付价格
        int minutes = DateUtils.getBetweenDateMinutes(DateUtils.totDate(one.getOrderTime()), new Date());
        one.setKeepTime(Integer.toString(minutes));//停车时间
        one.setFinishTime(DateUtils.formatNow());//结束时间
        //保存订单并且修改停车位状态
        switch (one.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:this.parkingOrderBaseService.confirmPublishParkingOrderSuccess(one);break;
            case 2:this.parkingOrderBaseService.confirmSideParkingOrderSuccess(one);break;
            case 3:this.parkingOrderBaseService.confirmCommunityParkingOrderSuccess(one);break;
        }
    }
    @Override
    public void failure(Map<String, String> resultMap) {
      //保持不变
    }
}
