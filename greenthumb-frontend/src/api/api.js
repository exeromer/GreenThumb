import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Cambia el puerto si tu backend usa otro

export const fetchProducts = (params) =>
    axios.get(`${API_URL}/productos`, { params });

export const fetchProductById = (id) =>
    axios.get(`${API_URL}/productos/${id}`);

export const fetchCart = (clienteId) =>
    axios.get(`${API_URL}/carrito/${clienteId}`);

export const addToCart = (clienteId, productoId, cantidad) =>
    axios.post(`${API_URL}/carrito/${clienteId}/agregar`, null, {
        params: { productoId, cantidad }
    });

export const checkUserRegistration = async (token) => {
    try {
        const response = await fetch(`${API_URL}/usuarios/me`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (response.status === 404) {
            return { needsRegistration: true };
        }
        if (!response.ok) {
            throw new Error('Error al verificar el registro del usuario');
        }
        const data = await response.json();
        return { needsRegistration: false, user: data };
    } catch (error) {
        console.error('Error en checkUserRegistration:', error);
        throw error;
    }
};

export const registrarNuevoCliente = async (datosRegistro, token) => {
    try {
        const response = await fetch(`${API_URL}/usuarios/registrar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(datosRegistro)
        });
        if (!response.ok) {
            const errorData = await response.text();
            throw new Error(`Error al registrar el cliente: ${errorData}`);
        }
        return await response.json(); // El backend devuelve el UsuarioDTO creado
    } catch (error) {
        console.error('Error en registrarNuevoCliente:', error);
        throw error;
    }
};