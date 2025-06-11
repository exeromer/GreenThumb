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

## Avances Recientes: Módulo de Carrito de Compras (Mayo 2025)

Hemos logrado un avance significativo al implementar la funcionalidad completa del **carrito de compras**. Esto permite a los usuarios gestionar sus productos deseados antes de la compra final, brindando una experiencia más interactiva y robusta.

### Resumen de Implementación:

* **Lógica del Carrito Centralizada:** Se crearon y modificaron entidades clave como `CarritoItem`, `CarritoItemId`, y se actualizaron las relaciones en `Cliente` y `Producto` para soportar la funcionalidad del carrito.
* **API REST Robusta:** Se desarrolló un `CarritoService` con validación de stock y se expuso a través de un `CarritoController` con endpoints claros para añadir, obtener, actualizar, eliminar y vaciar el carrito.
* **Información Detallada en Carrito:** Cada ítem del carrito ahora incluye el nombre del producto, el precio unitario actual (obtenido de `PrecioProductoActual`) y la URL de la imagen principal del producto (gestionada por `ImagenesProducto`).
* **Consistencia de Datos:** Se estandarizaron los tipos de IDs a `Integer` en todas las capas del módulo para asegurar la coherencia y evitar errores de tipo.
* **Datos de Prueba Mejorados:** El `DataInitializer` ha sido actualizado para crear datos de prueba más completos, incluyendo administradores y productos con sus imágenes asociadas, facilitando la validación.

### Cómo Probar los Endpoints del Carrito:

Asegúrate de que la aplicación Spring Boot esté corriendo (ver sección "Configuración y Ejecución"). Utiliza **Postman** (recomendado) o tu navegador para enviar peticiones a los siguientes endpoints.

**URL Base del Carrito:** `http://localhost:8080/api/carrito`

Para las pruebas, puedes usar los IDs generados por `DataInitializer` (verifica la consola al inicio de la app o phpMyAdmin). Un cliente de ejemplo tendrá `ID = 1`, y productos como "Monstera Deliciosa" y "Zamioculcas Zamiifolia" tendrán `ID = 1` y `ID = 2` respectivamente.

1.  **Agregar Producto al Carrito**
    * **Función:** Añade o actualiza la cantidad de un producto en el carrito de un cliente. Incluye validación de stock.
    * **Método:** `POST`
    * **URL:** `/{clienteId}/agregar`
    * **Parámetros (Query):** `productoId={ID_DEL_PRODUCTO}&cantidad={CANTIDAD}`
    * **Ejemplo:** `POST http://localhost:8080/api/carrito/1/agregar?productoId=1&cantidad=2`
    * **Respuestas:** `201 Created` (éxito), `400 Bad Request` (stock insuficiente, cantidad inválida), `500 Internal Server Error` (producto/cliente no encontrado).

2.  **Obtener Carrito de un Cliente**
    * **Función:** Consulta todos los productos en el carrito de un cliente.
    * **Método:** `GET`
    * **URL:** `/{clienteId}`
    * **Ejemplo:** `GET http://localhost:8080/api/carrito/1` (se puede probar en el navegador)
    * **Respuestas:** `200 OK` (lista de ítems), `204 No Content` (carrito vacío), `404 Not Found` (cliente no encontrado).

3.  **Actualizar Cantidad de Producto en el Carrito**
    * **Función:** Modifica la cantidad de un producto existente en el carrito.
    * **Método:** `PUT`
    * **URL:** `/{clienteId}/actualizar`
    * **Parámetros (Query):** `productoId={ID_DEL_PRODUCTO}&nuevaCantidad={NUEVA_CANTIDAD}`
    * **Ejemplo:** `PUT http://localhost:8080/api/carrito/1/actualizar?productoId=1&nuevaCantidad=5`
    * **Respuestas:** `200 OK` (ítem actualizado), `204 No Content` (si `nuevaCantidad` es 0 y se elimina el ítem), `400 Bad Request` (stock insuficiente), `404 Not Found` (ítem no encontrado en el carrito).

4.  **Eliminar Producto del Carrito**
    * **Función:** Quita un producto específico del carrito de un cliente.
    * **Método:** `DELETE`
    * **URL:** `/{clienteId}/eliminar/{productoId}`
    * **Ejemplo:** `DELETE http://localhost:8080/api/carrito/1/eliminar/2`
    * **Respuestas:** `204 No Content` (éxito), `404 Not Found` (ítem no encontrado).

5.  **Vaciar Carrito Completo**
    * **Función:** Elimina todos los productos del carrito de un cliente.
    * **Método:** `DELETE`
    * **URL:** `/{clienteId}/vaciar`
    * **Ejemplo:** `DELETE http://localhost:8080/api/carrito/1/vaciar`
    * **Respuestas:** `204 No Content` (éxito), `404 Not Found` (cliente no encontrado).

6.  **Confirmar Checkout (Crear Pedido desde Carrito)**
    * **Función:** Convierte los ítems del carrito de un cliente en un pedido final, ajustando el stock de los productos.
    * **Método:** `POST`
    * **URL:** `/{clienteId}/checkout`
    * **Headers:** `Content-Type: application/json`
    * **Body (raw, JSON):**
        ```json
        {
            "metodoPago": "Tarjeta de Credito",
            "notasCliente": "Entregar antes de las 5 PM."
        }
        ```
    * **Ejemplo:** `POST http://localhost:8080/api/carrito/1/checkout`
    * **Respuestas:**
        * `201 Created`: Pedido creado exitosamente. Retorna los detalles del `PedidoDTO`.
        * `400 Bad Request`: Carrito vacío, stock insuficiente para algún producto, o datos de entrada inválidos.
        * `415 Unsupported Media Type`: Si el `Content-Type` de la solicitud no es `application/json`.
        * `500 Internal Server Error`: Errores internos (ej. cliente no encontrado, tipos de movimiento o estados de pedido no configurados correctamente en `DataInitializer`).
    * **Verificación Post-Checkout:**
        * **Base de datos:** Confirma la creación de nuevos registros en las tablas `pedidos` y `detallespedido`.
        * **Stock:** Verifica que el `stockActual` en la tabla `productos` se haya reducido.
        * **Movimientos de Stock:** Revisa la tabla `movimientosstock` para ver los registros de tipo "Venta Cliente".
        * **Carrito:** Confirma que la tabla `carritoitems` ya no contiene los ítems del cliente procesado.

**Verificación en Base de Datos (phpMyAdmin):** Después de cada prueba, puedes acceder a phpMyAdmin y ejecutar `SELECT * FROM carritoitems;`, `SELECT * FROM pedidos;`, `SELECT * FROM detallespedido;`, `SELECT * FROM productos;`, `SELECT * FROM movimientosstock;` para confirmar que los cambios se reflejan en las tablas.

¡Excelente iniciativa de hacer un commit descriptivo! Es crucial para mantener un historial de proyecto claro.

Aquí tienes mis sugerencias para el README.md y el mensaje de commit, considerando los cambios que has hecho para integrar Mercado Pago en modo de prueba:

Sugerencias para README.md
Añade una nueva sección o actualiza una existente (ej. "Configuración" o "Características") con la siguiente información:

Integración con Mercado Pago (Modo Prueba)
Este backend incluye una integración con la API de Mercado Pago para la creación de preferencias de pago y la simulación de webhooks, exclusivamente para propósitos de prueba y desarrollo. No está configurado para procesar pagos reales en este momento.

Configuración:

Para utilizar la integración de Mercado Pago, asegúrate de configurar las siguientes propiedades en greenthumb-backend/src/main/resources/application.properties:

Properties

mercadopago.access_token=YOUR_MERCADO_PAGO_ACCESS_TOKEN
mercadopago.client_id=YOUR_MERCADO_PAGO_CLIENT_ID
mercadopago.client_secret=YOUR_MERCADO_PAGO_CLIENT_SECRET
Puedes obtener las credenciales de prueba desde tu cuenta de desarrollador de Mercado Pago.
Endpoints Disponibles:

POST /api/mercadopago/create-preference:

Descripción: Permite crear una preferencia de pago en Mercado Pago. Recibe detalles de los ítems del carrito y URLs de redirección.
Cuerpo de la Solicitud (Ejemplo):
JSON

{
    "clienteId": 1,
    "metodoPago": "MercadoPago",
    "notasCliente": "Pago online de prueba",
    "successUrl": "http://localhost:3000/pago-exitoso",
    "failureUrl": "http://localhost:3000/pago-fallido",
    "pendingUrl": "http://localhost:3000/pago-pendiente",
    "items": [
        {
            "id": "1",
            "title": "Producto de Prueba 1",
            "description": "Descripción del Producto 1",
            "quantity": 1,
            "unitPrice": 10.00
        }
    ]
}
Respuesta: Retorna el ID de la preferencia de Mercado Pago, así como init_point y sandbox_init_point (URL para redirigir al usuario al flujo de pago de Mercado Pago).
POST /api/mercadopago/webhook:

Descripción: Este endpoint simula la recepción de notificaciones IPN (Instant Payment Notification) de Mercado Pago. En un entorno real, Mercado Pago enviaría automáticamente un payload a esta URL para informar sobre el estado de un pago (aprobado, pendiente, rechazado, etc.).
Uso para Pruebas: Puedes enviar manualmente un POST a este endpoint (por ejemplo, con Postman) para simular diferentes estados de pago.
Cuerpo de la Solicitud (Ejemplo para un pago aprobado):
JSON

{
    "id": "PAYMENT_ID_SIMULADO",
    "topic": "payment",
    "resource": "https://api.mercadopago.com/v1/payments/PAYMENT_ID_SIMULADO",
    "type": "payment",
    "data": {
        "id": "PAYMENT_ID_SIMULADO",
        "status": "approved",
        "external_reference": "ID_DE_PREFERENCIA_O_PEDIDO"
    }
}


---

## Próximos Pasos (Desarrollo)

* Implementación de endpoints para Carrito de Compras.
* Implementación de endpoints para Pedidos.
* Refinamiento de la configuración de Spring Security (autenticación basada en tokens JWT, autorización por roles).
* Implementación de la subida de archivos para imágenes de producto.
* Desarrollo del frontend en React.

## Estructura del Proyecto (Simplificada)