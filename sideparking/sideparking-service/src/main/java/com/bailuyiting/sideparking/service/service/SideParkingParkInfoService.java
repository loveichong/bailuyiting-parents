package com.bailuyiting.sideparking.service.service;


import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.jpa.sideparking.SideParkingParkInfoRepository;
import com.bailuyiting.commons.core.service.BaseEntiryService;

public interface SideParkingParkInfoService extends BaseEntiryService<SideParkingParkInfoRepository> {
    /**
     * 保持停车位信息并把Section的停车位加1
     * @param base
     * @param sectionId
     * @return
     */
    boolean saveInfoAndSectionNumAddOne(SideParkingParkInfo base, String sectionId);



}
