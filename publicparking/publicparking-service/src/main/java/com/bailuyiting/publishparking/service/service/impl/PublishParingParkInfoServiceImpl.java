package com.bailuyiting.publishparking.service.service.impl;

import com.bailuyiting.commons.core.jpa.publishparking.PublishParkingParkInfoRepository;
import com.bailuyiting.publishparking.service.service.PublishParkingParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishParingParkInfoServiceImpl implements PublishParkingParkInfoService {
     @Autowired
     private PublishParkingParkInfoRepository publishParkingParkInfoRepository;

    @Override
    public PublishParkingParkInfoRepository getBaseJapRepository() {
        return this.publishParkingParkInfoRepository;
    }
}
