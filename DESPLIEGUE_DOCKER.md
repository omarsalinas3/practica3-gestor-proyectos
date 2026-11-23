# Despliegue con Docker

La aplicación **Gestor de proyectos** se despliega con las imágenes Docker de sus microservicios, orquestadas con Docker Compose.

| Servicio | Imagen | Puerto | Descripción |
|---|---|---|---|
| `db` | `mariadb:11` | 3307 (solo localhost) | Base de datos `dbgestor`; el esquema se carga de `database/schema.sql` |
| `backend` | `gestor-backend:1.0` | 8081 | API REST (`Ejercicio5MyAPIGestor`, Spring Boot 2.7, Java 21) |
| `frontend` | `gestor-frontend:1.0` | 9080 | Interfaz web (`Ejercicio5Cliente`, Spring Boot 3.2, Java 21) |

Cada microservicio tiene su `Dockerfile` en su carpeta. Se construyen en dos etapas: Maven compila el JAR (`maven:3.9.9-eclipse-temurin-21`) y la imagen final solo lleva el JRE (`eclipse-temurin:21-jre`) y corre con un usuario sin privilegios.

## Requisitos

- Docker Desktop (o Docker Engine con el plugin Compose) en ejecución.
- Puertos 8081 y 9080 libres.

## Desplegar

```bash
docker compose up -d --build
```

Compose crea la red interna, levanta la base de datos, espera a que esté lista (`healthcheck`), luego inicia el backend y por último el frontend.

- Aplicación: http://localhost:9080
- API del backend: http://localhost:8081 (por ejemplo `POST /buscarProyectos`)

Ver el estado y los registros:

```bash
docker compose ps
docker compose logs -f backend
```

## Detener

```bash
docker compose down        # detiene y elimina los contenedores (conserva los datos)
docker compose down -v     # además borra el volumen de la base de datos
```

## Configuración

Los servicios se configuran con variables de entorno, sin tocar el código:

| Variable | Servicio | Por defecto | Uso |
|---|---|---|---|
| `SPRING_DATASOURCE_URL` | backend | `jdbc:mariadb://db:3306/dbgestor` | Conexión a la base de datos |
| `SPRING_DATASOURCE_USERNAME` / `_PASSWORD` | backend | `gestor` / `gestor_local` | Credenciales de la base de datos |
| `GESTOR_BACKEND_URL` | frontend | `http://backend:8081` | Dirección del backend (propiedad `gestor.backend.url`) |
| `DB_ROOT_PASSWORD`, `DB_USER`, `DB_PASSWORD` | compose | `root_local`, `gestor`, `gestor_local` | Se pueden definir en un archivo `.env` para cambiar las credenciales |

Fuera de Docker (ejecución local con Maven) el frontend sigue usando `http://localhost:8081` por defecto.

## Imágenes por separado

```bash
docker build -t gestor-backend:1.0  ./Ejercicio5MyAPIGestor
docker build -t gestor-frontend:1.0 ./Ejercicio5Cliente
```

Las pruebas unitarias y la cobertura se ejecutan en los pipelines de Jenkins (`jenkins/`); la construcción de las imágenes solo empaqueta el JAR.
