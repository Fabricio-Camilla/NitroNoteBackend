# Etapa 1: construcción con Gradle
FROM gradle:jdk21 AS builder
WORKDIR /app

# Copiamos los archivos de configuración de Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle/

# Copiamos el código fuente
COPY src src/

# Construimos el archivo JAR
RUN ./gradlew clean build -x test

# Etapa 2: entorno de ejecución
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copiamos el JAR generado desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar nitro-note-1.0-SNAPSHOT.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "nitro-note-1.0-SNAPSHOT.jar"]
