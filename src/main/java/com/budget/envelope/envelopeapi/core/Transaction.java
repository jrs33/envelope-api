package com.budget.envelope.envelopeapi.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

class Transaction {

    private final long id;
    private final LocalDate date;
    private String userId;
    private final long envelopeId;
    private final double amount;
    private final String transactionName;
    @JsonProperty("transactionType") private final TransactionStrategy transactionStrategy;

    @JsonCreator
    Transaction(@JsonProperty("id") long id,
                @JsonProperty("date") LocalDate date,
                @JsonProperty("userId") String userId,
                @JsonProperty("envelopeId") long envelopeId,
                @JsonProperty("amount") double amount,
                @JsonProperty("transactionName") String transactionName,
                @JsonProperty("transactionType") TransactionStrategy transactionStrategy) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.envelopeId = envelopeId;
        this.amount = amount;
        this.transactionName = transactionName;
        this.transactionStrategy = transactionStrategy;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public long getEnvelopeId() {
        return envelopeId;
    }

    public LocalDate getDate() {
        return date;
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
}
