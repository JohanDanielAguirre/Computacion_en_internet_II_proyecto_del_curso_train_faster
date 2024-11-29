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

const RolesPage = () => {
    const [roles, setRoles] = useState([]);
    const [permissions, setPermissions] = useState([]);
    const [newRole, setNewRole] = useState({ name: '', permissionIds: [] });
    const navigate = useNavigate();

    useEffect(() => {
        fetchRoles();
        fetchPermissions();
    }, []);

    const fetchRoles = async () => {
        try {
            const response = await axiosInstance.get('/api/roles');
            setRoles(response.data || []);
        } catch (error) {
            console.error('Error fetching roles:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const fetchPermissions = async () => {
        try {
            const response = await axiosInstance.get('/api/roles/form');
            setPermissions(response.data || []);
        } catch (error) {
            console.error('Error fetching permissions:', error);
        }
    };

    const handleDelete = async (roleId) => {
        try {
            await axiosInstance.delete(`/api/roles`, { params: { id: roleId } });
            fetchRoles();
        } catch (error) {
            console.error('Error deleting role:', error);
        }
    };

    const handleCreate = async () => {
        try {
            await axiosInstance.post('/api/roles', newRole);
            setNewRole({ name: '', permissionIds: [] });
            fetchRoles();
        } catch (error) {
            console.error('Error creating role:', error);
        }
    };

    const handlePermissionChange = (e) => {
        const selectedPermissions = Array.from(e.target.selectedOptions, option => option.value);
        setNewRole({
            ...newRole,
            permissionIds: selectedPermissions.map(permissionId => parseInt(permissionId))
        });
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Roles
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Role Name</TableCell>
                            <TableCell>Permissions</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {roles.length > 0 ? (
                            roles.map(role => (
                                <TableRow key={role.id}>
                                    <TableCell>{role.name}</TableCell>
                                    <TableCell>{role.permissions.map(permission => permission.name).join(', ')}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="error" size="small" onClick={() => handleDelete(role.id)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="3" align="center">No roles found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            <Box mt={4}>
                <Typography variant="h6">Create New Role</Typography>
                <TextField
                    label="Role Name"
                    fullWidth
                    margin="normal"
                    value={newRole.name}
                    onChange={(e) => setNewRole({ ...newRole, name: e.target.value })}
                />
                <TextField
                    select
                    SelectProps={{
                        multiple: true,
                        native: true,
                    }}
                    variant="outlined"
                    fullWidth
                    value={newRole.permissionIds}
                    onChange={handlePermissionChange}
                >
                    {permissions.map(permission => (
                        <option key={permission.id} value={permission.id}>
                            {permission.name}
                        </option>
                    ))}
                </TextField>
                <Button variant="contained" color="primary" onClick={handleCreate}>Create</Button>
            </Box>
        </Container>
    );
};

export default RolesPage;