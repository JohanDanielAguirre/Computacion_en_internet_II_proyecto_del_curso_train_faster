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

const PermissionsPage = () => {
    const [permissions, setPermissions] = useState([]);
    const [newPermission, setNewPermission] = useState({ roleId: '', permissionName: '' });
    const navigate = useNavigate();

    useEffect(() => {
        fetchPermissions();
    }, []);

    const fetchPermissions = async () => {
        try {
            const response = await axiosInstance.get('/api/permissions');
            setPermissions(response.data || []);
        } catch (error) {
            console.error('Error fetching permissions:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const handleCreate = async () => {
        try {
            await axiosInstance.post('/api/permissions', newPermission);
            setNewPermission({ roleId: '', permissionName: '' });
            fetchPermissions();
        } catch (error) {
            console.error('Error creating permission:', error);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Permissions
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Role ID</TableCell>
                            <TableCell>Permission Name</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {permissions.length > 0 ? (
                            permissions.map(permission => (
                                <TableRow key={permission.id}>
                                    <TableCell>{permission.roleId}</TableCell>
                                    <TableCell>{permission.permissionName}</TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="2" align="center">No permissions found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            <Box mt={4}>
                <Typography variant="h6">Create New Permission</Typography>
                <TextField
                    label="Role ID"
                    type="number"
                    fullWidth
                    margin="normal"
                    value={newPermission.roleId}
                    onChange={(e) => setNewPermission({ ...newPermission, roleId: e.target.value })}
                />
                <TextField
                    label="Permission Name"
                    fullWidth
                    margin="normal"
                    value={newPermission.permissionName}
                    onChange={(e) => setNewPermission({ ...newPermission, permissionName: e.target.value })}
                />
                <Button variant="contained" color="primary" onClick={handleCreate}>Create</Button>
            </Box>
        </Container>
    );
};

export default PermissionsPage;