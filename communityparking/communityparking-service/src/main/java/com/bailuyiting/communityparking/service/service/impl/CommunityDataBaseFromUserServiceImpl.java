package com.bailuyiting.communityparking.service.service.impl;


import com.bailuyiting.commons.core.jpa.communityparking.CommunityDataBaseFromUserRepository;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseFromUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityDataBaseFromUserServiceImpl implements CommunityDataBaseFromUserService {
   @Autowired
    private CommunityDataBaseFromUserRepository communityDataBaseFromUserRepository;

    @Override
    public CommunityDataBaseFromUserRepository getBaseJapRepository() {
        return this.communityDataBaseFromUserRepository;
    }
}
