package com.cyg.demo.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cyg.demo.repositories.EventRepository;
import com.cyg.demo.services.DateService;
import com.cyg.demo.services.EventServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private DateService dateService;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsert() {
        String topic = "testTopic";
        String name = "testName";
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 3, 4, 0, 0);
        String expectedTableName = topic + "_" + fixedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);

        // Configure the mock DateService to return the fixed date
        when(dateService.getCurrentTime()).thenReturn(fixedDateTime);

        eventServiceImpl.insert(topic, name);

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> tableNameCaptor = ArgumentCaptor.forClass(String.class);

        verify(eventRepository).insert(nameCaptor.capture(), tableNameCaptor.capture());

        assertEquals(name, nameCaptor.getValue());
        assertEquals(expectedTableName, tableNameCaptor.getValue());
    }
}