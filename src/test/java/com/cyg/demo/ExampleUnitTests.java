package com.cyg.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

// A really basic Unit Test
@ExtendWith(MockitoExtension.class)
class ExampleUnitTests {

    @Test
    void testApplicationName() {
        assertEquals("demo", "demo");
    }
}