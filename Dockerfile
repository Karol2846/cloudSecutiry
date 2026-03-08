# Etap 1: Budowanie z użyciem Gradle
FROM gradle:8.5-jdk21-alpine AS builder
WORKDIR /app

# Kopiowanie plików konfiguracyjnych i kodu źródłowego
COPY build.gradle settings.gradle ./
COPY src ./src

# Budowanie aplikacji (pomijamy testy dla przyśpieszenia)
RUN gradle clean build -x test --no-daemon

# Etap 2: Środowisko uruchomieniowe (czyste JRE)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Kopiowanie wygenerowanego pliku JAR z etapu budowania
COPY --from=builder /app/build/libs/*.jar app.jar

# ENTRYPOINT bez wystawiania portu (EXPOSE), bo host i tak nie ma mieć dostępu
ENTRYPOINT ["java", "-jar", "app.jar"]