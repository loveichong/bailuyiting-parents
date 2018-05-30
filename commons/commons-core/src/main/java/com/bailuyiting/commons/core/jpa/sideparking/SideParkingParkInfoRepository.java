package com.bailuyiting.commons.core.jpa.sideparking;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SideParkingParkInfoRepository extends BaseStringJpaRepository<SideParkingParkInfo> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE side_parking_park_info u SET u.park_status=?2 WHERE u.id=?1", nativeQuery = true)
    void updateStatusById(String id,int status);

    SideParkingParkInfo findByParkNum(String parkNum);
    @Query(value = "select * from side_parking_park_info where section_id=?1", nativeQuery = true)
    List<SideParkingParkInfo> findBySectionId(String sectionId);
    /**
     * 根据经纬度范围查找所以车辆信息
     * 此处不开启事务
     * @param minLat
     * @param minLng
     * @param maxLat
     * @param maxLng
     * @return
     */
    @Query(value = "select * from side_parking_park_info where (latitude between ?1 and ?3) and (longitude between ?2 and ?4)", nativeQuery = true)
    List<SideParkingParkInfo> findByAround(double minLat, double minLng, double maxLat, double maxLng);

}
