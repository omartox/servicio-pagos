# Servicio Pagos



## 1.-  Cosntrucción del proyecto Gradle

Compilar el proyecto:

```bash
./gradlew clean build
```
---


## 2.-  Levantar los contenedores

Desde la carpeta donde está `docker-compose.yml`:

```bash
docker compose up -d --build
```
---

## 3.-  URLs de los servicios

Aplicacion Spring Boot:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```
---
## 4.-  RabbitMQ

Panel:

```
http://localhost:15672
```
Credenciales:

```
Usuario: admin
Contraseña: admin
```
---

## 5.-  MySQL

Equipo local:

```
Host: localhost
Puerto: 3306
Base de datos: pagos_db
Usuario: admin
Contraseña: pass
```

Desde Contenedor :

```
Host: mysql
Puerto: 3306
Base de datos: pagos_db
Usuario: admin
Contraseña: pass
```
---
