# GreenThumb E-Commerce - Backend 🍃

Este es el backend para el proyecto de e-commerce GreenThumb. Provee una API REST para gestionar productos, usuarios, pedidos y la lógica de negocio de la tienda.

## Tecnologías Utilizadas
* **Java 17**
* **Spring Boot 3**
* **Spring Web** - Para la creación de la API REST.
* **Spring Data JPA** - Para la persistencia de datos con MariaDB.
* **Spring Security** - Para la protección de endpoints.
* **Auth0** - Como proveedor de identidad para la autenticación y autorización basada en tokens JWT.
* **MariaDB/MySQL** - Como motor de base de datos.
* **Maven** - Para la gestión de dependencias y construcción del proyecto.

---

## Configuración del Entorno

Para poder ejecutar el proyecto, necesitas configurar las siguientes variables en el archivo `src/main/resources/application.properties`.

### 1. Configuración de la Base de Datos
Asegúrate de tener una base de datos MariaDB o MySQL corriendo y crea una base de datos (ej. `greenthumb_db`). Luego, configura estas propiedades:

```properties
# URL de conexión a tu base de datos
spring.datasource.url=jdbc:mariadb://localhost:3306/greenthumb_db
# Usuario de la base de datos
spring.datasource.username=root
# Contraseña de la base de datos
spring.datasource.password=tu_contraseña
```

### 2. Configuración de Seguridad (Auth0)
Estas credenciales las obtienes de tu panel de Auth0, en la sección de **APIs**.

```properties
# Dominio de tu tenant de Auth0 (ej: dev-xxxxxxxx.us.auth0.com)
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://TU_DOMINIO_AUTH0/
# Identificador (Audience) de tu API en Auth0
spring.security.oauth2.resourceserver.jwt.audiences=[https://api.greenthumb.com](https://api.greenthumb.com)
```

### 3. Perfil de Desarrollo (Sin Seguridad)
Para trabajar en endpoints sin necesidad de un token, puedes activar el perfil `dev`.

```properties
# Descomenta la siguiente línea para desactivar la seguridad
# spring.profiles.active=dev
```

---

## Cómo Ejecutar la Aplicación

1.  **Clona el repositorio.**
2.  **Configura** el archivo `application.properties` como se describió anteriormente.
3.  **Ejecuta el script SQL** `greenthumb_db (3).sql` para crear la estructura de tablas inicial. La aplicación está configurada para actualizar el esquema (`ddl-auto: update`), pero es una buena práctica tener una base inicial.
4.  Puedes ejecutar la aplicación de dos maneras:
    * **Desde tu IDE (IntelliJ/Eclipse):** Simplemente ejecuta la clase principal `GreenthumbBackendApplication.java`.
    * **Desde la terminal (usando Maven):**
        ```bash
        ./mvnw spring-boot:run
        ```
El servidor se iniciará en `http://localhost:8080`.

---

## Cuentas de Prueba

Puedes usar las siguientes cuentas (previamente creadas en Auth0) para probar la aplicación.

* **Usuario Administrador:**
    * **Email:** `luisluis@admin.com`
    * **Contraseña:** `Hola1234`
    *(Este usuario tiene el rol "ADMIN" en Auth0 y puede acceder a los endpoints protegidos).*

* **Usuarios Clientes:**
    * **Email:** `anagarcia@example.com` / `cristianoronaldo@cliente.com`
    * **Contraseña:** `Hola1234`
    *(Estos usuarios no tienen el rol de Admin y serán bloqueados de las rutas de administración).*