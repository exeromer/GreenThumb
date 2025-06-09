import React, { useState } from 'react';
import { TextField, Button, Box, Typography } from '@mui/material';

export default function Register() {
    const [email, setEmail] = useState('');
    const [contrasena, setContrasena] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        // Aquí deberías llamar a tu backend para registrar
        alert('Registro simulado');
    };

    return (
        <Box sx={{ maxWidth: 400, mx: 'auto', mt: 4 }}>
            <Typography variant="h5">Registrarse</Typography>
            <form onSubmit={handleSubmit}>
                <TextField label="Email" fullWidth margin="normal" value={email} onChange={e => setEmail(e.target.value)} />
                <TextField label="Contraseña" type="password" fullWidth margin="normal" value={contrasena} onChange={e => setContrasena(e.target.value)} />
                <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>Registrarse</Button>
            </form>
        </Box>
    );
}