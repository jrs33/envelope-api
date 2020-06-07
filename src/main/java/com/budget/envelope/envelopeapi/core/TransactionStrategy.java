package com.budget.envelope.envelopeapi.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

enum TransactionStrategy {

    CREDIT((current, diffAmount) -> current - diffAmount),
    DEBIT((current, diffAmount) -> current + diffAmount);

    private static final Map<String, TransactionStrategy> lookupMap = new HashMap<>();

    static {
        TransactionStrategy[] strategies = TransactionStrategy.values();
        for(TransactionStrategy strategy : strategies) {
            lookupMap.put(strategy.name(), strategy);
        }
    }

    private final BiFunction<Double, Double, Double> strategy;

    TransactionStrategy(BiFunction<Double, Double, Double> strategy) {
        this.strategy = strategy;
    }

    private BiFunction<Double, Double, Double> getStrategy() {
        return strategy;
    }

    public Double apply(Envelope envelope, Transaction transaction) {
        return getStrategy().apply(envelope.getSpentMoney(), transaction.getAmount());
    }

    static TransactionStrategy from(String name) {
        if(!lookupMap.containsKey(name)) {
            throw new IllegalArgumentException("invalid_strategy_name");
        }
        return lookupMap.get(name);
    }
}
