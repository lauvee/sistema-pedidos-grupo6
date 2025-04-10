# Sistema de Gestión de Pedidos con Spring Boot y Kafka

## Descripción del Proyecto 📋

Este proyecto consiste en desarrollar un sistema de gestión de pedidos utilizando un stack tecnológico basado en **Spring Boot, JPA, Kafka y PostgreSQL**. Se implementa una arquitectura **MVC** para mantener la organización del código, y se integra **JWT con Spring Security** para el manejo seguro de autenticación y autorización.

El sistema permite la creación, modificación, consulta y eliminación de pedidos y usuarios, además de la comunicación asincrónica de eventos mediante **Apache Kafka**.

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
- **Spring Security & JWT**: Autenticación y autorización seguras.
- **PostgreSQL**: Motor de base de datos.
- **Postman**: Testing y documentación de endpoints.

## Instalación y Configuración 🚀

```bash
# Clona el repositorio
git clone https://github.com/tuusuario/sistema-pedidos.git
cd sistema-pedidos

# Configura la base de datos en application.properties o application.yml.
# Crea el archivo .env con las variables necesarias.

# Ejecuta la aplicación
mvn spring-boot:run
```

## Funcionalidades Principales ✅

- **Gestión de Usuarios**: Registro, inicio de sesión y autenticación con JWT.
- **Gestión de Productos**: CRUD de productos disponibles.
- **Gestión de Pedidos**: Creación, modificación y consulta de pedidos.
- **Notificaciones Asincrónicas**: Eventos de pedidos con Apache Kafka.
- **Seguridad**: Control de acceso basado en roles (usuario y administrador).
- **Documentación y Testing**: Pruebas con Postman y Swagger.

## Endpoints 🔗

Para mas documentacion visitar la cocumentacion de Swagger en el siguiente enlace: [Swagger](http://localhost:8080/swagger-ui/index.html#/)

### Usuarios

| Método | Endpoint                   | Descripción                | Código HTTP      |
| ------ | -------------------------- | -------------------------- | ---------------- |
| POST   | `/api/users/auth/register` | Registro de usuario        | `201 Created`    |
| POST   | `/api/users/auth/login`    | Inicio de sesión           | `200 OK`         |
| GET    | `/api/users/users`         | Obtener todos los usuarios | `200 OK`         |
| GET    | `/api/users/users/{id}`    | Obtener usuario por ID     | `200 OK`         |
| DELETE | `/api/users/{id}`          | Eliminar usuario por ID    | `204 No Content` |

### Productos

| Método | Endpoint          | Descripción                 | Código HTTP      |
| ------ | ----------------- | --------------------------- | ---------------- |
| GET    | `/productos`      | Obtener todos los productos | `200 OK`         |
| GET    | `/productos/{id}` | Obtener producto por ID     | `200 OK`         |
| POST   | `/productos`      | Crear nuevo producto        | `201 Created`    |
| PUT    | `/productos/{id}` | Actualizar producto por ID  | `200 OK`         |
| DELETE | `/productos/{id}` | Eliminar producto por ID    | `204 No Content` |

### Pedidos

| Método | Endpoint           | Descripción               | Código HTTP      |
| ------ | ------------------ | ------------------------- | ---------------- |
| GET    | `/all/pedidos`     | Obtener todos los pedidos | `200 OK`         |
| GET    | `/pedido/{id}`     | Obtener pedido por ID     | `200 OK`         |
| POST   | `/pedido`          | Crear nuevo pedido        | `201 Created`    |
| PUT    | `/pedido/{id}`     | Actualizar pedido por ID  | `200 OK`         |
| DELETE | `/del/pedido/{id}` | Eliminar pedido por ID    | `204 No Content` |

## Mensajes de Respuesta y Códigos de Estado HTTP implementados 🎯

- **200 OK**: Operación exitosa.
- **201 Created**: Recurso creado satisfactoriamente.
- **204 No Content**: Recurso eliminado.
- **401 Unauthorized**: Error de autenticación.
- **403 Forbidden**: Acceso no autorizado.
- **404 Not Found**: Recurso no encontrado.

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
