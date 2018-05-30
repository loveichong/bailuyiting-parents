package com.bailuyiting.commons.core.jpa.sideparking;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SideParkingParkSectionRepository extends BaseStringJpaRepository<SideParkingParkSection> {
    List<SideParkingParkSection> findByStreet(String street);
    SideParkingParkSection findByStreetAndSection(String street, String section);

    /**
     * 此接口查询效率极慢，后面要做性能优化
     * @param street
     * @param section
     * @return
     */
    SideParkingParkSection findByProvinceAndCityAndAreaAndStreetAndSection(String province, String city,
                                                                           String Area, String street, String section);
    /**
     * 根据经纬度范围查找所以街道下的段落
     * 此处不开启事务
     * @param minLat
     * @param minLng
     * @param maxLat
     * @param maxLng
     * @return
     */
    @Query(value = "select * from side_parking_park_section where (latitude between ?1 and ?3) and (longitude between ?2 and ?4)", nativeQuery = true)
    List<SideParkingParkSection> findByAround(double minLat, double minLng, double maxLat, double maxLng);


}
