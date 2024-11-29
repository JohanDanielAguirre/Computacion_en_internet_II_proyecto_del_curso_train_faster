import React from 'react';
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider, Navigate } from 'react-router-dom';
import Home from '../Pages/HomePage';
import Login from '../Pages/LoginPage';
import Register from '../Pages/RegisterPage';
import TicketsPage from '../Pages/TicketsPage';
import MaintenancePage from '../Pages/MaintenancePage';
import TripsPage from '../Pages/TripsPage';
import PermissionsPage from '../Pages/PermissionsPage';
import RolesPage from '../Pages/RolesPage';
import PrivateRoute from '../Components/PrivateRoute';
import Authenticator from '../Components/Authenticator';

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Navigate to="/login" />} /> {/* Redirigir a /login */}
            <Route element={<Authenticator />}>
                <Route path="/home" element={<Home />} />
                <Route path="/tickets" element={<TicketsPage />} />
                <Route element={<PrivateRoute roles={['ROLE_ADMIN']} />}>
                    <Route path="/maintenances" element={<MaintenancePage />} />
                </Route>
                <Route path="/trips" element={<TripsPage />} />
                <Route path="/permissions" element={<PermissionsPage />} />
                <Route path="/roles" element={<RolesPage />} />
            </Route>
        </>
    )
);

const App = () => {
    return <RouterProvider router={router} />;
};

export default App;