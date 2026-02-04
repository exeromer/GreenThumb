# 🌱 GreenThumb Market – Proyecto Final de Base de Datos

**GreenThumb Market** es un proyecto **Full Stack** desarrollado como **Proyecto Final de la materia Base de Datos**, cuyo eje central es el **diseño, implementación y explotación de una base de datos relacional**, utilizando un backend en **Spring Boot** como capa de acceso, validación y exposición de los datos mediante una API REST.

El sistema modela un escenario realista de **comercio electrónico de productos de jardinería**, priorizando la **integridad, normalización y consistencia de los datos**.

---

## 🎯 Objetivo del Proyecto

El objetivo principal del proyecto es:

- Diseñar una **base de datos relacional completa y normalizada**
- Implementar reglas de negocio a nivel de datos
- Garantizar integridad referencial
- Manejar información histórica (precios, stock)
- Exponer los datos mediante un backend robusto y desacoplado

El frontend funciona como **interfaz de prueba y consumo**, pero **no es el foco principal del trabajo**.

---

## 🗄️ Base de Datos (Eje Central)

La base de datos fue diseñada para soportar un sistema de ventas real, contemplando:

### ✔️ Características del modelo
- Modelo relacional normalizado
- Claves primarias y foráneas
- Integridad referencial
- Borrado lógico
- Manejo de estados
- Historización de datos críticos

### 📦 Entidades principales
- **Producto**
- **Categoría**
- **Marca**
- **Cliente**
- **Pedido**
- **DetallePedido**
- **MovimientoStock**

### 📈 Aspectos destacados
- Control de stock mediante movimientos
- Precios históricos por producto
- Relación maestro–detalle en pedidos
- Separación clara entre datos maestros y transaccionales
- Preparado para reportes y consultas históricas

---

## ⚙️ Backend – API REST

El backend fue desarrollado para **respaldar el modelo de datos**, aplicando las reglas de negocio y permitiendo su acceso controlado.

### Tecnologías utilizadas
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **Spring Security**
- **MySQL / MariaDB**
- **Maven**

### Funciones del backend
- Persistencia de datos
- Validaciones
- Control de estados
- Gestión de relaciones
- Exposición de endpoints REST
- Seguridad y control de acceso

El backend actúa como una **capa intermedia entre la base de datos y el frontend**, garantizando la correcta manipulación de la información.

---

## 🧱 Arquitectura General
Frontend (React)
↓
API REST (Spring Boot)
↓
Base de Datos Relacional (MySQL / MariaDB)

El diseño desacoplado permite:
- Escalabilidad
- Mantenimiento sencillo
- Reutilización del backend
- Posible reemplazo del frontend sin afectar la base de datos

---

## 🖥️ Frontend (Complementario)

El frontend fue desarrollado únicamente como **capa visual y de prueba** para consumir la API.

- React
- Material UI
- Consumo de API REST

No forma parte del objetivo principal del proyecto de base de datos.

---

## 🚀 Ejecución del Proyecto

### Backend
1. Configurar la base de datos en `application.properties`
2. Ejecutar:
   ```bash
   mvn spring-boot:run
3.API disponible en:

http://localhost:8080

Frontend
npm install
npm start

📌 Estado del Proyecto

✅ Proyecto finalizado
✅ Entregado como Proyecto Final de Base de Datos
✅ Modelo de datos completo y funcional
✅ Backend implementado y operativo

🧠 Aprendizajes Aplicados

Diseño de bases de datos relacionales

Normalización

Integridad referencial

Modelado de escenarios reales

Uso de JPA como capa de persistencia

Separación de responsabilidades

Arquitectura REST
