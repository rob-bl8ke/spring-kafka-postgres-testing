package com.cyg.demo;

import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final DateService dateService;

    public EventServiceImpl(EventRepository eventRepository, DateService dateService) {
        this.eventRepository = eventRepository;
        this.dateService = dateService;
    }

    @Override
    public void insert(String topic, String name) {
        String tableName = topic + "_" + dateService.getCurrentTime()
            .format(DateTimeFormatter.BASIC_ISO_DATE);
        
        eventRepository.insert(name, tableName);
    }
}
