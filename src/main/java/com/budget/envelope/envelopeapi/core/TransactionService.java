package com.budget.envelope.envelopeapi.core;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
class TransactionService {

    private final JdbcTemplate jdbcTemplate;
    private final EnvelopeService envelopeService;

    @Autowired
    TransactionService(JdbcTemplate jdbcTemplate, EnvelopeService envelopeService) {
        this.jdbcTemplate = jdbcTemplate;
        this.envelopeService = envelopeService;
    }

    List<Transaction> getTransactions(final String userId, final long start, final long limit) {
        return jdbcTemplate.query(
                "SELECT * FROM transactions WHERE userId = ? LIMIT ?, ?",
                new Object[] {userId, start, limit},
                (resultSet, rowNum) ->
                        new Transaction(
                                resultSet.getLong("id"),
                                LocalDate.parse(resultSet.getString("date")),
                                resultSet.getString("userId"),
                                resultSet.getLong("envelopeId"),
                                resultSet.getDouble("amount"),
                                resultSet.getString("transactionName"),
                                TransactionStrategy.from(resultSet.getString("transactionStrategy")))
        );
    }

    Transaction recordTransaction(Transaction transaction) throws TransactionException {

        Optional<Envelope> envelope = envelopeService.findEnvelope(transaction.getUserId(), transaction.getEnvelopeId());
        if(!envelope.isPresent()) {
            throw new TransactionException("no_matching_envelope");
        }

        int numRowsUpdated = jdbcTemplate.update(
                "INSERT INTO transactions(date, userId, envelopeId, amount, transactionName, transactionStrategy) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                LocalDate.now().toString(),
                transaction.getUserId(),
                transaction.getEnvelopeId(),
                transaction.getAmount(),
                transaction.getTransactionName(),
                transaction.getTransactionStrategy().name()
        );
        if(numRowsUpdated != 1) {
            throw new TransactionException("error_saving_transaction");
        }

        Double transactionResult = transaction.getTransactionStrategy().apply(envelope.get(), transaction);
        envelopeService.updateSpentValue(envelope.get().getId(), envelope.get().getUserId(), transactionResult);

        return transaction;
    }
}
