FROM gradle:jdk21-jammy
WORKDIR /app

COPY . /app
RUN gradle build -x test -i --stacktrace --no-daemon

FROM eclipse-temurin:21
WORKDIR /app

COPY --from=0 /app/build/libs/*.jar app.jar

# TODO: For testing purposes only, remove
CMD ls .