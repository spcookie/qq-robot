FROM alpine:latest

LABEL authors="augenstern"

# ADD ./jre-17-x64-alpine-linux.tar.gz /root
ADD https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.8%2B7/OpenJDK17U-jre_x64_alpine-linux_hotspot_17.0.8_7.tar.gz /root

ENV JAVA_HOME=/root/jdk-17.0.8+7-jre
ENV PATH=$JAVA_HOME/bin:$PATH
ENV LANG C.UTF-8

RUN echo -e 'https://mirrors.aliyun.com/alpine/v3.6/main/\nhttps://mirrors.aliyun.com/alpine/v3.6/community/' > /etc/apk/repositories
RUN apk add -U tzdata  \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime  \
    && apk del tzdata