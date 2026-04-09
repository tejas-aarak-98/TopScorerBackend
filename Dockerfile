FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy all files
COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Run the jar file
CMD ["sh", "-c", "java -jar target/*.jar"]