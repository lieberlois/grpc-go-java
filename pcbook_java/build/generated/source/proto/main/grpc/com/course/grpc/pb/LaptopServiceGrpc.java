package com.course.grpc.pb;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: laptop_service.proto")
public final class LaptopServiceGrpc {

  private LaptopServiceGrpc() {}

  public static final String SERVICE_NAME = "LaptopService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.course.grpc.pb.CreateLaptopRequest,
      com.course.grpc.pb.CreateLaptopResponse> getCreateLaptopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateLaptop",
      requestType = com.course.grpc.pb.CreateLaptopRequest.class,
      responseType = com.course.grpc.pb.CreateLaptopResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.course.grpc.pb.CreateLaptopRequest,
      com.course.grpc.pb.CreateLaptopResponse> getCreateLaptopMethod() {
    io.grpc.MethodDescriptor<com.course.grpc.pb.CreateLaptopRequest, com.course.grpc.pb.CreateLaptopResponse> getCreateLaptopMethod;
    if ((getCreateLaptopMethod = LaptopServiceGrpc.getCreateLaptopMethod) == null) {
      synchronized (LaptopServiceGrpc.class) {
        if ((getCreateLaptopMethod = LaptopServiceGrpc.getCreateLaptopMethod) == null) {
          LaptopServiceGrpc.getCreateLaptopMethod = getCreateLaptopMethod =
              io.grpc.MethodDescriptor.<com.course.grpc.pb.CreateLaptopRequest, com.course.grpc.pb.CreateLaptopResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateLaptop"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.course.grpc.pb.CreateLaptopRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.course.grpc.pb.CreateLaptopResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LaptopServiceMethodDescriptorSupplier("CreateLaptop"))
              .build();
        }
      }
    }
    return getCreateLaptopMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LaptopServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceStub>() {
        @java.lang.Override
        public LaptopServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceStub(channel, callOptions);
        }
      };
    return LaptopServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LaptopServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceBlockingStub>() {
        @java.lang.Override
        public LaptopServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceBlockingStub(channel, callOptions);
        }
      };
    return LaptopServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LaptopServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceFutureStub>() {
        @java.lang.Override
        public LaptopServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceFutureStub(channel, callOptions);
        }
      };
    return LaptopServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LaptopServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createLaptop(com.course.grpc.pb.CreateLaptopRequest request,
        io.grpc.stub.StreamObserver<com.course.grpc.pb.CreateLaptopResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateLaptopMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateLaptopMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.course.grpc.pb.CreateLaptopRequest,
                com.course.grpc.pb.CreateLaptopResponse>(
                  this, METHODID_CREATE_LAPTOP)))
          .build();
    }
  }

  /**
   */
  public static final class LaptopServiceStub extends io.grpc.stub.AbstractAsyncStub<LaptopServiceStub> {
    private LaptopServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LaptopServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceStub(channel, callOptions);
    }

    /**
     */
    public void createLaptop(com.course.grpc.pb.CreateLaptopRequest request,
        io.grpc.stub.StreamObserver<com.course.grpc.pb.CreateLaptopResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateLaptopMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LaptopServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LaptopServiceBlockingStub> {
    private LaptopServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LaptopServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.course.grpc.pb.CreateLaptopResponse createLaptop(com.course.grpc.pb.CreateLaptopRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateLaptopMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LaptopServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LaptopServiceFutureStub> {
    private LaptopServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LaptopServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.course.grpc.pb.CreateLaptopResponse> createLaptop(
        com.course.grpc.pb.CreateLaptopRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateLaptopMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_LAPTOP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LaptopServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LaptopServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_LAPTOP:
          serviceImpl.createLaptop((com.course.grpc.pb.CreateLaptopRequest) request,
              (io.grpc.stub.StreamObserver<com.course.grpc.pb.CreateLaptopResponse>) responseObserver);
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

  private static abstract class LaptopServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LaptopServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.course.grpc.pb.LaptopServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LaptopService");
    }
  }

  private static final class LaptopServiceFileDescriptorSupplier
      extends LaptopServiceBaseDescriptorSupplier {
    LaptopServiceFileDescriptorSupplier() {}
  }

  private static final class LaptopServiceMethodDescriptorSupplier
      extends LaptopServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LaptopServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LaptopServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LaptopServiceFileDescriptorSupplier())
              .addMethod(getCreateLaptopMethod())
              .build();
        }
      }
    }
    return result;
  }
}
