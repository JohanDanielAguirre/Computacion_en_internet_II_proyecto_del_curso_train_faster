import React, { useEffect, useState } from 'react';
import { axiosInstance } from '../Services/Axios';
import { useNavigate } from 'react-router-dom';
import {
    Container,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Button,
    TextField,
    Box
} from '@mui/material';

const HomePage = () => {
    const [users, setUsers] = useState([]);
    const [roles, setRoles] = useState([]);
    const [editUser, setEditUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchUsers();
        fetchRoles();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get('/api/users');
            const usersData = response.data || [];
            const usersWithRoles = usersData.map(user => ({
                ...user,
                roles: user.roles || []
            }));
            setUsers(usersWithRoles);
        } catch (error) {
            console.error('Error fetching users:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const fetchRoles = async () => {
        try {
            const response = await axiosInstance.get('/api/roles');
            setRoles(response.data || []);
        } catch (error) {
            console.error('Error fetching roles:', error);
        }
    };

    const handleEdit = (user) => {
        setEditUser({
            ...user,
            roles: user.roles || [] // Ensure roles is always an array
        });
    };

    const handleDelete = async (userId) => {
        try {
            await axiosInstance.delete(`/api/users`, { params: { id: userId } });
            fetchUsers();
        } catch (error) {
            console.error('Error deleting user:', error);
        }
    };

    const handleSave = async () => {
        try {
            await axiosInstance.put('/api/users', editUser);
            setEditUser(null);
            fetchUsers();
        } catch (error) {
            console.error('Error saving user:', error);
        }
    };

    const handleRoleChange = (e) => {
        const selectedRoles = Array.from(e.target.selectedOptions, option => option.value);
        setEditUser({
            ...editUser,
            roles: selectedRoles.map(roleId => roles.find(role => role.id === roleId))
        });
    };

    const handleLogout = () => {
        localStorage.removeItem('access_token');
        navigate('/login');
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Home
            </Typography>
            <Button variant="contained" color="primary" onClick={handleLogout} style={{ marginBottom: '20px' }}>
                Logout
            </Button>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Username</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Roles</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.length > 0 ? (
                            users.map(user => (
                                <TableRow key={user.id}>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{Array.isArray(user.roles) && user.roles.length > 0 ? user.roles.map(role => role.name).join(', ') : 'No roles'}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="warning" size="small" onClick={() => handleEdit(user)}>Edit</Button>
                                        <Button variant="contained" color="error" size="small" onClick={() => handleDelete(user.id)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="4" align="center">No users found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {editUser && (
                <Box mt={4}>
                    <Typography variant="h6">Edit User</Typography>
                    <TextField
                        label="Username"
                        fullWidth
                        margin="normal"
                        value={editUser.username}
                        onChange={(e) => setEditUser({ ...editUser, username: e.target.value })}
                    />
                    <TextField
                        label="Email"
                        fullWidth
                        margin="normal"
                        value={editUser.email}
                        onChange={(e) => setEditUser({ ...editUser, email: e.target.value })}
                    />
                    {editUser.roles && editUser.roles.some(role => role?.name === 'ROLE_ADMIN') && (
                        <Box mt={2}>
                            <Typography variant="subtitle1">Roles</Typography>
                            <TextField
                                select
                                SelectProps={{
                                    multiple: true,
                                    native: true,
                                }}
                                variant="outlined"
                                fullWidth
                                value={editUser.roles.map(role => role.id)}
                                onChange={handleRoleChange}
                            >
                                {roles.map(role => (
                                    <option key={role.id} value={role.id}>
                                        {role.name}
                                    </option>
                                ))}
                            </TextField>
                        </Box>
                    )}
                    <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
                    <Button variant="contained" color="secondary" onClick={() => setEditUser(null)}>Cancel</Button>
                </Box>
            )}
        </Container>
    );
};

export default HomePage;