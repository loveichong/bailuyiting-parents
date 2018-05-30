package com.bailuyiting.sso.service.config.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class Scheduing {
    @Scheduled(fixedRate = 3000)
    public void timerRate() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
    }
}
