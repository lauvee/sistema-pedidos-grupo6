# Sistema de Gestión de Pedidos con Spring Boot y Kafka

## Descripción del Proyecto 📋

Este proyecto consiste en desarrollar un sistema de gestión de pedidos utilizando un stack tecnológico basado en **Spring Boot, JPA, Kafka y PostgreSQL**. Se implementa una arquitectura **MVC** para mantener la organización del código, y se integra **JWT con Spring Security** para el manejo seguro de autenticación y autorización.

El sistema permite la creación, modificación, consulta y eliminación de pedidos y usuarios, además de la comunicación asincrónica de eventos mediante **Apache Kafka** junto con Kafka UI.

## Metodología de Trabajo 🛠️

Se sigue una metodología **Ágil (Scrum)** para la gestión y desarrollo de tareas. Se utilizan herramientas como **Trello** para la organización del trabajo y seguimiento del desarrollo.

## Equipo de Desarrollo 👥

@karlosvas  
@Rs-845  
@lauvee  
Creadores de Bytes Colaborativos: @devch-tech y @Jorexbp  
Canal de Twitch: [Bytes Colaborativos](https://www.twitch.tv/api/bytescolaborativos)

## Tecnologías Utilizadas ✨

- **Spring Boot**: Framework principal para el desarrollo del backend.
- **JPA (Hibernate)**: Mapeo objeto-relacional para la persistencia de datos.
- **Apache Kafka**: Comunicación asincrónica y notificaciones.
- **Swagger**: Documentación de la API REST.
- **Kafka UI**: Interfaz gráfica para la gestión de Kafka.
- **Docker**: Contenerización de servicios (Kafka, Zookeeper y Kafka UI).
- **Spring Security & JWT**: Autenticación y autorización seguras.
- **PostgreSQL**: Motor de base de datos.
- **Postman**: Testing y documentación de endpoints.

## Instalación y Configuración 🚀

```bash
# Clona el repositorio
git clone https://github.com/tuusuario/sistema-pedidos.git
cd sistema-pedidos

# Configura la base de datos en application.properties con el archivo application-demo.propierties con las variables necesarias, y renombralo a application.properties
# Estas son las variables que necesitas configurar:
jwt.secret.key=demo_key
spring.datasource.username=demo_user
spring.datasource.password=demo_password

# Enciende el servidor de Kafka, Kafka UI y Zookeeper con Docker:
docker-compose up -d # -d (opcional) para que se ejecute en segundo plano

# Ejecuta la aplicación
mvn spring-boot:run

# Puedes acceder a al adocumentacion de la api con Swagger en el siguiente enlace: http://localhost:8080/swagger-ui/index.html#/ y a la base de datos en memoria H2 en http://localhost:8080/h2-console
# O acceder a la interfaz de Kafka UI en http://localhost:8080/kafka-ui
```

## Funcionalidades Principales ✅

- **Gestión de Usuarios**: Registro, inicio de sesión y autenticación con JWT.
- **Gestión de Productos**: CRUD de productos disponibles.
- **Gestión de Pedidos**: Creación, modificación y consulta de pedidos.
- **Notificaciones Asincrónicas**: Eventos de pedidos con Apache Kafka.
- **Seguridad**: Control de acceso basado en roles (usuario y administrador).
- **Documentación y Testing**: Pruebas con Postman y Swagger.

- Monitorea la actividad de Kafka y los mensajes enviados a través de la interfaz de **Kafka UI**.
  Puedes acceder a la interfaz de Kafka UI en el siguiente enlace: [Kafka UI](http://localhost:8080/kafka-ui)

## Endpoints 🔗

Para mas documentacion visitar la cocumentacion de Swagger en el siguiente enlace: [Swagger](http://localhost:8080/swagger-ui/index.html#/)

### Usuarios

| Método | Endpoint                  | Descripción                | Código HTTP      |
| ------ | ------------------------- | -------------------------- | ---------------- |
| POST   | `/api/user/auth/register` | Registro de usuario        | `201 Created`    |
| POST   | `/api/user/auth/login`    | Inicio de sesión           | `200 OK`         |
| GET    | `/api/user/all`           | Obtener todos los usuarios | `200 OK`         |
| GET    | `/api/user/{email}`       | Obtener usuario por email  | `200 OK`         |
| DELETE | `/api/user/{id}`          | Eliminar usuario por ID    | `204 No Content` |

### Roles

| Método | Endpoint                    | Descripción               | Código HTTP      |
| ------ | --------------------------- | ------------------------- | ---------------- |
| POST   | `/api/role`                 | Crear nuevo rol           | `201 Created`    |
| GET    | `/api/role/{id}`            | Obtener rol por ID        | `200 OK`         |
| GET    | `/api/role/name{name}`      | Obtener rol por su nombre | `200 OK`         |
| GET    | `/api/role/all`             | Obtener todos los roles   | `200 OK`         |
| PUT    | `/api/role/{id}`            | Actualizar rol por ID     | `200 OK`         |
| PUT    | `/api/role/name/{name}`     | Actualizar rol por nombre | `200 OK`         |
| DELETE | `/api/role/del/{id}`        | Eliminar rol por ID       | `204 No Content` |
| DELETE | `/api/role/del/name/{name}` | Eliminar rol por nombre   | `204 No Content` |

### Productos

| Método | Endpoint                 | Descripción                 | Código HTTP      |
| ------ | ------------------------ | --------------------------- | ---------------- |
| GET    | `/api/producto/{id}`     | Obtener producto por ID     | `200 OK`         |
| GET    | `/api/producto/all`      | Obtener todos los productos | `200 OK`         |
| POST   | `/api/producto`          | Crear nuevo producto        | `201 Created`    |
| PUT    | `/api/producto/{id}`     | Actualizar producto         | `200 OK`         |
| DELETE | `/api/producto/del/{id}` | Eliminar producto por ID    | `204 No Content` |

### Pedidos

| Método | Endpoint               | Descripción               | Código HTTP      |
| ------ | ---------------------- | ------------------------- | ---------------- |
| GET    | `/api/pedido/{id}`     | Obtener pedido por ID     | `200 OK`         |
| GET    | `/api/pedido/all`      | Obtener todos los pedidos | `200 OK`         |
| POST   | `/api/pedido`          | Crear nuevo pedido        | `201 Created`    |
| PUT    | `/api/pedido`          | Actualizar pedido por ID  | `200 OK`         |
| DELETE | `/api/pedido/del/{id}` | Eliminar pedido por ID    | `204 No Content` |

## Mensajes de Respuesta y Códigos de Estado HTTP implementados 🎯

- **200 OK**: Operación exitosa.
- **201 Created**: Recurso creado satisfactoriamente.
- **204 No Content**: Recurso eliminado.
- **400 Bad Request**: Solicitud incorrecta (errores de validación, sintaxis inválida)
- **401 Unauthorized**: Error de autenticación.
- **403 Forbidden**: Acceso no autorizado.
- **404 Not Found**: Recurso no encontrado.
- **409 Conflict:** Conflicto con el estado actual del recurso (ya en tu enumeración)
- **405 Method Not Allowed:** Método HTTP no permitido para este recurso (ya en tu enumeración)
- **415 Unsupported Media Type:** El formato de los datos de la solicitud no es soportado
- **422 Unprocessable Entity:** La solicitud está bien formada pero tiene errores semánticos (ya en tu enumeración)
- **429 Too Many Requests:** Demasiadas solicitudes en un periodo de tiempo (rate limiting)
- **500 Not Found**: Error interno del servidor.
- **503 Service Unavailable:** Servicio temporalmente no disponible (ya en tu enumeración)

### Variables de entorno necesarias para la aplicación 🌍

En el caso de que no quieras usar un archivo .env, puedes configurar las variables directamente en el archivo `application.properties` con lla base de datos en memoria H2, descomentando su configuración y comenmentando la de PostgreSQL.

aplication.properties

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

.env

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/sistema_pedidos
SPRING_DATASOURCE_USERNAME=tu_usuario
SPRING_DATASOURCE_PASSWORD=tu_contraseña
```
