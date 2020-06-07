package com.budget.envelope.envelopeapi.core;

class TransactionException extends Exception {

    TransactionException(String errorMessage) {
        super(errorMessage);
    }

    TransactionException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
