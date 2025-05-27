# ---------- Build Stage ----------
FROM amazoncorretto:21 as build

# Install required tools for mvnw
RUN yum install -y tar curl unzip

WORKDIR /app

# Copy the Maven wrapper and project files
COPY . .

# Go offline and package the JAR (skip tests for faster build)
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM amazoncorretto:21

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Run the app
CMD ["java", "-jar", "app.jar"]
