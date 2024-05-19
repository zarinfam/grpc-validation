package com.saeed.grpcvalidation;

import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import build.buf.protovalidate.exceptions.ValidationException;
import com.google.protobuf.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GrpcValidator {

    private final Validator validator;

    public GrpcValidator() {
        this.validator = new Validator();
    }

    @Around("@annotation(com.saeed.grpcvalidation.GrpcValidation)")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        try {
            final var args = proceedingJoinPoint.getArgs();
            for (Object param : args) {
                if (param instanceof Message message) {
                    ValidationResult validationResult = validator.validate(message);

                    if (!validationResult.getViolations().isEmpty()) {
                        throw new GrpcValidationException(validationResult.getViolations());
                    }
                }
            }
            result = proceedingJoinPoint.proceed(args);
        } catch (ValidationException e) {
            throw new GrpcValidationException(e.getMessage(), e);
        }

        return result;
    }
}
