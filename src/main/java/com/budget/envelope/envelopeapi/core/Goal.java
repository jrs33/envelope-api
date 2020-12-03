package com.budget.envelope.envelopeapi.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goal {

    private long id;
    private String userId;
    private String name;
    private double goalAmount;
    private double goalProgress;

    @JsonCreator
    public Goal(@JsonProperty("id") long id,
                @JsonProperty("userId") String userId,
                @JsonProperty("name") String name,
                @JsonProperty("goalAmount") double goalAmount,
                @JsonProperty("goalProgress") double goalProgress) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.goalAmount = goalAmount;
        this.goalProgress = goalProgress;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public double getGoalProgress() {
        return goalProgress;
    }

    public String getUserId() {
        return userId;
    }
}
