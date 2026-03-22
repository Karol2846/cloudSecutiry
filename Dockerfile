# --- Build stage ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true
COPY src/ src/
RUN ./gradlew bootJar --no-daemon -x test

# --- Runtime stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#TODO: obrazy zbudowane (7.1c) - zapisane w pliku zad7 https://docs.google.com/document/d/1LdKF2DlUz208P9LIdz2pAEmA--JL820bkG_cFVNX5qw/edit?tab=t.0
    # przy dalszych krokach koniecznie włóż instrukcję z lab do gemini - daje mega protipy do komend