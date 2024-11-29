import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API,
    headers: {
        'X-Requested-With': 'XMLHttpRequest',
        Accept: 'application/json',
        'Content-Type': 'application/json',
    },
    withCredentials: true
});

// Interceptor para agregar el token a todas las peticiones
axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('access_token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor para manejar errores de respuesta
axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response) {
            // El servidor respondió con un código de estado fuera del rango 2xx
            console.error('Response error:', error.response.status, error.response.data);
            if (error.response.status === 403) {
                console.error('Forbidden access. Check CORS and authentication.');
            }
        } else if (error.request) {
            // La petición fue hecha pero no se recibió respuesta
            console.error('Request error:', error.request);
        } else {
            // Algo sucedió en la configuración de la petición
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);


console.log('API Base URL:', process.env.REACT_APP_API);

export{axiosInstance};