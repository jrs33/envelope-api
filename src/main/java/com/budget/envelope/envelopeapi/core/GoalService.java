package com.budget.envelope.envelopeapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GoalService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                goal.getUserId(),
                goal.getGoalAmount(),
                goal.getGoalProgress()
        );
    }

    int updateGoalProgress(final int goalId, final String userId, final double goalProgress) {
        return jdbcTemplate.update(
                "UPDATE goals SET goalProgress = ? WHERE id = ? AND userId = ?",
                goalProgress,
                goalId,
                userId
        );
    }
}
