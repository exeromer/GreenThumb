import React from 'react';
import { AppBar, Toolbar, Typography, Button, IconButton } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
    const { user, logout } = useAuth();

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" component={Link} to="/" sx={{ flexGrow: 1, color: 'inherit', textDecoration: 'none' }}>
                    GreenThumb
                </Typography>
                <Button color="inherit" component={Link} to="/">Catálogo</Button>
                <IconButton color="inherit" component={Link} to="/carrito">
                    <ShoppingCartIcon />
                </IconButton>
                {user ? (
                    <>
                        {user.rol === 'admin' && <Button color="inherit" component={Link} to="/admin">Admin</Button>}
                        <Button color="inherit" onClick={logout}>Salir</Button>
                    </>
                ) : (
                    <>
                        <Button color="inherit" component={Link} to="/login">Ingresar</Button>
                        <Button color="inherit" component={Link} to="/register">Registrarse</Button>
                    </>
                )}
            </Toolbar>
        </AppBar>
    );
}