package com.saeed.grpcvalidation;

import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import build.buf.protovalidate.exceptions.ValidationException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Component;

@Component
public class GrpcValidator {

    private final Validator validator;

    public GrpcValidator() {
        this.validator = new Validator();
    }

    public void validate(Message message) {
        try {
            ValidationResult result = validator.validate(message);

            if (!result.getViolations().isEmpty()) {
                throw new GrpcValidationException(result.getViolations());
            }
        } catch (ValidationException e) {
            throw new GrpcValidationException(e.getMessage(), e);
        }
    }
}
