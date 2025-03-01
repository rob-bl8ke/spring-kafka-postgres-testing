package com.cyg.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestTableRepository {
    private final JdbcTemplate jdbcTemplate;

    public TestTableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS test_table (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL
            )
        """;
        jdbcTemplate.execute(sql);
    }

    public void insertTestRow() {
        String sql = "INSERT INTO test_table (name) VALUES (?)";
        jdbcTemplate.update(sql, "Test Name");
    }
}
