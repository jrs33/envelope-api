package com.budget.envelope.envelopeapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class EnvelopeStatisticsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    EnvelopeStatisticsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Double getRemaining(final String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT SUM(allocatedMoney) - SUM(spentMoney) as remaining FROM envelopes WHERE userId = ?",
                new Object[] {userId},
                (resultSet, rowNum) -> resultSet.getDouble("remaining"));
    }
}
