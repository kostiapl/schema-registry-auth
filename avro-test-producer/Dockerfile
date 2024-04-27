FROM maven:3.8.3-openjdk-17 as build

WORKDIR /build

COPY . /build/
#COPY ./src/ /build
#COPY ./pom.xml /build

RUN mvn -X package

FROM openjdk:17.0.1-jdk-slim AS run

RUN adduser --system --group app-user
COPY --from=build --chown=app-user:app-user /build/target/avro-schema-test-1.0-SNAPSHOT.lib avro-schema-test-1.0-SNAPSHOT.lib
COPY --from=build --chown=app-user:app-user /build/target/avro-schema-test-1.0-SNAPSHOT.jar app.jar


USER app-user

CMD ["java", "-jar", "app.jar"]