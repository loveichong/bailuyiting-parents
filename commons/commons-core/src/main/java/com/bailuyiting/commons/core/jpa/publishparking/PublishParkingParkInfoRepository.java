package com.bailuyiting.commons.core.jpa.publishparking;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PublishParkingParkInfoRepository extends BaseStringJpaRepository<PublishParkingParkInfo> {
    PublishParkingParkInfo findByPublishName(String publishName);
    /**
     * 根据经纬度范围查找所以车辆信息
     * 此处不开启事务
     * @param minLat
     * @param minLng
     * @param maxLat
     * @param maxLng
     * @return
     */
    @Query(value = "select * from publish_parking_park_info where (latitude between ?1 and ?3) and (longitude between ?2 and ?4)", nativeQuery = true)
    List<PublishParkingParkInfo> findByAround(double minLat, double minLng, double maxLat, double maxLng);

    /**
     * 剩余停车位加1
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE publish_parking_park_info SET park_residual_num=park_residual_num+1 WHERE id=?1", nativeQuery = true)
    void  parkResidualNumAddById(String id);

}
