# Etapa 1: build con Gradle
FROM gradle:8.3-jdk21 AS builder
WORKDIR /app

# Copiamos los archivos de Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copiamos el código fuente
COPY src src

# Construimos el .jar
RUN ./gradlew clean build -x test

# Etapa 2: runtime
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copiamos el jar generado desde la etapa builder
COPY --from=builder /app/build/libs/*.jar nitro-note-1.0-SNAPSHOT.jar

# Puerto de la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "nitro-note-1.0-SNAPSHOT.jar "]
