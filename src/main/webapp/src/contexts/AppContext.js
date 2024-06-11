// src/contexts/AppContext.js
import React, { createContext, useState, useEffect } from 'react';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
    const [darkMode, setDarkMode] = useState(false);
    const [houseState, setHouseState] = useState(null);

    useEffect(() => {
        fetch('http://10.9.24.206:8080/smarthome/house')
            .then(response => response.json())
            .then(data => setHouseState(data))
            .catch(err => console.log(err));
    }, []);

    const value = {
        darkMode,
        setDarkMode,
        houseState,
    };

    return (
        <AppContext.Provider value={value}>
            {children}
        </AppContext.Provider>
    );
};
