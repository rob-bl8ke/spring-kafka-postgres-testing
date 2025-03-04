package com.cyg.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private final JdbcTemplate jdbcTemplate;

    public EventRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(String name, String tableName) {
        String createTableSql = String.format("""
            CREATE TABLE IF NOT EXISTS %s (
            id SERIAL PRIMARY KEY,
            name VARCHAR(100) NOT NULL
            )
        """, tableName);
        jdbcTemplate.execute(createTableSql);

        String insertRowSql = "INSERT INTO test_table (name) VALUES (?)";
        jdbcTemplate.update(insertRowSql, name);
    }
}
