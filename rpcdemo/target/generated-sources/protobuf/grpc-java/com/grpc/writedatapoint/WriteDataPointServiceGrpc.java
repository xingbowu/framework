package com.grpc.writedatapoint;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.3.0)",
    comments = "Source: writedatapoint.proto")
public final class WriteDataPointServiceGrpc {

  private WriteDataPointServiceGrpc() {}

  public static final String SERVICE_NAME = "grpc.writedatapoint.WriteDataPointService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.grpc.writedatapoint.WriteDataPoint.PutDPRequest,
      com.grpc.writedatapoint.WriteDataPoint.PutDPReply> METHOD_PUT_DP =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "grpc.writedatapoint.WriteDataPointService", "PutDP"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.writedatapoint.WriteDataPoint.PutDPRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.writedatapoint.WriteDataPoint.PutDPReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WriteDataPointServiceStub newStub(io.grpc.Channel channel) {
    return new WriteDataPointServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WriteDataPointServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WriteDataPointServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static WriteDataPointServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WriteDataPointServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class WriteDataPointServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void putDP(com.grpc.writedatapoint.WriteDataPoint.PutDPRequest request,
        io.grpc.stub.StreamObserver<com.grpc.writedatapoint.WriteDataPoint.PutDPReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PUT_DP, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PUT_DP,
            asyncUnaryCall(
              new MethodHandlers<
                com.grpc.writedatapoint.WriteDataPoint.PutDPRequest,
                com.grpc.writedatapoint.WriteDataPoint.PutDPReply>(
                  this, METHODID_PUT_DP)))
          .build();
    }
  }

  /**
   */
  public static final class WriteDataPointServiceStub extends io.grpc.stub.AbstractStub<WriteDataPointServiceStub> {
    private WriteDataPointServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WriteDataPointServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WriteDataPointServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WriteDataPointServiceStub(channel, callOptions);
    }

    /**
     */
    public void putDP(com.grpc.writedatapoint.WriteDataPoint.PutDPRequest request,
        io.grpc.stub.StreamObserver<com.grpc.writedatapoint.WriteDataPoint.PutDPReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PUT_DP, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WriteDataPointServiceBlockingStub extends io.grpc.stub.AbstractStub<WriteDataPointServiceBlockingStub> {
    private WriteDataPointServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WriteDataPointServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WriteDataPointServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WriteDataPointServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.grpc.writedatapoint.WriteDataPoint.PutDPReply putDP(com.grpc.writedatapoint.WriteDataPoint.PutDPRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PUT_DP, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WriteDataPointServiceFutureStub extends io.grpc.stub.AbstractStub<WriteDataPointServiceFutureStub> {
    private WriteDataPointServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WriteDataPointServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WriteDataPointServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WriteDataPointServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.grpc.writedatapoint.WriteDataPoint.PutDPReply> putDP(
        com.grpc.writedatapoint.WriteDataPoint.PutDPRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PUT_DP, getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT_DP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WriteDataPointServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WriteDataPointServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUT_DP:
          serviceImpl.putDP((com.grpc.writedatapoint.WriteDataPoint.PutDPRequest) request,
              (io.grpc.stub.StreamObserver<com.grpc.writedatapoint.WriteDataPoint.PutDPReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class WriteDataPointServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpc.writedatapoint.WriteDataPoint.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WriteDataPointServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WriteDataPointServiceDescriptorSupplier())
              .addMethod(METHOD_PUT_DP)
              .build();
        }
      }
    }
    return result;
  }
}
