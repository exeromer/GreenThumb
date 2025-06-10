import React, { createContext, useState, useContext } from 'react';

// 1. Crear el Contexto
const UserContext = createContext();

// 2. Crear un hook personalizado para usar el contexto fácilmente
export const useUser = () => {
    return useContext(UserContext);
};

// 3. Crear el Proveedor del Contexto
export const UserProvider = ({ children }) => {
    const [localUser, setLocalUser] = useState(null);

    return (
        <UserContext.Provider value={{ localUser, setLocalUser }}>
            {children}
        </UserContext.Provider>
    );
};