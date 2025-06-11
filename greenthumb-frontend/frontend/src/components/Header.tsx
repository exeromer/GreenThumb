"use client"; // Necesario para usar hooks como useCart

import Link from 'next/link';
import styles from './Header.module.css';
import { useCart } from '@/contexts/CartContext'; // Importar el hook

export const Header = () => {
    const { itemCount } = useCart(); // Usar el contexto para obtener itemCount

    return (
        <header className={styles.header}>
            <div className={styles.container}>
                <Link href="/" className={styles.logo}>
                    GreenThumb Market
                </Link>
                <nav className={styles.nav}>
                    <Link href="/products">Productos</Link>
                    <Link href="/categories">Categorías</Link>
                </nav>
                <div className={styles.actions}>
                    <Link href="/cart" className={styles.cartLink}>
                        Carrito ({itemCount})
                    </Link>
                    <Link href="/login">Login</Link>
                </div>
            </div>
        </header>
    );
};