package com.saeed.grpcvalidation;

import build.buf.validate.Violation;

import java.util.List;

public class GrpcValidationException extends RuntimeException {

    public GrpcValidationException(String message, Throwable cause) {
        super(mergeErrorMessages(List.of(message)), cause);
    }

    public GrpcValidationException(List<Violation> violations) {
        super(mergeErrorMessages(violations.stream().map(Violation::getMessage).toList()));
    }

    private static String mergeErrorMessages(List<String> messages) {
        return String.join("\n", messages);
    }
}
