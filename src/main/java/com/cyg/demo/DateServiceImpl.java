package com.cyg.demo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl {    
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
