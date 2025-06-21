# 1. Use the official OpenJDK image from Docker Hub
FROM openjdk:21-jdk

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the JAR file from your machine into the container
COPY target/*.jar app.jar

# 4. Expose port 8080 (Render assigns the port at runtime via $PORT)
EXPOSE 8080

# 5. Run the app, passing in the assigned PORT dynamically
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
