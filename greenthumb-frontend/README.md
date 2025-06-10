# GreenThumb E-Commerce - Frontend 🌿

Esta es la aplicación de cliente (frontend) para el proyecto GreenThumb. Es una Single Page Application (SPA) construida con React que consume la API del backend.

## Tecnologías Utilizadas
* **React 18**
* **React Router** - Para la navegación y el manejo de rutas.
* **Auth0 React SDK** - Para integrar la autenticación y el login con Auth0.
* **CSS** - Para los estilos.

---

## Configuración del Entorno

Para poder ejecutar el proyecto, necesitas crear un archivo de configuración para las variables de entorno.

1.  En la raíz de la carpeta `greenthumb-frontend`, crea un archivo llamado **`.env`**.
2.  Pega el siguiente contenido en el archivo `.env` y reemplaza los valores con tus propias credenciales de Auth0.

    ```env
    # URL base de la API del backend
    REACT_APP_API_URL=http://localhost:8080/api

    # --- Credenciales de Auth0 ---
    # Obtenidas del panel de Auth0, en Applications -> Applications -> [Tu App de tipo Single Page Application]

    # Dominio de tu tenant de Auth0
    REACT_APP_AUTH0_DOMAIN=TU_DOMINIO_AUTH0

    # Client ID de tu aplicación SPA en Auth0
    REACT_APP_AUTH0_CLIENT_ID=TU_CLIENT_ID_SPA

    # Audience (Identificador) de la API que creaste en Auth0 (debe ser el mismo que en el backend)
    REACT_APP_AUTH0_AUDIENCE=[https://api.greenthumb.com](https://api.greenthumb.com)
    ```
    **Importante:** Nunca subas tu archivo `.env` a un repositorio público de Git.

---

## Cómo Ejecutar la Aplicación

1.  **Clona el repositorio.**
2.  **Configura** el archivo `.env` como se describió anteriormente.
3.  Abre una terminal en la carpeta `greenthumb-frontend` y ejecuta el siguiente comando para instalar las dependencias:
    ```bash
    npm install
    ```
4.  Una vez finalizada la instalación, ejecuta el siguiente comando para iniciar la aplicación:
    ```bash
    npm start
    ```
La aplicación se abrirá automáticamente en tu navegador en `http://localhost:3000`.

---

## Cuentas de Prueba

Puedes usar las siguientes cuentas (previamente creadas en Auth0) para probar la aplicación.

* **Usuario Administrador:**
    * **Email:** `luisluis@admin.com`
    * **Contraseña:** `Hola1234`
    *(Este usuario tiene el rol "ADMIN" en Auth0 y podrá acceder a futuras funcionalidades de administración).*

* **Usuarios Clientes:**
    * **Email:** `anagarcia@example.com` / `cristianoronaldo@cliente.com`
    * **Contraseña:** `Hola1234`
    *(Estos usuarios serán redirigidos para completar su perfil la primera vez que inicien sesión).*