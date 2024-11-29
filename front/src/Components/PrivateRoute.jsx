import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';

const getValidToken = () => {
  const token = localStorage.getItem('access_token');
  if (!token) return null;

  try {
    const decodedToken = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    if (decodedToken.exp < currentTime) {
      localStorage.removeItem('access_token');
      return null;
    }
    return token;
  } catch (error) {
    console.error('Invalid token:', error);
    return null;
  }
};

const PrivateRoute = ({ roles }) => {
  const token = getValidToken();
  const userRoles = token ? jwtDecode(token).roles : [];

  return token && roles.some(role => userRoles.includes(role)) ? (
    <Outlet />
  ) : (
    <Navigate to="/login" />
  );
};

export default PrivateRoute;