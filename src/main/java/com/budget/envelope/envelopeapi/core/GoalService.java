package com.budget.envelope.envelopeapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class GoalService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GoalService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Optional<Goal> getGoal(String userId, long id) {
        List<Goal> queryResult = jdbcTemplate.query(
                "SELECT * from goals WHERE userId = ? AND id = ?",
                new Object[] {userId, id},
                (resultSet, rowNum) ->
                        new Goal(
                                resultSet.getLong("id"),
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getDouble("goalAmount"),
                                resultSet.getDouble("goalProgress")
                        )
        );

        return queryResult.size() != 1 ?
                Optional.empty() :
                Optional.of(queryResult.get(0));
    }

    List<Goal> getGoals(String userId, final long start, final long limit) {
        return jdbcTemplate.query(
                "SELECT * from goals WHERE userId = ? LIMIT ? OFFSET ?",
                new Object[] {userId, limit, start},
                (resultSet, rowNum) ->
                        new Goal(
                                resultSet.getLong("id"),
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getDouble("goalAmount"),
                                resultSet.getDouble("goalProgress")
                        )
        );
    }

    int createGoal(String userId, Goal goal) {
        return jdbcTemplate.update(
                "INSERT INTO goals (name, userId, goalAmount, goalProgress) VALUES (?, ?, ?, ?)",
                goal.getName(),
                userId,
                goal.getGoalAmount(),
                goal.getGoalProgress()
        );
    }

    int updateGoalProgress(final long goalId, final String userId, final double goalProgress) {
        return jdbcTemplate.update(
                "UPDATE goals SET goalProgress = ? WHERE id = ? AND userId = ?",
                goalProgress,
                goalId,
                userId
        );
    }
}
