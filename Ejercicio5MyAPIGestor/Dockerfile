# Imagen del microservicio backend (API REST + MariaDB), Java 21.
# Construccion en dos etapas: Maven compila el JAR y la imagen final solo lleva el JRE.

# ---- Etapa 1: compilar ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Las pruebas y la cobertura se ejecutan en el pipeline de Jenkins; aqui solo se empaqueta.
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -Dmaven.test.skip=true package

# ---- Etapa 2: imagen de ejecucion ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --no-create-home gestor
COPY --from=build /app/target/Ejercicio5MyAPIGestor-0.0.1-SNAPSHOT.jar app.jar
USER gestor
EXPOSE 8081
# La conexion a la base de datos se define con SPRING_DATASOURCE_URL / _USERNAME / _PASSWORD.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
