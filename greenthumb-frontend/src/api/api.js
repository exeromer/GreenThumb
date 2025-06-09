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

// ...agrega más funciones según tus endpoints

