package com.cyg.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

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
        String expectedTableName = topic + "_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        eventServiceImpl.insert(topic, name);

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> tableNameCaptor = ArgumentCaptor.forClass(String.class);

        verify(eventRepository).insert(nameCaptor.capture(), tableNameCaptor.capture());

        assertEquals(name, nameCaptor.getValue());
        assertEquals(expectedTableName, tableNameCaptor.getValue());
    }
}