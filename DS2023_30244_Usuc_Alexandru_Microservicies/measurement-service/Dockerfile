FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/measurement-service-1.0-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/measurement-service-1.0-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:17-alpine

ENV TZ=UTC

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]

#FROM maven:3.8.3-openjdk-17 AS builder
#
#COPY ./src/ /root/src
#COPY ./pom.xml /root/
#COPY ./checkstyle.xml /root/
#WORKDIR /root
#RUN mvn package
#RUN java -Djarmode=layertools -jar /root/target/device-service-1.0-SNAPSHOT.jar list
#RUN java -Djarmode=layertools -jar /root/target/device-service-1.0-SNAPSHOT.jar extract
#RUN ls -l /root
#
#FROM openjdk:17-jdk-slim-buster
#
#ENV TZ=UTC
#ENV DB_IP=localhost
#ENV DB_PORT=3306
#ENV DB_USER=root
#ENV DB_PASSWORD=root
#ENV DB_DBNAME=ds2023devices
#
#
#COPY --from=builder /root/dependencies/ ./
#COPY --from=builder /root/snapshot-dependencies/ ./
#
#RUN sleep 10
#COPY --from=builder /root/spring-boot-loader/ ./
#COPY --from=builder /root/application/ ./
#
## Copy device-service JAR
##COPY --from=builder /root/target/device-service-1.0-SNAPSHOT.jar /app/device-service.jar
#
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]