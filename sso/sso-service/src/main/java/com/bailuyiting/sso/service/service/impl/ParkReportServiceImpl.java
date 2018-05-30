package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.jpa.sso.ParkReportRepository;
import com.bailuyiting.sso.service.service.ParkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkReportServiceImpl implements ParkReportService {
    @Autowired
    private ParkReportRepository parkReportRepository;
    @Override
    public ParkReportRepository getBaseJapRepository() {
        return this.parkReportRepository;
    }
}
