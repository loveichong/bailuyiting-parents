package com.bailuyiting.communityparking.service.service.impl;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingOwnerAuthRepository;
import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingParkInfoRepository;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommunityOwnerAuthServiceImpl implements CommunityParkingOwnerAuthService {
    @Autowired
    private CommunityParkingOwnerAuthRepository communityParkingOwnerAuthRepository;
    @Autowired
    private CommunityParkingParkInfoRepository communityParkingParkInfoRepository;
    @Override
    public CommunityParkingOwnerAuthRepository getBaseJapRepository() {
        return this.communityParkingOwnerAuthRepository;
    }

    @Override
    @Transactional
    public boolean confirmCommunityParkingOwnerAuthAndSaveCommunityParkingParkInfo(CommunityParkingOwnerAuth auth, CommunityParkingParkInfo parkInfo) {
        this.communityParkingOwnerAuthRepository.save(auth);
        this.communityParkingParkInfoRepository.save(parkInfo);
        return true;
    }
}
