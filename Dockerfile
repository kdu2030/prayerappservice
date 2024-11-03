FROM gradle:jdk21-jammy
WORKDIR /app

COPY . /app
RUN gradle build -x test -i --stacktrace --no-daemon

FROM eclipse-temurin:21
WORKDIR /app

COPY --from=0 /app/build/libs/*.jar app.jar

# Render provides a PORT environmental variable.
# Needs to be mapped to SERVER_PORT so Spring Boot can use it.
EXPOSE ${PORT}
ENV SERVER_PORT=${PORT}

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prd"]
