FROM maven:3.6-openjdk-8 AS builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
COPY --from=builder /usr/src/app/target/envelope-api-0.0.1-SNAPSHOT.jar /app.jar
RUN adduser -D myuser
USER myuser
CMD java -jar -Dserver.port=$PORT /app.jar