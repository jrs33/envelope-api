package com.budget.envelope.envelopeapi.core;

public class SourceException extends Exception {

    SourceException(String errorMessage) {
        super(errorMessage);
    }

    SourceException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
