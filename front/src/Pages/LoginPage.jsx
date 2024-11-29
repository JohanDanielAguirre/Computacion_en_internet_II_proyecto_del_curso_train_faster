import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../Services/UserService';
import {
    Container,
    Typography,
    TextField,
    Button,
    Box,
    Paper
} from '@mui/material';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            await login(username, password);
            navigate('/home');
        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{ padding: 4, mt: 8 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Login
                </Typography>
                <Box component="form" onSubmit={handleLogin}>
                    <TextField
                        label="Username"
                        fullWidth
                        margin="normal"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <TextField
                        label="Password"
                        type="password"
                        fullWidth
                        margin="normal"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
                        Login
                    </Button>
                </Box>
                <Link to="/register" style={{ textDecoration: 'none', display: 'block', textAlign: 'center', marginTop: '16px' }}>
                    Don't have an account? Register
                </Link>
            </Paper>
        </Container>
    );
};

export default LoginPage;