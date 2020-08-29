package com.budget.envelope.envelopeapi.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionTarget {

    private final long id;
    private final long year;
    private final long month;
    private final long day;
    private String userId;
    private final long envelopeId;
    private final double amount;
    private final String transactionName;
    @JsonProperty("transactionType") private final TransactionStrategy transactionStrategy;
    private final long sourceId;

    @JsonCreator
    TransactionTarget(@JsonProperty("id") long id,
                      @JsonProperty("year") long year,
                      @JsonProperty("month") long month,
                      @JsonProperty("day") long day,
                      @JsonProperty("userId") String userId,
                      @JsonProperty("envelopeId") long envelopeId,
                      @JsonProperty("amount") double amount,
                      @JsonProperty("transactionName") String transactionName,
                      @JsonProperty("transactionType") TransactionStrategy transactionStrategy,
                      @JsonProperty("sourceId") long sourceId) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.userId = userId;
        this.envelopeId = envelopeId;
        this.amount = amount;
        this.transactionName = transactionName;
        this.transactionStrategy = transactionStrategy;
        this.sourceId = sourceId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public long getEnvelopeId() {
        return envelopeId;
    }

    public TransactionStrategy getTransactionStrategy() {
        return transactionStrategy;
    }

    public double getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getYear() {
        return year;
    }

    public long getMonth() {
        return month;
    }

    public long getDay() {
        return day;
    }
}
