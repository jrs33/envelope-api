package com.budget.envelope.envelopeapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SourceService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SourceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Source> getSources(final String userId, final long start, final long limit) {
        return jdbcTemplate.query(
                "SELECT * FROM sources WHERE userId = ? LIMIT ? OFFSET ?",
                new Object[] {userId, limit, start},
                (resultSet, rowNum) ->
                        new Source(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getString("userId")));
    }

    Optional<Source> findSource(final String userId, final long id) {
        List<Source> sources = jdbcTemplate.query(
                "SELECT * FROM sources WHERE id = ? AND userId = ?",
                new Object[] {id, userId},
                (resultSet, rowNum) ->
                        new Source(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getString("userId"))
        );
        if(sources.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(sources.get(0));
    }

    Source createSource(Source source) throws SourceException {
        int numRowsUpdated = jdbcTemplate.update(
               "INSERT INTO sources(userId, name, description) " +
                       "VALUES (?, ?, ?)",
                source.getUserId(), source.getName(), source.getDescription()
        );
        if (numRowsUpdated != 1) {
            throw new SourceException("unable_to_create_source");
        }

        return source;
    }
}
