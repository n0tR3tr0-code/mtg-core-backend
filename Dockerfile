# Fase 1: Build con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
# Definiamo una cartella di lavoro
WORKDIR /app
# Copiamo il pom e le sorgenti
COPY pom.xml .
COPY src ./src
# Eseguiamo il build
RUN mvn clean package -DskipTests

# Fase 2: Esecuzione con Java
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copiamo il file JAR generato dalla fase di build
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]