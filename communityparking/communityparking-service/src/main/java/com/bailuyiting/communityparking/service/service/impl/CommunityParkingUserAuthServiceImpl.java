package com.bailuyiting.communityparking.service.service.impl;

import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingUserAuthRepository;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityParkingUserAuthServiceImpl implements CommunityParkingUserAuthService {
    @Autowired
    private CommunityParkingUserAuthRepository communityParkingUserAuthRepository;
    @Override
    public CommunityParkingUserAuthRepository getBaseJapRepository() {
        return this.communityParkingUserAuthRepository;
    }
}
