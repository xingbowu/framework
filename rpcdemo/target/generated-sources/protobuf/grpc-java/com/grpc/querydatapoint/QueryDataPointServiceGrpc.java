package com.grpc.querydatapoint;

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
 * <pre>
 * Service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.3.0)",
    comments = "Source: querydatapoint.proto")
public final class QueryDataPointServiceGrpc {

  private QueryDataPointServiceGrpc() {}

  public static final String SERVICE_NAME = "grpc.writedatapoint.QueryDataPointService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest,
      com.grpc.querydatapoint.QueryDataPoint.QueryDPReply> METHOD_QUERY_DP =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "grpc.writedatapoint.QueryDataPointService", "QueryDP"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.querydatapoint.QueryDataPoint.QueryDPReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryDataPointServiceStub newStub(io.grpc.Channel channel) {
    return new QueryDataPointServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryDataPointServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new QueryDataPointServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static QueryDataPointServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new QueryDataPointServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Service
   * </pre>
   */
  public static abstract class QueryDataPointServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void queryDP(com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest request,
        io.grpc.stub.StreamObserver<com.grpc.querydatapoint.QueryDataPoint.QueryDPReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_DP, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_QUERY_DP,
            asyncUnaryCall(
              new MethodHandlers<
                com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest,
                com.grpc.querydatapoint.QueryDataPoint.QueryDPReply>(
                  this, METHODID_QUERY_DP)))
          .build();
    }
  }

  /**
   * <pre>
   * Service
   * </pre>
   */
  public static final class QueryDataPointServiceStub extends io.grpc.stub.AbstractStub<QueryDataPointServiceStub> {
    private QueryDataPointServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryDataPointServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryDataPointServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryDataPointServiceStub(channel, callOptions);
    }

    /**
     */
    public void queryDP(com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest request,
        io.grpc.stub.StreamObserver<com.grpc.querydatapoint.QueryDataPoint.QueryDPReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_DP, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service
   * </pre>
   */
  public static final class QueryDataPointServiceBlockingStub extends io.grpc.stub.AbstractStub<QueryDataPointServiceBlockingStub> {
    private QueryDataPointServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryDataPointServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryDataPointServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryDataPointServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.grpc.querydatapoint.QueryDataPoint.QueryDPReply queryDP(com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_DP, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service
   * </pre>
   */
  public static final class QueryDataPointServiceFutureStub extends io.grpc.stub.AbstractStub<QueryDataPointServiceFutureStub> {
    private QueryDataPointServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryDataPointServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryDataPointServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryDataPointServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.grpc.querydatapoint.QueryDataPoint.QueryDPReply> queryDP(
        com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_DP, getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_DP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryDataPointServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueryDataPointServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_DP:
          serviceImpl.queryDP((com.grpc.querydatapoint.QueryDataPoint.QueryDPRequest) request,
              (io.grpc.stub.StreamObserver<com.grpc.querydatapoint.QueryDataPoint.QueryDPReply>) responseObserver);
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

  private static final class QueryDataPointServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpc.querydatapoint.QueryDataPoint.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QueryDataPointServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QueryDataPointServiceDescriptorSupplier())
              .addMethod(METHOD_QUERY_DP)
              .build();
        }
      }
    }
    return result;
  }
}
