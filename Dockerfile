#FROM openjdk:11.0.13-jre-slim-buster
## 修改docker时间为东八区
#RUN echo "Asia/Shanghai" > /etc/timezone
## 修改镜像源为阿里云
#RUN echo "deb https://mirrors.aliyun.com/debian buster main" > /etc/apt/sources.list
#RUN echo "deb https://mirrors.aliyun.com/debian buster-updates main" >> /etc/apt/sources.list
FROM registry.cn-zhangjiakou.aliyuncs.com/guanweiming/openjdk:11.0.13-jre

ENV PROFILES dev
ENV JAVA_OPTS "-Xms128m -Xmx200m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m"

ADD target/lib libs
ADD target/face-server-1.0.jar app.jar

CMD java $JAVA_OPTS -Dloader.path=/libs -Dspring.profiles.active=${PROFILES} -jar /app.jar
