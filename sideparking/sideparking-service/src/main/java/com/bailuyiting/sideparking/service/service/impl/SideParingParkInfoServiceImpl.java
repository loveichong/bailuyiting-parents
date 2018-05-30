package com.bailuyiting.sideparking.service.service.impl;

import com.bailuyiting.commons.core.jpa.sideparking.SideParkingParkInfoRepository;
import com.bailuyiting.commons.core.jpa.sideparking.SideParkingParkSectionRepository;
import com.bailuyiting.sideparking.service.service.SideParkingParkInfoService;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SideParingParkInfoServiceImpl implements SideParkingParkInfoService {

    @Autowired
    private SideParkingParkInfoRepository sideParkingParkInfoRepository;
    @Autowired
    private SideParkingParkSectionRepository sideParkingParkSectionRepository;

    @Override
    public SideParkingParkInfoRepository getBaseJapRepository() {
        return this.sideParkingParkInfoRepository;
    }

    @Override
    @Transactional
    public boolean saveInfoAndSectionNumAddOne(SideParkingParkInfo base, String sectionId) {
        //开启事务保存base
        this.sideParkingParkInfoRepository.save(base);
        //重新查找section防止脏读
        SideParkingParkSection one = this.sideParkingParkSectionRepository.findOne(sectionId);
        //section的num加1
        if(one==null){
            return false;
        }
        if(one.getNum()==null){
            one.setNum(1);
        }
        else {
            one.setNum(one.getNum()+1);
        }
        //保持section
        this.sideParkingParkSectionRepository.save(one);
        return true;
    }


}
