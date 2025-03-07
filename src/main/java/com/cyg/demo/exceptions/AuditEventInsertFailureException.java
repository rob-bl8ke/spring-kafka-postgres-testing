package com.cyg.demo.exceptions;

public class AuditEventInsertFailureException extends RuntimeException {
    public AuditEventInsertFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
