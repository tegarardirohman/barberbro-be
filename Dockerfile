# Gunakan image resmi Maven untuk build tahap pertama
FROM maven:3.8.5-openjdk-17 AS build

# Set work directory di dalam container
WORKDIR /app

# Salin file pom.xml dan unduh dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Salin seluruh source code ke dalam container
COPY src ./src

# Build aplikasi Spring Boot
RUN mvn clean package -DskipTests

# Gunakan image OpenJDK untuk runtime tahap kedua
FROM openjdk:17-oracle
# FROM openjdk:17-jdk-slim

# Set work directory di dalam container
WORKDIR /app

# Salin hasil build dari tahap pertama
COPY --from=build /app/target/*.jar app.jar

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
