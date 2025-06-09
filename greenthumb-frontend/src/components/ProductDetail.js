import React, { useEffect, useState } from 'react';
import { fetchProductById } from '../api/api';
import { useParams } from 'react-router-dom';
import { Card, CardMedia, CardContent, Typography, Button } from '@mui/material';
import { useCart } from '../context/CartContext';

export default function ProductDetail() {
    const { id } = useParams();
    const [producto, setProducto] = useState(null);
    const { cart, setCart } = useCart();

    useEffect(() => {
        fetchProductById(id).then(res => setProducto(res.data));
    }, [id]);

    if (!producto) return <div>Cargando...</div>;

    const handleAddToCart = () => {
        // Aquí deberías llamar a la API y actualizar el contexto
        setCart([...cart, { productoId: producto.productoId, cantidad: 1 }]);
    };

    return (
        <Card sx={{ maxWidth: 600, margin: 'auto', mt: 4 }}>
            <CardMedia
                component="img"
                height="300"
                image={producto.imagenes?.[0]?.urlImagen || '/placeholder.jpg'}
                alt={producto.nombreProducto}
            />
            <CardContent>
                <Typography variant="h4">{producto.nombreProducto}</Typography>
                <Typography variant="body1">{producto.descripcionGeneral}</Typography>
                <Typography variant="h6" color="primary">${producto.precioActual?.precioVenta}</Typography>
                <Button variant="contained" onClick={handleAddToCart}>Agregar al carrito</Button>
            </CardContent>
        </Card>
    );
}