import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { Auth0Provider } from '@auth0/auth0-react'; // Asegúrate de que la importación es de la librería
import { CartProvider } from './context/CartContext';
import { BrowserRouter } from 'react-router-dom';
import { UserProvider } from './context/UserContext';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Auth0Provider
            domain="dev-e7rix4gh8kwqcqy1.us.auth0.com"      // Tu Domain de Auth0 (SPA)
            clientId="yDcrX9qCXNtSvmK6VvpZolyCJII5ZczR" // Tu Client ID de Auth0 (SPA)
            authorizationParams={{
                redirect_uri: window.location.origin,
                audience: "https://api.greenthumb.com"      // El Audience/Identifier de tu API
            }}
        >
            <UserProvider>
                <BrowserRouter>
                    <CartProvider>
                        <App />
                    </CartProvider>
                </BrowserRouter>
            </UserProvider>
        </Auth0Provider>
    </React.StrictMode >
);
