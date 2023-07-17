/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.net.api.work;

import com.google.protobuf.Message;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.stub.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public final class DubboWorkServiceTriple {

    public static final String SERVICE_NAME = WorkService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, WorkService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, WorkProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboWorkServiceTriple::newStub);
        StubSuppliers.addSupplier(WorkService.JAVA_SERVICE_NAME, DubboWorkServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(WorkService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static WorkService newStub(Invoker<?> invoker) {
        return new WorkServiceStub((Invoker<WorkService>) invoker);
    }

    private static final StubMethodDescriptor doWorkMethod = new StubMethodDescriptor("doWork",
            io.net.api.GroupCmd.class, io.net.api.MsgResult.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), io.net.api.GroupCmd::parseFrom,
            io.net.api.MsgResult::parseFrom);

    private static final StubMethodDescriptor doWorkAsyncMethod = new StubMethodDescriptor("doWork",
            io.net.api.GroupCmd.class, java.util.concurrent.CompletableFuture.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), io.net.api.GroupCmd::parseFrom,
            io.net.api.MsgResult::parseFrom);

    private static final StubMethodDescriptor doWorkProxyAsyncMethod = new StubMethodDescriptor("doWorkAsync",
            io.net.api.GroupCmd.class, io.net.api.MsgResult.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), io.net.api.GroupCmd::parseFrom,
            io.net.api.MsgResult::parseFrom);

    private static final StubMethodDescriptor manifestMethod = new StubMethodDescriptor("manifest",
            com.google.protobuf.Empty.class, io.net.api.work.Menu.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.google.protobuf.Empty::parseFrom,
            io.net.api.work.Menu::parseFrom);

    private static final StubMethodDescriptor manifestAsyncMethod = new StubMethodDescriptor("manifest",
            com.google.protobuf.Empty.class, java.util.concurrent.CompletableFuture.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.google.protobuf.Empty::parseFrom,
            io.net.api.work.Menu::parseFrom);

    private static final StubMethodDescriptor manifestProxyAsyncMethod = new StubMethodDescriptor("manifestAsync",
            com.google.protobuf.Empty.class, io.net.api.work.Menu.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
            obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.google.protobuf.Empty::parseFrom,
            io.net.api.work.Menu::parseFrom);


    public static class WorkServiceStub implements WorkService {
        private final Invoker<WorkService> invoker;

        public WorkServiceStub(Invoker<WorkService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public io.net.api.MsgResult doWork(io.net.api.GroupCmd request) {
            return StubInvocationUtil.unaryCall(invoker, doWorkMethod, request);
        }

        public CompletableFuture<io.net.api.MsgResult> doWorkAsync(io.net.api.GroupCmd request) {
            return StubInvocationUtil.unaryCall(invoker, doWorkAsyncMethod, request);
        }

        @Override
        public void doWork(io.net.api.GroupCmd request, StreamObserver<io.net.api.MsgResult> responseObserver) {
            StubInvocationUtil.unaryCall(invoker, doWorkMethod, request, responseObserver);
        }

        @Override
        public io.net.api.work.Menu manifest(com.google.protobuf.Empty request) {
            return StubInvocationUtil.unaryCall(invoker, manifestMethod, request);
        }

        public CompletableFuture<io.net.api.work.Menu> manifestAsync(com.google.protobuf.Empty request) {
            return StubInvocationUtil.unaryCall(invoker, manifestAsyncMethod, request);
        }

        @Override
        public void manifest(com.google.protobuf.Empty request, StreamObserver<io.net.api.work.Menu> responseObserver) {
            StubInvocationUtil.unaryCall(invoker, manifestMethod, request, responseObserver);
        }


    }

    public static abstract class WorkServiceImplBase implements WorkService, ServerService<WorkService> {

        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public final Invoker<WorkService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
                    .getExtensionLoader(PathResolver.class)
                    .getDefaultExtension();
            Map<String, StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub("/" + SERVICE_NAME + "/doWork");
            pathResolver.addNativeStub("/" + SERVICE_NAME + "/doWorkAsync");
            pathResolver.addNativeStub("/" + SERVICE_NAME + "/manifest");
            pathResolver.addNativeStub("/" + SERVICE_NAME + "/manifestAsync");

            BiConsumer<io.net.api.GroupCmd, StreamObserver<io.net.api.MsgResult>> doWorkFunc = this::doWork;
            handlers.put(doWorkMethod.getMethodName(), new UnaryStubMethodHandler<>(doWorkFunc));
            BiConsumer<io.net.api.GroupCmd, StreamObserver<io.net.api.MsgResult>> doWorkAsyncFunc = syncToAsync(this::doWork);
            handlers.put(doWorkProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(doWorkAsyncFunc));
            BiConsumer<com.google.protobuf.Empty, StreamObserver<io.net.api.work.Menu>> manifestFunc = this::manifest;
            handlers.put(manifestMethod.getMethodName(), new UnaryStubMethodHandler<>(manifestFunc));
            BiConsumer<com.google.protobuf.Empty, StreamObserver<io.net.api.work.Menu>> manifestAsyncFunc = syncToAsync(this::manifest);
            handlers.put(manifestProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(manifestAsyncFunc));


            return new StubInvoker<>(this, url, WorkService.class, handlers);
        }


        @Override
        public io.net.api.MsgResult doWork(io.net.api.GroupCmd request) {
            throw unimplementedMethodException(doWorkMethod);
        }

        @Override
        public io.net.api.work.Menu manifest(com.google.protobuf.Empty request) {
            throw unimplementedMethodException(manifestMethod);
        }


        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }

        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                    "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
