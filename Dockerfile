FROM maven:3.6-openjdk-8

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml clean package

CMD ["java","-jar","/usr/src/app/target/envelope-api-0.0.1-SNAPSHOT.jar"]