"use client";

import { createContext, useState, useContext, ReactNode, useEffect, useMemo, useCallback } from 'react';
import { CarritoItem } from '@/libs/types'; // Asegúrate de que este tipo esté definido correctamente
import { getCart, addToCart, updateCartItemQuantity, removeFromCart, clearCart } from '@/services/api';

// La interfaz del contexto no necesita cambiar
interface CartContextType {
    items: CarritoItem[];
    itemCount: number;
    total: number;
    loading: boolean;
    error: string | null;
    handleAddToCart: (productoId: number, cantidad: number) => Promise<void>;
    handleUpdateQuantity: (productoId: number, nuevaCantidad: number) => Promise<void>;
    handleRemoveItem: (productoId: number) => Promise<void>;
    handleClearCart: () => Promise<void>;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider = ({ children }: { children: ReactNode }) => {
    const [items, setItems] = useState<CarritoItem[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchInitialCart = async () => {
            try {
                setLoading(true);
                const cartItems = await getCart();
                setItems(cartItems || []); // Asegurarse de que sea un array si la respuesta es null
                setError(null);
            } catch (err) {
                console.error("Failed to fetch cart:", err);
                setError("No se pudo cargar el carrito.");
            } finally {
                setLoading(false);
            }
        };
        fetchInitialCart();
    }, []);

    const handleAddToCart = useCallback(async (productoId: number, cantidad: number) => {
        try {
            const newItem = await addToCart(productoId, cantidad);
            setItems(prevItems => {
                const existingItem = prevItems.find(item => item.productoId === productoId);
                if (existingItem) {
                    return prevItems.map(item => item.productoId === productoId ? { ...item, cantidad: item.cantidad + cantidad } : item);
                } else {
                    return [...prevItems, newItem];
                }
            });
            // Recargar el carrito para obtener los subtotales correctos si la API los calculara
            // Por ahora, manejamos el estado localmente
        } catch (err) {
            console.error("Failed to add to cart:", err);
        }
    }, []);

    const handleUpdateQuantity = useCallback(async (productoId: number, nuevaCantidad: number) => {
        if (nuevaCantidad < 1) {
            await handleRemoveItem(productoId);
            return;
        }
        try {
            const updatedItem = await updateCartItemQuantity(productoId, nuevaCantidad);
            setItems(prevItems => prevItems.map(item => item.productoId === productoId ? { ...item, cantidad: nuevaCantidad } : item));
        } catch (err) {
            console.error("Failed to update quantity:", err);
        }
    }, []);

    const handleRemoveItem = useCallback(async (productoId: number) => {
        try {
            await removeFromCart(productoId);
            setItems(prevItems => prevItems.filter(item => item.productoId !== productoId));
        } catch (err) {
            console.error("Failed to remove item:", err);
        }
    }, []);

    const handleClearCart = useCallback(async () => {
        try {
            await clearCart();
            setItems([]);
        } catch (err) {
            console.error("Failed to clear cart:", err);
        }
    }, []);

    // --- CAMBIO CLAVE: Calcular el total correctamente ---
    const { itemCount, total } = useMemo(() => {
        const count = items.reduce((sum, item) => sum + item.cantidad, 0);
        const newTotal = items.reduce((sum, item) => sum + (item.precioUnitario * item.cantidad), 0);
        return { itemCount: count, total: newTotal };
    }, [items]);

    const value = {
        items,
        itemCount,
        total,
        loading,
        error,
        handleAddToCart,
        handleUpdateQuantity,
        handleRemoveItem,
        handleClearCart,
    };

    return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
};

export const useCart = () => {
    const context = useContext(CartContext);
    if (context === undefined) {
        throw new Error('useCart must be used within a CartProvider');
    }
    return context;
};