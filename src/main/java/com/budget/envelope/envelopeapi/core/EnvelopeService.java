package com.budget.envelope.envelopeapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class EnvelopeService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    EnvelopeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Envelope> getEnvelopes(final String userId, final long start, final long limit) {
        return jdbcTemplate.query(
                "SELECT * FROM envelopes WHERE userId = ? LIMIT ? OFFSET ?",
                new Object[] {userId, start, limit},
                (resultSet, rowNum) ->
                        new Envelope(
                                resultSet.getLong("id"),
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getString("envelopeType"),
                                resultSet.getDouble("allocatedMoney"),
                                resultSet.getDouble("spentMoney")
                        )
        );
    }

    Optional<Envelope> findEnvelope(final String userId, final long id) {
        List<Envelope> envelopes = jdbcTemplate.query(
                "SELECT * FROM envelopes WHERE id = ? AND userId = ?",
                new Object[] {id, userId},
                (resultSet, rowNum) ->
                        new Envelope(
                                resultSet.getLong("id"),
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getString("envelopeType"),
                                resultSet.getDouble("allocatedMoney"),
                                resultSet.getDouble("spentMoney")
                        )
        );
        if(envelopes.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(envelopes.get(0));
    }

    int createEnvelope(final String userId, final Envelope envelope) {
        return jdbcTemplate.update(
                "INSERT INTO envelopes (name, userId, envelopeType, allocatedMoney, spentMoney) VALUES (?, ?, ?, ?, ?)",
                envelope.getName(),
                userId,
                envelope.getEnvelopeType(),
                envelope.getAllocatedMoney(),
                envelope.getSpentMoney()
        );
    }

    int updateSpentValue(final long envelopeId, final String userId, final double newSpentValue) {
        return jdbcTemplate.update(
                "UPDATE envelopes SET spentMoney = ? WHERE id = ? AND userId = ?",
                newSpentValue,
                envelopeId,
                userId
        );
    }
}
