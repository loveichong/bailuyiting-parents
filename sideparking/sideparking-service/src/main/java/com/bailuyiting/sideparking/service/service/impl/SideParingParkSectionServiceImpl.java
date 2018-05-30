package com.bailuyiting.sideparking.service.service.impl;

import com.bailuyiting.commons.core.jpa.sideparking.SideParkingParkSectionRepository;
import com.bailuyiting.sideparking.service.service.SideParkingParkingSectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SideParingParkSectionServiceImpl implements SideParkingParkingSectionService {
    @Autowired
    private SideParkingParkSectionRepository sideParkingParkSectionRepository;
    @Override
    public SideParkingParkSectionRepository getBaseJapRepository() {
        return this.sideParkingParkSectionRepository;
    }
}
