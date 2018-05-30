package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.entity.common.ParkInfoBase;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingParkInfoRepository;
import com.bailuyiting.commons.core.jpa.publishparking.PublishParkingParkInfoRepository;
import com.bailuyiting.commons.core.jpa.sideparking.SideParkingParkInfoRepository;
import com.bailuyiting.commons.core.jpa.sso.ParkingOrderBaseRepository;
import com.bailuyiting.commons.core.jpa.sso.SysMyCarRepository;
import com.bailuyiting.commons.core.jpa.sso.SysUserRepository;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingOrderBaseServiceImpl implements ParkingOrderBaseService {

    @Autowired
    private ParkingOrderBaseRepository parkingOrderBaseRepository;

    @Autowired
    private SideParkingParkInfoRepository sideParkingParkInfoRepository;

    @Autowired
    private CommunityParkingParkInfoRepository communityParkingParkInfoRepository;
    @Autowired
    private PublishParkingParkInfoRepository publishParkingParkInfoRepository;
    @Autowired
    private SysMyCarRepository sysMyCarRepository;
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public ParkingOrderBaseRepository getBaseJapRepository() {
        return this.parkingOrderBaseRepository;
    }


    @Override
    @Transactional
    public ParkingOrderBase addSideParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order,SideParkingParkInfo parkInfo) {
        return this.addOrder(order,parkInfo);
    }

    @Override
    @Transactional
    public ParkingOrderBase confirmSideParkingOrderSuccess(ParkingOrderBase order) {
         return this.confirmOrder(order);
    }


    @Override
    @Transactional
    public ParkingOrderBase addCommunityParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order,CommunityParkingParkInfo parkInfo) {
        return this.addOrder(order,parkInfo);
    }

    @Override
    @Transactional
    public ParkingOrderBase confirmCommunityParkingOrderSuccess(ParkingOrderBase order) {
        return this.confirmOrder(order);
    }

    @Override
    @Transactional
    public ParkingOrderBase addPublishParkingOrderAndModifyParkInfoStatus(ParkingOrderBase order, PublishParkingParkInfo parkInfo) {
         return addOrder(order,null);
    }

    @Override
    @Transactional
    public ParkingOrderBase confirmPublishParkingOrderSuccess(ParkingOrderBase order) {
        return this.confirmOrder(order);
    }

    /**
     * 根据订单类型生成订单
     * @param order
     * @param parkInfo
     */
    private ParkingOrderBase  addOrder(ParkingOrderBase order,ParkInfoBase parkInfo){
        //锁定我的爱车
        this.sysMyCarRepository.updateStatusByAccountAndCarNum(order.getSysAccount(),order.getCarNumber(),2);
        //锁定账户正在使用订单
        this.sysUserRepository.updateStatusByAccount(order.getSysAccount(),4);
        //生成订单
        ParkingOrderBase result = this.parkingOrderBaseRepository.save(order);
        //锁定停车位
        //如果是小区停车，默认输入null
        if(parkInfo==null){
            return result;
        }
        if(parkInfo instanceof SideParkingParkInfo){
            this.sideParkingParkInfoRepository.save((SideParkingParkInfo) parkInfo);
            return result;
        }
        if(parkInfo instanceof CommunityParkingParkInfo){
            this.communityParkingParkInfoRepository.save((CommunityParkingParkInfo) parkInfo);
            return result;
        }
        //都不是直接抛错
       throw new RuntimeException("订单类型有问题，请检查");
    }

  private  ParkingOrderBase confirmOrder(ParkingOrderBase order){
      //释放我的爱车
      this.sysMyCarRepository.updateStatusByAccountAndCarNum(order.getSysAccount(),order.getCarNumber(),1);
      //释放账户 如果是钱包支付的话就要提前释放 因为开启了全程事务
      this.sysUserRepository.updateStatusByAccount(order.getSysAccount(),5);
      //保存订单
      ParkingOrderBase result = this.parkingOrderBaseRepository.save(order);
      //释放停车位 修改停车位
      switch (order.getOrderType()){
          case 1:this.publishParkingParkInfoRepository.parkResidualNumAddById(order.getParkId());return result;
          case 2: this.sideParkingParkInfoRepository.updateStatusById(order.getParkId(),2);return result;
          case 3: this.communityParkingParkInfoRepository.updateStatusById(order.getParkId(),2);return result;
      }
      //都不是直接抛错
      throw new RuntimeException("订单类型有问题，请检查");
  }

}
