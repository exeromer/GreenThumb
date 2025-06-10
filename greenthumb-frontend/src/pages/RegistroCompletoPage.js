import React, { useState, useEffect } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import { registrarNuevoCliente } from '../api/api';
import { useUser } from '../context/UserContext';
import './RegistroCompletoPage.css'; // <-- 1. Importamos la nueva hoja de estilos

const RegistroCompletoPage = () => {
    const { user, getAccessTokenSilently } = useAuth0();
    const navigate = useNavigate();
    const { setLocalUser } = useUser();

    const [formData, setFormData] = useState({
        calle: '',
        numero: '',
        codigoPostal: '',
        ciudad: '',
        provincia: '',
        telefono: ''
    });

    // Este efecto pre-rellena los campos que ya conocemos de Auth0 (si los hay)
    useEffect(() => {
        if (user) {
            setFormData(prevData => ({
                ...prevData,
                // Puedes pre-rellenar más campos si vienen de Auth0
            }));
        }
    }, [user]);


    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const datosCompletos = {
                ...formData,
                auth0Id: user.sub,
                email: user.email,
                nombre: user.given_name || user.nickname,
                apellido: user.family_name || ' '
            };

            const token = await getAccessTokenSilently();
            const nuevoUsuario = await registrarNuevoCliente(datosCompletos, token);

            setLocalUser(nuevoUsuario);
            alert("¡Registro completado con éxito!");
            navigate('/'); 

        } catch (error) {
            console.error("Error al enviar el formulario de registro:", error);
            alert("Hubo un error al completar el registro.");
        }
    };

    return (
        <div className="form-container">
            <h1>Completar Registro</h1>
            <p>
                ¡Bienvenido a GreenThumb! Como es tu primera vez,
                necesitamos algunos datos adicionales para crear tu perfil.
            </p>
            <form onSubmit={handleSubmit}>
                <input name="calle" value={formData.calle} onChange={handleChange} placeholder="Calle" required />
                <input name="numero" value={formData.numero} onChange={handleChange} placeholder="Número / Dpto" required />

                {/* 2. INPUTS MEJORADOS */}
                <input 
                    type="tel" 
                    name="telefono" 
                    value={formData.telefono} 
                    onChange={handleChange} 
                    placeholder="Teléfono (solo números)" 
                    pattern="[0-9]*"
                    required 
                />
                <input 
                    type="text" 
                    name="codigoPostal" 
                    value={formData.codigoPostal} 
                    onChange={handleChange} 
                    placeholder="Código Postal (solo números)" 
                    pattern="[0-9]*" 
                    maxLength="4"
                    required 
                />

                <input name="ciudad" value={formData.ciudad} onChange={handleChange} placeholder="Ciudad" required />
                <input name="provincia" value={formData.provincia} onChange={handleChange} placeholder="Provincia" required />
                <button type="submit">Completar Registro</button>
            </form>
        </div>
    );
};

export default RegistroCompletoPage;