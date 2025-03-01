package com.cyg.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestTableRepositoryIntegrationTest extends BaseTestContainer {

    @Autowired
    private TestTableRepository testTableRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateTableAndInsertRow() {
        // Create the table
        testTableRepository.createTable();

        // Insert a test row
        testTableRepository.insertTestRow();

        // Verify the row was inserted
        String sql = "SELECT COUNT(*) FROM test_table WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, "Test Name");
        assertEquals(1, count);
    }
}