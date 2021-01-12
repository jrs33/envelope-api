package com.budget.envelope.envelopeapi.core;

import com.budget.envelope.envelopeapi.security.AuthHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class GoalController {

    private GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/goal")
    public Goal getGoal(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "id") long id
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        Optional<Goal> goalOptional = goalService.getGoal(userId, id);
        if (!goalOptional.isPresent()) {
            throw new RuntimeException("no_goal_found");
        }
        return goalOptional.get();
    }

    @GetMapping("/goals")
    public List<Goal> getGoals(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "from") long from
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        return goalService.getGoals(userId, from, 10);
    }

    @PostMapping(path = "/goal/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Goal createGoal(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Goal goal
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        int numRowsChanged = goalService.createGoal(userId, goal);
        if(numRowsChanged == 0) {
            throw new RuntimeException("error_creating_goal");
        }
        return goal;
    }

    @PatchMapping(path = "/goal/progress/increase", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Goal updateGoalProgress(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "amount") double increaseAmount,
            @RequestParam(value = "goalId") long goalId
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);

        Optional<Goal> goalOptional = goalService.getGoal(userId, goalId);
        if (!goalOptional.isPresent()) {
            throw new RuntimeException("no_goal_found");
        }

        Goal goal = goalOptional.get();
        double newProgress = goal.getGoalProgress() + increaseAmount;
        int numRowsChanged = goalService.updateGoalProgress(goal.getId(), userId, newProgress);
        if(numRowsChanged == 0) {
            throw new RuntimeException("error_updating_goal");
        }
        return goal;
    }
}
