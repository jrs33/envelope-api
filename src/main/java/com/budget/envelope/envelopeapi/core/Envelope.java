package com.budget.envelope.envelopeapi.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Envelope {

    private long id;
    private String userId;
    private String name;
    private String envelopeType;

    private double allocatedMoney;
    private double spentMoney;

    @JsonCreator
    public Envelope(@JsonProperty("id") long id,
                    @JsonProperty("userId") String userId,
                    @JsonProperty("name") String name,
                    @JsonProperty("envelopeType") String envelopeType,
                    @JsonProperty("allocatedMoney") double allocatedMoney,
                    @JsonProperty("spentMoney") double spentMoney) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.envelopeType = envelopeType;
        this.allocatedMoney = allocatedMoney;
        this.spentMoney = spentMoney;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEnvelopeType() {
        return envelopeType;
    }

    public double getAllocatedMoney() {
        return allocatedMoney;
    }

    public double getSpentMoney() {
        return spentMoney;
    }
}
