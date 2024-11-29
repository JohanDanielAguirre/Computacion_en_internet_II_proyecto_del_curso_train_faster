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

const TripsPage = () => {
    const [trips, setTrips] = useState([]);
    const [editTrip, setEditTrip] = useState(null);
    const [newTrip, setNewTrip] = useState({ destination: '', departureTime: '', trainId: '' });
    const navigate = useNavigate();

    useEffect(() => {
        fetchTrips();
    }, []);

    const fetchTrips = async () => {
        try {
            const response = await axiosInstance.get('/api/trips');
            setTrips(response.data || []);
        } catch (error) {
            console.error('Error fetching trips:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const handleEdit = (trip) => {
        setEditTrip(trip);
    };

    const handleDelete = async (tripId) => {
        try {
            await axiosInstance.delete(`/api/trips`, { params: { id: tripId } });
            fetchTrips();
        } catch (error) {
            console.error('Error deleting trip:', error);
        }
    };

    const handleSave = async () => {
        try {
            await axiosInstance.put(`/api/trips`, editTrip);
            setEditTrip(null);
            fetchTrips();
        } catch (error) {
            console.error('Error saving trip:', error);
        }
    };

    const handleCreate = async () => {
        try {
            await axiosInstance.post('/api/trips', newTrip);
            setNewTrip({ destination: '', departureTime: '', trainId: '' });
            fetchTrips();
        } catch (error) {
            console.error('Error creating trip:', error);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Trips
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Destination</TableCell>
                            <TableCell>Departure Time</TableCell>
                            <TableCell>Train ID</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {trips.length > 0 ? (
                            trips.map(trip => (
                                <TableRow key={trip.id}>
                                    <TableCell>{trip.destination}</TableCell>
                                    <TableCell>{trip.departureTime}</TableCell>
                                    <TableCell>{trip.trainId}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="warning" size="small" onClick={() => handleEdit(trip)}>Edit</Button>
                                        <Button variant="contained" color="error" size="small" onClick={() => handleDelete(trip.id)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="4" align="center">No trips found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {editTrip && (
                <Box mt={4}>
                    <Typography variant="h6">Edit Trip</Typography>
                    <TextField
                        label="Destination"
                        fullWidth
                        margin="normal"
                        value={editTrip.destination}
                        onChange={(e) => setEditTrip({ ...editTrip, destination: e.target.value })}
                    />
                    <TextField
                        label="Departure Time"
                        type="datetime-local"
                        fullWidth
                        margin="normal"
                        value={editTrip.departureTime}
                        onChange={(e) => setEditTrip({ ...editTrip, departureTime: e.target.value })}
                    />
                    <TextField
                        label="Train ID"
                        type="number"
                        fullWidth
                        margin="normal"
                        value={editTrip.trainId}
                        onChange={(e) => setEditTrip({ ...editTrip, trainId: e.target.value })}
                    />
                    <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
                    <Button variant="contained" color="secondary" onClick={() => setEditTrip(null)}>Cancel</Button>
                </Box>
            )}
            <Box mt={4}>
                <Typography variant="h6">Create New Trip</Typography>
                <TextField
                    label="Destination"
                    fullWidth
                    margin="normal"
                    value={newTrip.destination}
                    onChange={(e) => setNewTrip({ ...newTrip, destination: e.target.value })}
                />
                <TextField
                    label="Departure Time"
                    type="datetime-local"
                    fullWidth
                    margin="normal"
                    value={newTrip.departureTime}
                    onChange={(e) => setNewTrip({ ...newTrip, departureTime: e.target.value })}
                />
                <TextField
                    label="Train ID"
                    type="number"
                    fullWidth
                    margin="normal"
                    value={newTrip.trainId}
                    onChange={(e) => setNewTrip({ ...newTrip, trainId: e.target.value })}
                />
                <Button variant="contained" color="primary" onClick={handleCreate}>Create</Button>
            </Box>
        </Container>
    );
};

export default TripsPage;