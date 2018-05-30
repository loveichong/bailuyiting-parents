package com.bailuyiting.communityparking.service.service.impl;

import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingParkInfoRepository;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityParkingParkInfoServiceImpl implements CommunityParkingParkInfoService {
    @Autowired
    private CommunityParkingParkInfoRepository communityParkingParkInfoRepository;


    @Override
    public CommunityParkingParkInfoRepository getBaseJapRepository() {
        return this.communityParkingParkInfoRepository;
    }
}
