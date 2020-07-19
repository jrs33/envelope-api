package com.budget.envelope.envelopeapi.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Source {

    private final long id;
    private final String name;
    private final String description;
    private String userId;

    @JsonCreator
    Source(@JsonProperty("id") long id,
           @JsonProperty("name") String name,
           @JsonProperty("description") String description,
           @JsonProperty("userId") String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    void setUserId(String userId) {
        this.userId = userId;
    }
}
