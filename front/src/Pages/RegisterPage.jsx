import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { register } from '../Services/UserService';
import {
    Container,
    Typography,
    TextField,
    Button,
    Box,
    Paper
} from '@mui/material';

const RegisterPage = () => {
    const [user, setUser] = useState({
        username: '',
        password: '',
        email: '',
        name: '',
        lastName: ''
    });
    const navigate = useNavigate();

    const handleRegister = async () => {
        try {
            await register(user);
            navigate('/login');
        } catch (error) {
            console.error('Error registering:', error);
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{ padding: 4, mt: 8 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Register
                </Typography>
                <Box component="form">
                    <TextField
                        label="Username"
                        fullWidth
                        margin="normal"
                        value={user.username}
                        onChange={(e) => setUser({ ...user, username: e.target.value })}
                        required
                    />
                    <TextField
                        label="Password"
                        type="password"
                        fullWidth
                        margin="normal"
                        value={user.password}
                        onChange={(e) => setUser({ ...user, password: e.target.value })}
                        required
                    />
                    <TextField
                        label="Email"
                        type="email"
                        fullWidth
                        margin="normal"
                        value={user.email}
                        onChange={(e) => setUser({ ...user, email: e.target.value })}
                        required
                    />
                    <TextField
                        label="Name"
                        fullWidth
                        margin="normal"
                        value={user.name}
                        onChange={(e) => setUser({ ...user, name: e.target.value })}
                        required
                    />
                    <TextField
                        label="Last Name"
                        fullWidth
                        margin="normal"
                        value={user.lastName}
                        onChange={(e) => setUser({ ...user, lastName: e.target.value })}
                        required
                    />
                    <Button variant="contained" color="primary" fullWidth sx={{ mt: 2 }} onClick={handleRegister}>
                        Register
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
};

export default RegisterPage;