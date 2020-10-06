FROM adoptopenjdk:11-jre-openj9
FROM maven:3.6.3-adoptopenjdk-11-openj9

COPY . .
RUN mvn -v
RUN mvn clean verify compile install -DskipTests

COPY ./target/encryption-1.0.0.jar /usr/app/
WORKDIR /usr/app
RUN java -version
CMD ["java", "-XX:+UseG1GC", "-jar", "encryption-1.0.0.jar", "10", "-Dspring.profiles.active=default"]
