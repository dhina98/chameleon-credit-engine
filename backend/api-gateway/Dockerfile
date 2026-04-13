# Use an official OpenJDK runtime as a parent image and compile as jar image
FROM eclipse-temurin:17-jdk-alpine AS build
LABEL authors="dhina"
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew && ./gradlew bootjar -x test

# Use a smaller base image for the runtime environment
# alpine is uses 5MB linux base image, which is much smaller than the full JDK image used for building
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar","app.jar"]