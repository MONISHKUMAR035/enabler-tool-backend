# Stage 1 — build the app with Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /build

# copy project files and build (skip tests)
COPY . .
RUN mvn -B -DskipTests package

# Stage 2 — runtime JRE
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# copy the JAR from the build stage
COPY --from=build /build/target/*.jar app.jar

# expose port
EXPOSE 8080

# run with dynamic port (Render sets PORT)
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
