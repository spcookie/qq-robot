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

    package io.net.api.command;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboGroupCmdServiceTriple {

    public static final String SERVICE_NAME = GroupCmdService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,GroupCmdService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME,CommandProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboGroupCmdServiceTriple::newStub);
        StubSuppliers.addSupplier(GroupCmdService.JAVA_SERVICE_NAME,  DubboGroupCmdServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(GroupCmdService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static GroupCmdService newStub(Invoker<?> invoker) {
        return new GroupCmdServiceStub((Invoker<GroupCmdService>)invoker);
    }

    private static final StubMethodDescriptor invokeMethod = new StubMethodDescriptor("invoke",
    GroupCmd.class, MsgResult.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GroupCmd::parseFrom,
    MsgResult::parseFrom);

    private static final StubMethodDescriptor invokeAsyncMethod = new StubMethodDescriptor("invoke",
    GroupCmd.class, CompletableFuture.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GroupCmd::parseFrom,
    MsgResult::parseFrom);

    private static final StubMethodDescriptor invokeProxyAsyncMethod = new StubMethodDescriptor("invokeAsync",
    GroupCmd.class, MsgResult.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GroupCmd::parseFrom,
    MsgResult::parseFrom);





    public static class GroupCmdServiceStub implements GroupCmdService{
        private final Invoker<GroupCmdService> invoker;

        public GroupCmdServiceStub(Invoker<GroupCmdService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public MsgResult invoke(GroupCmd request){
            return StubInvocationUtil.unaryCall(invoker, invokeMethod, request);
        }

        public CompletableFuture<MsgResult> invokeAsync(GroupCmd request){
            return StubInvocationUtil.unaryCall(invoker, invokeAsyncMethod, request);
        }

        @Override
        public void invoke(GroupCmd request, StreamObserver<MsgResult> responseObserver){
            StubInvocationUtil.unaryCall(invoker, invokeMethod , request, responseObserver);
        }



    }

    public static abstract class GroupCmdServiceImplBase implements GroupCmdService, ServerService<GroupCmdService> {

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
        public final Invoker<GroupCmdService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/invoke" );
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/invokeAsync" );

            BiConsumer<GroupCmd, StreamObserver<MsgResult>> invokeFunc = this::invoke;
            handlers.put(invokeMethod.getMethodName(), new UnaryStubMethodHandler<>(invokeFunc));
            BiConsumer<GroupCmd, StreamObserver<MsgResult>> invokeAsyncFunc = syncToAsync(this::invoke);
            handlers.put(invokeProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(invokeAsyncFunc));




            return new StubInvoker<>(this, url, GroupCmdService.class, handlers);
        }


        @Override
        public MsgResult invoke(GroupCmd request){
            throw unimplementedMethodException(invokeMethod);
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
