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

const MaintenancePage = () => {
    const [maintenances, setMaintenances] = useState([]);
    const [editMaintenance, setEditMaintenance] = useState(null);
    const [newMaintenance, setNewMaintenance] = useState({ description: '', maintenanceDate: '', status: '', trainId: '' });
    const navigate = useNavigate();

    useEffect(() => {
        fetchMaintenances();
    }, []);

    const fetchMaintenances = async () => {
        try {
            const response = await axiosInstance.get('/api/maintenances');
            setMaintenances(response.data || []);
        } catch (error) {
            console.error('Error fetching maintenances:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const handleEdit = (maintenance) => {
        setEditMaintenance(maintenance);
    };

    const handleDelete = async (maintenanceId) => {
        try {
            await axiosInstance.delete(`/api/maintenances`, { params: { id: maintenanceId } });
            fetchMaintenances();
        } catch (error) {
            console.error('Error deleting maintenance:', error);
        }
    };

    const handleSave = async () => {
        try {
            await axiosInstance.put(`/api/maintenances/${editMaintenance.maintenanceId}`, editMaintenance);
            setEditMaintenance(null);
            fetchMaintenances();
        } catch (error) {
            console.error('Error saving maintenance:', error);
        }
    };

    const handleCreate = async () => {
        try {
            await axiosInstance.post('/api/maintenances', newMaintenance);
            setNewMaintenance({ description: '', maintenanceDate: '', status: '', trainId: '' });
            fetchMaintenances();
        } catch (error) {
            console.error('Error creating maintenance:', error);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Maintenances
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Description</TableCell>
                            <TableCell>Maintenance Date</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Train ID</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {maintenances.length > 0 ? (
                            maintenances.map(maintenance => (
                                <TableRow key={maintenance.maintenanceId}>
                                    <TableCell>{maintenance.description}</TableCell>
                                    <TableCell>{maintenance.maintenanceDate}</TableCell>
                                    <TableCell>{maintenance.status}</TableCell>
                                    <TableCell>{maintenance.trainId}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="warning" size="small" onClick={() => handleEdit(maintenance)}>Edit</Button>
                                        <Button variant="contained" color="error" size="small" onClick={() => handleDelete(maintenance.maintenanceId)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="5" align="center">No maintenances found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {editMaintenance && (
                <Box mt={4}>
                    <Typography variant="h6">Edit Maintenance</Typography>
                    <TextField
                        label="Description"
                        fullWidth
                        margin="normal"
                        value={editMaintenance.description}
                        onChange={(e) => setEditMaintenance({ ...editMaintenance, description: e.target.value })}
                    />
                    <TextField
                        label="Maintenance Date"
                        type="datetime-local"
                        fullWidth
                        margin="normal"
                        value={editMaintenance.maintenanceDate}
                        onChange={(e) => setEditMaintenance({ ...editMaintenance, maintenanceDate: e.target.value })}
                    />
                    <TextField
                        label="Status"
                        fullWidth
                        margin="normal"
                        value={editMaintenance.status}
                        onChange={(e) => setEditMaintenance({ ...editMaintenance, status: e.target.value })}
                    />
                    <TextField
                        label="Train ID"
                        type="number"
                        fullWidth
                        margin="normal"
                        value={editMaintenance.trainId}
                        onChange={(e) => setEditMaintenance({ ...editMaintenance, trainId: e.target.value })}
                    />
                    <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
                    <Button variant="contained" color="secondary" onClick={() => setEditMaintenance(null)}>Cancel</Button>
                </Box>
            )}
            <Box mt={4}>
                <Typography variant="h6">Create New Maintenance</Typography>
                <TextField
                    label="Description"
                    fullWidth
                    margin="normal"
                    value={newMaintenance.description}
                    onChange={(e) => setNewMaintenance({ ...newMaintenance, description: e.target.value })}
                />
                <TextField
                    label="Maintenance Date"
                    type="datetime-local"
                    fullWidth
                    margin="normal"
                    value={newMaintenance.maintenanceDate}
                    onChange={(e) => setNewMaintenance({ ...newMaintenance, maintenanceDate: e.target.value })}
                />
                <TextField
                    label="Status"
                    fullWidth
                    margin="normal"
                    value={newMaintenance.status}
                    onChange={(e) => setNewMaintenance({ ...newMaintenance, status: e.target.value })}
                />
                <TextField
                    label="Train ID"
                    type="number"
                    fullWidth
                    margin="normal"
                    value={newMaintenance.trainId}
                    onChange={(e) => setNewMaintenance({ ...newMaintenance, trainId: e.target.value })}
                />
                <Button variant="contained" color="primary" onClick={handleCreate}>Create</Button>
            </Box>
        </Container>
    );
};

export default MaintenancePage;