# identity-service

Microservicio de identidad de Technova E-Commerce, desarrollado con Spring Boot.

## Responsabilidades

- Registro e inicio de sesión de usuarios.
- Gestión de usuarios y roles.
- Almacenamiento seguro de contraseñas.
- Emisión y validación de tokens JWT.
- Consulta interna de información del usuario para otros servicios mediante una API protegida con `X-Internal-Api-Key`.

## Requisitos

- Java 21
- Maven (o Maven Wrapper)
- MySQL y Config Server, según la configuración del entorno

## Ejecución

Configura los valores requeridos por el entorno, como `INTERNAL_API_KEY`, y ejecuta:

```bash
./mvnw spring-boot:run
```

La configuración centralizada es opcional en `application.properties` y apunta por defecto a `http://localhost:8888`.
