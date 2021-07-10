/* eslint-disable no-param-reassign,no-undef */
import axios from 'axios';
import { checkEmptyObject } from './CommonUtils';

/**
 * Global Configurations for Axios, setting BaseURL, Timeout
 * and Interceptors.
 * */
const api = axios.create({
    timeout: 30000,
});

api.interceptors.request.use((config) => {
    // Do something before request is sent
    const applicationState = JSON.parse(localStorage.getItem('persist:root'));
    const login = applicationState ? JSON.parse(applicationState?.login) : {};
    if (!checkEmptyObject(login)) {
        config.headers.Authorization = login?.data?.token;
    }
    return config;
}, (error) => {
    console.log('Catched Request Error:', error);
    return Promise.reject(error);
});

api.interceptors.response.use((response) => {
    return response;
}, (error) => {
    // Do something with response error
    if (error.response?.status === 401) {
        // authProvider.logout();
    }
    return Promise.reject(error);
});

export default api;
