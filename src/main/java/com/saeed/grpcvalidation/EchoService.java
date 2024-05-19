package com.saeed.grpcvalidation;

import com.saeed.grpcvalidation.model.EchoServiceGrpc;
import com.saeed.grpcvalidation.model.Message;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EchoService extends EchoServiceGrpc.EchoServiceImplBase {

    private final GrpcValidator validator;

    public EchoService(GrpcValidator validator) {
        this.validator = validator;
    }

    @Override
    public void echo(Message request, StreamObserver<Message> responseObserver) {
        validator.validate(request);
        Message message = Message.newBuilder()
                .setText("Echo: " + request.getText())
                .build();
        responseObserver.onNext(message);
        responseObserver.onCompleted();
    }
}
