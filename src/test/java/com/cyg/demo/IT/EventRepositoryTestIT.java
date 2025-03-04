package com.cyg.demo.IT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.cyg.demo.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventRepositoryTestIT extends BaseContainerTestIT {

    private static final String SELECT_QUERY = "SELECT COUNT(*) FROM test_table WHERE name = ?";

    @Autowired
    private EventRepository testTableRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void testCreateTableAndInsertRow() {
        // Create the table
        testTableRepository.insert("Test Name", "test_table");

        // Verify the row was inserted
        String sql = SELECT_QUERY;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, "Test Name");
        assertEquals(1, count);
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateTableAndInsertRow_2() {
        // Create the table
        testTableRepository.insert("Test Name", "test_table");

        // Verify the row was inserted
        String sql = SELECT_QUERY;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, "Test Name");
        assertEquals(1, count);
    }
}