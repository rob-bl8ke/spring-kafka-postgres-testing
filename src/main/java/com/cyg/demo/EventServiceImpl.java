package com.cyg.demo;

import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void insert(String topic, String name) {
        String tableName = topic + "_" + java.time.LocalDate.now()
            .format(DateTimeFormatter.BASIC_ISO_DATE);
        
        eventRepository.insert(name, tableName);
    }
}
