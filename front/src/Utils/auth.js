import { jwtDecode } from 'jwt-decode';

const TOKEN_KEY = 'access_token';

const getValidToken = () => {
  if (isTokenExpired() && getDecodedToken()) {
    removeToken();
    window.location.reload();
  }
  return getToken();
};

const getToken = () => {
  return localStorage.getItem(TOKEN_KEY);
};

const setToken = (token) => {
  localStorage.setItem(TOKEN_KEY, token);
  return getToken();
};

const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY);
};

const getDecodedToken = () => {
  const token = getToken();
  if (!token) return null;
  try {
    return jwtDecode(token);
  } catch (error) {
    console.error('Failed to decode token', error);
    return null;
  }
};

const getUsername = (decodedToken) => {
  return decodedToken ? decodedToken.sub : '';
};

const getRoles = (decodedToken) => {
  return decodedToken ? decodedToken.roles : [];
};

const isTokenExpired = () => {
  const decodedToken = getDecodedToken();
  if (!decodedToken) return true;
  return decodedToken.exp * 1000 < Date.now();
};

export default {
  getValidToken,
  setToken,
  removeToken,
  getDecodedToken,
  getUsername,
  getRoles,
  isTokenExpired,
};