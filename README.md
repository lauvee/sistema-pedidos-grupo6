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

| Método | Endpoint         | Descripción                 | Código HTTP      |
| ------ | ---------------- | --------------------------- | ---------------- |
| POST   | `/auth/register` | Registro de usuario         | `201 Created`    |
| POST   | `/auth/login`    | Inicio de sesión            | `200 OK`         |
| GET    | `/productos`     | Obtener todos los productos | `200 OK`         |
| POST   | `/pedidos`       | Crear nuevo pedido          | `201 Created`    |
| PUT    | `/pedidos/{id}`  | Actualizar pedido           | `200 OK`         |
| DELETE | `/pedidos/{id}`  | Eliminar pedido             | `204 No Content` |

## Mensajes de Respuesta y Códigos de Estado HTTP 🎯

- **200 OK**: Operación exitosa.
- **201 Created**: Recurso creado satisfactoriamente.
- **204 No Content**: Recurso eliminado.
- **401 Unauthorized**: Error de autenticación.
- **403 Forbidden**: Acceso no autorizado.
- **404 Not Found**: Recurso no encontrado.
