package com.bailuyiting.commons.core.jpa.sso;


import com.bailuyiting.commons.core.entity.sso.SysMyCar;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SysMyCarRepository extends BaseStringJpaRepository<SysMyCar> {

    @Query(value = "UPDATE sys_my_car u SET u.car_status=?3 WHERE u.sys_account=?1 and u.car_num=?2",nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatusByAccountAndCarNum(String account,String carNum,int status);


    @Query(value = "UPDATE sys_my_car u SET u.car_status=?2 WHERE u.id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatusById(String id,int status);
    /**
     *
     * @param carNum
     * @return
     */
    SysMyCar findByCarNum(String carNum);
    List<SysMyCar> findBySysUserId(String id);
    List<SysMyCar> findBySysAccount(String account);
    List<SysMyCar> findBySysAccount(String account, Pageable pageable);



}
