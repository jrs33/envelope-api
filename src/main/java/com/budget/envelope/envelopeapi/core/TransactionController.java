package com.budget.envelope.envelopeapi.core;

import com.budget.envelope.envelopeapi.security.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/transactions")
    public List<Transaction> getTransactions(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "from") long from
    ) throws TransactionException {
        String userId = AuthHelper.getUserIdFromHeader(token);
        return transactionService.getTransactions(userId, from, 10);
    }

    @PostMapping(path = "/transaction/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction postTransaction(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Transaction transaction
    ) throws TransactionException {
        String userId = AuthHelper.getUserIdFromHeader(token);
        transaction.setUserId(userId);
        return transactionService.recordTransaction(transaction);
    }
}
