package com.bailuyiting.commons.core.jpa.sso;

import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingOrderBaseRepository extends BaseStringJpaRepository<ParkingOrderBase> {
    @Query(value = "FROM parking_order_base WHERE carNumber like CONCAT('%',?1,'%') and  parkId=?2")
    List<ParkingOrderBase> findByCarNumberAndParkId(String carNum,String id);
    List<ParkingOrderBase> findBySysAccount(String sysAccount,Pageable pageable);
    List<ParkingOrderBase> findByOrderTypeAndSysAccount(int orderType,String sysAccount,Pageable pageable);
    List<ParkingOrderBase> findByOrderTypeAndParkId(int orderType,int parkId,Pageable pageable);
    /**
     * 管理员查询已经预约的订单 根据是否有orderTime来判断是否入场
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "FROM parking_order_base WHERE orderStatus=1 and orderTime is null and parkId=?1")
    List<ParkingOrderBase> findOrderWhichIsAppointment(String id,Pageable pageable);
    /**
     * 管理员查询已经入场但是未支付的订单
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "FROM parking_order_base WHERE orderStatus=1 and orderTime is not null and parkId=?1")
    List<ParkingOrderBase> findOrderWhichIsAppointmentButNotPrice(String id,Pageable pageable);

    /**
     * 管理员查询已经预约已经付款但是未出场的订单
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "FROM parking_order_base  WHERE orderStatus=1 and price is not null and parkId=?1")
    List<ParkingOrderBase> findOrderWhichIsPriceButNotConfirm(String id,Pageable pageable);

    List<ParkingOrderBase> findByOrderStatusAndSysAccount(int orderStatus,String sysAccount,Pageable pageable);
    /**
     * 根据订单状态和小区停车位ID和车牌号查询订单
     * @param orderStatus
     * @param parkId
     * @param carNumber
     * @return
     */
    ParkingOrderBase findByOrderStatusAndParkIdAndCarNumber(int orderStatus,String parkId,String carNumber);
    /**
     * 根据订单状态和小区停车位ID查询订单
     * @param orderStatus
     * @param parkId
     * @param pageable
     * @return
     */
    List<ParkingOrderBase> findByOrderStatusAndParkId(int orderStatus,String parkId,Pageable pageable);
    /**
     * 根据账户查询所有已经确认成功的订单
     * @param sysAccount
     * @param pageable
     * @return
     */
    @Query(value = "FROM parking_order_base  WHERE orderStatus =2 and sysAccount=?1 ")
    List<ParkingOrderBase> findBySysAccountWhereOrderIsConfirm(String sysAccount,Pageable pageable);
    /**
     * 根据账户查询所有已经未确认成功的订单
     * @param sysAccount
     * @param pageable
     * @return
     */
    @Query(value = "FROM parking_order_base  WHERE orderStatus <>4 and sysAccount=?1 ")
    List<ParkingOrderBase> findBySysAccountWhereOrderIsNotConfirm(String sysAccount,Pageable pageable);
}
