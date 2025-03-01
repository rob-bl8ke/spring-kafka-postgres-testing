package com.cyg.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.application.name=demo"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Value("${spring.application.name}")
    private String applicationName;

    @Test
    public void testApplicationName() {
        assertEquals("demo", applicationName);
    }

}
