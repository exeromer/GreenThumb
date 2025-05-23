# GreenThumb Market - Backend

Bienvenido al backend del proyecto GreenThumb Market. Esta aplicación está construida con Spring Boot y se encarga de gestionar toda la lógica de negocio y persistencia de datos para un e-commerce de productos de jardinería.

## Descripción del Proyecto

"GreenThumb Market" es una plataforma de compra en línea especializada en la venta de plantas de interior y exterior, semillas, herramientas de jardinería y accesorios relacionados. El objetivo es crear un sistema robusto y fácil de usar que permita a los aficionados y profesionales de la jardinería adquirir productos de calidad.

Este backend maneja:
* Gestión de Usuarios (Clientes y Administradores) 
* Catálogo de Productos (Plantas, Herramientas, Semillas) con detalles específicos
* Categorización y Tipos de Producto
* Gestión de Precios (actuales e históricos) y Costos
* Control de Inventario y Movimientos de Stock
* Gestión de Pedidos (a implementar)
* Carrito de Compras (a implementar)
* Borrado Lógico de entidades

## Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3.4.5** (o la versión en tu `pom.xml`)
    * Spring Web (para API REST)
    * Spring Data JPA (para persistencia)
    * Spring Security (para seguridad)
* **MariaDB** (como base de datos)
* **Hibernate** (como proveedor JPA)
* **Maven** (para gestión de dependencias y build)

## Prerrequisitos

* **JDK 17** o superior instalado y configurado.
* **Apache Maven** instalado y configurado.
* Un servidor de base de datos **MariaDB** en ejecución. (Alternativamente, MySQL podría funcionar con ajustes menores en la configuración de dialécto si fuera necesario, pero MariaDB es el configurado).
* (Opcional) Un IDE como IntelliJ IDEA o Eclipse con soporte para Maven y Spring Boot.
* (Opcional) Una herramienta para probar APIs como Postman o Insomnia.

## Configuración

### 1. Base de Datos

* Asegúrate de que tu servidor MariaDB esté corriendo.
* La aplicación intentará conectarse a una base de datos llamada `greenthumb_db`. Si no existe, intentará crearla (debido a `createDatabaseIfNotExist=true` en la URL de conexión).
* Las credenciales y la URL de conexión se encuentran en `src/main/resources/application.properties`. Por defecto, son:
    * URL: `jdbc:mariadb://localhost:3306/greenthumb_db`
    * Usuario: `root`
    * Contraseña: (vacía por defecto, ajusta según tu configuración de MariaDB)

    Modifica estos valores si tu configuración de MariaDB es diferente.

### 2. Configuración de la Aplicación

* El archivo principal de configuración es `src/main/resources/application.properties`.
* `spring.jpa.hibernate.ddl-auto=update`: Hibernate intentará actualizar el esquema de la base de datos basándose en las entidades al iniciar la aplicación. Para un primer uso o desarrollo, esto es conveniente. En producción, se recomienda usar herramientas de migración como Flyway o Liquibase.

## Cómo Ejecutar el Proyecto

1.  **Clonar el Repositorio** (si aplica)
    ```bash
    git clone <url-del-repositorio>
    cd greenthumb-backend
    ```

2.  **Construir con Maven**
    Desde la raíz del proyecto (`greenthumb-backend`), ejecuta:
    ```bash
    mvn clean install
    ```
    Esto compilará el código y descargará las dependencias.

3.  **Ejecutar la Aplicación Spring Boot**
    Puedes ejecutar la aplicación de varias maneras:
    * **Usando el plugin de Maven Spring Boot:**
        ```bash
        mvn spring-boot:run
        ```
    * **Ejecutando el JAR empaquetado (después de `mvn clean install`):**
        ```bash
        java -jar target/greenthumb-backend-0.0.1-SNAPSHOT.jar
        ```
    * **Desde tu IDE:** Busca la clase principal `GreenthumbBackendApplication.java` y ejecútala como una aplicación Java.

4.  **Verificación**
    * Una vez iniciada, la aplicación estará disponible (por defecto) en `http://localhost:8080`.
    * Revisa la consola para ver los logs de inicio, incluyendo los mensajes del `ApplicationStartupListener` y la inicialización de datos por `DataInitializer`.
    * La base de datos `greenthumb_db` debería haber sido creada/actualizada y poblada con los datos iniciales.

## Datos Iniciales

La aplicación utiliza una clase `DataInitializer` para cargar datos maestros y de ejemplo al arrancar. Esto incluye:
* Un usuario administrador (`admin@greenthumb.com` / contraseña `admin123` - ¡CAMBIAR EN PRODUCCIÓN!).
* Estados de pedido.
* Categorías de productos.
* Tipos de producto.
* Niveles de luz, frecuencias de riego.
* Tipos de movimiento de stock.
* Clientes de ejemplo.
* Productos de ejemplo con precios, costos, detalles e imágenes (las imágenes deben estar en `src/main/resources/static/uploads/productos/` y las URLs en `DataInitializer` deben coincidir).

## Endpoints de la API (Ejemplos)

La API sigue el prefijo `/api`. (Anteriormente `/api/v1`, ajustado según tu preferencia).

* **Categorías:**
    * `GET /api/categorias`
    * `GET /api/categorias/{id}`
    * `POST /api/categorias`
    * `PUT /api/categorias/{id}`
    * `DELETE /api/categorias/{id}` (Borrado lógico)
* **Productos:**
    * `GET /api/productos` (soporta paginación y filtros: `?page=0&size=5&sort=nombreProducto,asc&categoriaId=1&nombre=Helecho`)
    * `GET /api/productos/{id}`
    * `POST /api/productos`
    * `PUT /api/productos/{id}`
    * `DELETE /api/productos/{id}` (Borrado lógico)

Consulta el código de los controladores (`CategoriaController`, `ProductoController`) para más detalles sobre los cuerpos de solicitud y respuesta esperados.

## Próximos Pasos (Desarrollo)

* Implementación de endpoints para Carrito de Compras.
* Implementación de endpoints para Pedidos.
* Refinamiento de la configuración de Spring Security (autenticación basada en tokens JWT, autorización por roles).
* Implementación de la subida de archivos para imágenes de producto.
* Desarrollo del frontend en React.

## Estructura del Proyecto (Simplificada)