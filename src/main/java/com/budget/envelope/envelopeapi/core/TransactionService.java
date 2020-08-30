package com.budget.envelope.envelopeapi.core;

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
    private final SourceService sourceService;

    @Autowired
    TransactionService(JdbcTemplate jdbcTemplate, EnvelopeService envelopeService, SourceService sourceService) {
        this.jdbcTemplate = jdbcTemplate;
        this.envelopeService = envelopeService;
        this.sourceService = sourceService;
    }

    @Deprecated
    List<Transaction> getTransactions(final String userId, final long start, final long limit) {
        return jdbcTemplate.query(
                "SELECT * FROM transactions WHERE userId = ? LIMIT ? OFFSET ?",
                new Object[] {userId, limit, start},
                (resultSet, rowNum) ->
                        new Transaction(
                                resultSet.getLong("id"),
                                LocalDate.parse(resultSet.getString("date")),
                                resultSet.getString("userId"),
                                resultSet.getLong("envelopeId"),
                                resultSet.getDouble("amount"),
                                resultSet.getString("transactionName"),
                                TransactionStrategy.from(resultSet.getString("transactionStrategy")),
                                resultSet.getLong("sourceId"))
        );
    }

    List<TransactionTarget> getTransactionsAll(
            final String userId,
            final long month,
            final long year
    ) {
        return jdbcTemplate.query(
                "SELECT * FROM transactions2 WHERE userId = ? AND month = ? AND year = ?",
                new Object[] {userId, month, year},
                (resultSet, rowNum) ->
                        new TransactionTarget(
                                resultSet.getLong("id"),
                                resultSet.getLong("year"),
                                resultSet.getLong("month"),
                                resultSet.getLong("day"),
                                resultSet.getString("userId"),
                                resultSet.getLong("envelopeId"),
                                resultSet.getDouble("amount"),
                                resultSet.getString("transactionName"),
                                TransactionStrategy.from(resultSet.getString("transactionStrategy")),
                                resultSet.getLong("sourceId"))
        );
    }

    List<TransactionTarget> getTransactionsPaginated(
            final String userId,
            final long month,
            final long year,
            final long start,
            final long limit
    ) {
        return jdbcTemplate.query(
                "SELECT * FROM transactions2 WHERE userId = ? AND month = ? AND year = ? LIMIT ? OFFSET ?",
                new Object[] {userId, month, year, limit, start},
                (resultSet, rowNum) ->
                        new TransactionTarget(
                                resultSet.getLong("id"),
                                resultSet.getLong("year"),
                                resultSet.getLong("month"),
                                resultSet.getLong("day"),
                                resultSet.getString("userId"),
                                resultSet.getLong("envelopeId"),
                                resultSet.getDouble("amount"),
                                resultSet.getString("transactionName"),
                                TransactionStrategy.from(resultSet.getString("transactionStrategy")),
                                resultSet.getLong("sourceId"))
        );
    }

    List<TransactionTarget> getTransactionsForDay(
            final String userId,
            final long month,
            final long year,
            final long day
    ) {
        return jdbcTemplate.query(
                "SELECT * FROM transactions2 WHERE userId = ? AND month = ? AND year = ? AND day = ?",
                new Object[] {userId, month, year, day},
                (resultSet, rowNum) ->
                        new TransactionTarget(
                                resultSet.getLong("id"),
                                resultSet.getLong("year"),
                                resultSet.getLong("month"),
                                resultSet.getLong("day"),
                                resultSet.getString("userId"),
                                resultSet.getLong("envelopeId"),
                                resultSet.getDouble("amount"),
                                resultSet.getString("transactionName"),
                                TransactionStrategy.from(resultSet.getString("transactionStrategy")),
                                resultSet.getLong("sourceId"))
        );
    }

    @Deprecated
    Transaction recordTransaction(Transaction transaction) throws TransactionException {

        Optional<Envelope> envelope = envelopeService.findEnvelope(transaction.getUserId(), transaction.getEnvelopeId());
        if(!envelope.isPresent()) {
            throw new TransactionException("no_matching_envelope");
        }
        Optional<Source> source = sourceService.findSource(transaction.getUserId(), transaction.getSourceId());
        if(!source.isPresent()) {
            throw new TransactionException("no_matching_source");
        }

        int numRowsUpdated = jdbcTemplate.update(
                "INSERT INTO transactions(date, userId, envelopeId, amount, transactionName, transactionStrategy, sourceId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                transaction.getDate(),
                transaction.getUserId(),
                transaction.getEnvelopeId(),
                transaction.getAmount(),
                transaction.getTransactionName(),
                transaction.getTransactionStrategy().name(),
                transaction.getSourceId()
        );
        if(numRowsUpdated != 1) {
            throw new TransactionException("error_saving_transaction");
        }

        Double transactionResult = transaction.getTransactionStrategy().apply(envelope.get(), transaction);
        envelopeService.updateSpentValue(envelope.get().getId(), envelope.get().getUserId(), transactionResult);

        return transaction;
    }

    TransactionTarget recordTransactionTarget(TransactionTarget transaction) throws TransactionException {

        Optional<Envelope> envelope = envelopeService.findEnvelope(transaction.getUserId(), transaction.getEnvelopeId());
        if(!envelope.isPresent()) {
            throw new TransactionException("no_matching_envelope");
        }
        Optional<Source> source = sourceService.findSource(transaction.getUserId(), transaction.getSourceId());
        if(!source.isPresent()) {
            throw new TransactionException("no_matching_source");
        }

        int numRowsUpdated = jdbcTemplate.update(
                "INSERT INTO transactions2(year, month, day, userId, envelopeId, amount, transactionName, transactionStrategy, sourceId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                transaction.getYear(),
                transaction.getMonth(),
                transaction.getDay(),
                transaction.getUserId(),
                transaction.getEnvelopeId(),
                transaction.getAmount(),
                transaction.getTransactionName(),
                transaction.getTransactionStrategy().name(),
                transaction.getSourceId()
        );
        if(numRowsUpdated != 1) {
            throw new TransactionException("error_saving_transaction");
        }

        Double transactionResult = transaction.getTransactionStrategy().applyTarget(envelope.get(), transaction);
        envelopeService.updateSpentValue(envelope.get().getId(), envelope.get().getUserId(), transactionResult);

        return transaction;
    }
}
