import { useEffect, useRef } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { useUser } from '../context/UserContext';
import { checkUserRegistration } from '../api/api';
import { useNavigate } from 'react-router-dom'; // Hook para redirigir

const UserSyncManager = () => {
    const { user, isAuthenticated, getAccessTokenSilently } = useAuth0();
    const { setLocalUser } = useUser();
    const navigate = useNavigate(); // Hook para la navegación
    const hasChecked = useRef(false); // Para evitar múltiples verificaciones

    useEffect(() => {
        const checkAndSync = async () => {
            if (isAuthenticated && user && !hasChecked.current) {
                hasChecked.current = true; // Marcamos que ya iniciamos la verificación
                try {
                    console.log("Usuario autenticado, verificando registro en BD local...");
                    const token = await getAccessTokenSilently();
                    console.log("TOKEN DE ACCESO:", token); 
                    const result = await checkUserRegistration(token);

                    if (result.needsRegistration) {
                        // Si el usuario necesita registrarse, lo redirigimos
                        console.log("Usuario nuevo detectado. Redirigiendo a /completar-registro");
                        navigate('/completar-registro');
                    } else {
                        // Si ya está registrado, guardamos sus datos en el contexto
                        setLocalUser(result.user);
                        console.log("Usuario existente encontrado y sincronizado:", result.user);
                    }

                } catch (error) {
                    console.error("Fallo la verificación/sincronización del usuario:", error);
                }
            }
        };
        checkAndSync();
    }, [isAuthenticated, user, getAccessTokenSilently, setLocalUser, navigate]);

    return null; // Este componente no renderiza nada
};

export default UserSyncManager;