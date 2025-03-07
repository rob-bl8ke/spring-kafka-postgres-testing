package com.cyg.demo.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {    
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
