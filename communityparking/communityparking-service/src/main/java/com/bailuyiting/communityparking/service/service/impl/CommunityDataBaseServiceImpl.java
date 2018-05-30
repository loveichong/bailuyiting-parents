package com.bailuyiting.communityparking.service.service.impl;


import com.bailuyiting.commons.core.jpa.communityparking.CommunityDataBaseRepository;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityDataBaseServiceImpl implements CommunityDataBaseService {

    @Autowired
    private CommunityDataBaseRepository communityDataBaseRepository;
    @Override
    public CommunityDataBaseRepository getBaseJapRepository() {
        return this.communityDataBaseRepository;
    }
}
