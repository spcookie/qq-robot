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

import org.apache.dubbo.common.stream.StreamObserver;

import java.util.concurrent.CompletableFuture;

public interface WorkService extends org.apache.dubbo.rpc.model.DubboStub {

    String JAVA_SERVICE_NAME = "io.net.api.work.WorkService";
    String SERVICE_NAME = "protobuf.work.WorkService";

    io.net.api.MsgResult doWork(io.net.api.GroupCmd request);

    default CompletableFuture<io.net.api.MsgResult> doWorkAsync(io.net.api.GroupCmd request){
        return CompletableFuture.completedFuture(doWork(request));
    }

    /**
    * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
    * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
    */
    default void doWork(io.net.api.GroupCmd request, StreamObserver<io.net.api.MsgResult> responseObserver){
        doWorkAsync(request).whenComplete((r, t) -> {
            if (t != null) {
                responseObserver.onError(t);
            } else {
                responseObserver.onNext(r);
                responseObserver.onCompleted();
            }
        });
    }

    io.net.api.work.Menu manifest(com.google.protobuf.Empty request);

    default CompletableFuture<io.net.api.work.Menu> manifestAsync(com.google.protobuf.Empty request){
        return CompletableFuture.completedFuture(manifest(request));
    }

    /**
    * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
    * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
    */
    default void manifest(com.google.protobuf.Empty request, StreamObserver<io.net.api.work.Menu> responseObserver){
        manifestAsync(request).whenComplete((r, t) -> {
            if (t != null) {
                responseObserver.onError(t);
            } else {
                responseObserver.onNext(r);
                responseObserver.onCompleted();
            }
        });
    }






}
