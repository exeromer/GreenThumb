import React, { useEffect } from 'react';
import { useCart } from '../context/CartContext';
import { List, ListItem, ListItemText, Button } from '@mui/material';

export default function Cart() {
    const { cart, setCart } = useCart();

    // Aquí deberías cargar el carrito real del backend

    if (!cart.length) return <div>El carrito está vacío.</div>;

    return (
        <List>
            {cart.map((item, idx) => (
                <ListItem key={idx}>
                    <ListItemText
                        primary={`Producto ID: ${item.productoId}`}
                        secondary={`Cantidad: ${item.cantidad}`}
                    />
                    <Button color="error" onClick={() => setCart(cart.filter((_, i) => i !== idx))}>Eliminar</Button>
                </ListItem>
            ))}
        </List>
    );
}