package com.bailuyiting.sso.service.service;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.jpa.sso.ParkingOrderBaseRepository;
import com.bailuyiting.commons.core.service.BaseEntiryService;


public interface ParkingOrderBaseService extends BaseEntiryService<ParkingOrderBaseRepository> {
    /**
     * 保存路边停车订单并且修改路边停车位状态
     * @param order
     * @param parkInfo
     * @return
     */
    ParkingOrderBase addSideParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order, SideParkingParkInfo parkInfo);
    ParkingOrderBase confirmSideParkingOrderSuccess(ParkingOrderBase order);
    /**
     * 保存小区停车订单并且修改小区停车位状态
     * @param order
     * @param parkInfo
     * @return
     */
    ParkingOrderBase addCommunityParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order, CommunityParkingParkInfo parkInfo);
    ParkingOrderBase confirmCommunityParkingOrderSuccess(ParkingOrderBase order);

    /**
     * 保存公共停车订单并且修改小区停车位状态
     * @param order
     * @param parkInfo
     * @return
     */
    ParkingOrderBase addPublishParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order, PublishParkingParkInfo parkInfo);
    ParkingOrderBase confirmPublishParkingOrderSuccess(ParkingOrderBase order);
}
