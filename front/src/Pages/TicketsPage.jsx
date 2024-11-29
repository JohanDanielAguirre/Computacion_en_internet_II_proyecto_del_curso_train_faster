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

const TicketsPage = () => {
    const [tickets, setTickets] = useState([]);
    const [editTicket, setEditTicket] = useState(null);
    const [newTicket, setNewTicket] = useState({ seat: '', tripId: '', userId: '' });
    const navigate = useNavigate();

    useEffect(() => {
        fetchTickets();
    }, []);

    const fetchTickets = async () => {
        try {
            const response = await axiosInstance.get('/api/tickets');
            setTickets(response.data || []);
        } catch (error) {
            console.error('Error fetching tickets:', error);
            if (error.response?.status === 403) {
                localStorage.removeItem('access_token');
                navigate('/login');
            }
        }
    };

    const handleEdit = (ticket) => {
        setEditTicket(ticket);
    };

    const handleDelete = async (ticketId) => {
        try {
            await axiosInstance.delete(`/api/tickets`, { params: { id: ticketId } });
            fetchTickets();
        } catch (error) {
            console.error('Error deleting ticket:', error);
        }
    };

    const handleSave = async () => {
        try {
            await axiosInstance.put('/api/tickets', editTicket);
            setEditTicket(null);
            fetchTickets();
        } catch (error) {
            console.error('Error saving ticket:', error);
        }
    };

    const handleCreate = async () => {
        try {
            await axiosInstance.post('/api/tickets', newTicket);
            setNewTicket({ seat: '', tripId: '', userId: '' });
            fetchTickets();
        } catch (error) {
            console.error('Error creating ticket:', error);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom align="center">
                Tickets
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Seat</TableCell>
                            <TableCell>Trip ID</TableCell>
                            <TableCell>User ID</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {tickets.length > 0 ? (
                            tickets.map(ticket => (
                                <TableRow key={ticket.id}>
                                    <TableCell>{ticket.seat}</TableCell>
                                    <TableCell>{ticket.tripId}</TableCell>
                                    <TableCell>{ticket.userId}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="warning" size="small" onClick={() => handleEdit(ticket)}>Edit</Button>
                                        <Button variant="contained" color="error" size="small" onClick={() => handleDelete(ticket.id)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="4" align="center">No tickets found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {editTicket && (
                <Box mt={4}>
                    <Typography variant="h6">Edit Ticket</Typography>
                    <TextField
                        label="Seat"
                        fullWidth
                        margin="normal"
                        value={editTicket.seat}
                        onChange={(e) => setEditTicket({ ...editTicket, seat: e.target.value })}
                    />
                    <TextField
                        label="Trip ID"
                        type="number"
                        fullWidth
                        margin="normal"
                        value={editTicket.tripId}
                        onChange={(e) => setEditTicket({ ...editTicket, tripId: e.target.value })}
                    />
                    <TextField
                        label="User ID"
                        type="number"
                        fullWidth
                        margin="normal"
                        value={editTicket.userId}
                        onChange={(e) => setEditTicket({ ...editTicket, userId: e.target.value })}
                    />
                    <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
                    <Button variant="contained" color="secondary" onClick={() => setEditTicket(null)}>Cancel</Button>
                </Box>
            )}
            <Box mt={4}>
                <Typography variant="h6">Create New Ticket</Typography>
                <TextField
                    label="Seat"
                    fullWidth
                    margin="normal"
                    value={newTicket.seat}
                    onChange={(e) => setNewTicket({ ...newTicket, seat: e.target.value })}
                />
                <TextField
                    label="Trip ID"
                    type="number"
                    fullWidth
                    margin="normal"
                    value={newTicket.tripId}
                    onChange={(e) => setNewTicket({ ...newTicket, tripId: e.target.value })}
                />
                <TextField
                    label="User ID"
                    type="number"
                    fullWidth
                    margin="normal"
                    value={newTicket.userId}
                    onChange={(e) => setNewTicket({ ...newTicket, userId: e.target.value })}
                />
                <Button variant="contained" color="primary" onClick={handleCreate}>Create</Button>
            </Box>
        </Container>
    );
};

export default TicketsPage;