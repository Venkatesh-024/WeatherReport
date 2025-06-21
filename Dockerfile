# Use Maven to build the project first
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the whole project (including pom.xml and src/)
COPY . .

# Build the jar inside Docker
RUN mvn clean install -DskipTests

# Now use a lightweight OpenJDK image to run the JAR
FROM openjdk:21-jdk

WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
