import { axiosInstance } from './Axios';

export const login = async (username, password) => {
    try {
        const response = await axiosInstance.post('/auth/login', {
            username,
            password,
        });
        if (response.data.token) {
            localStorage.setItem('access_token', response.data.token);
        }
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const register = async (user) => {
    try {
        const response = await axiosInstance.post('/auth/register', user);
        return response.data;
    } catch (error) {
        throw error;
    }
};