import React, { useEffect } from 'react';
import { connect, subscribe, sendMessage } from '../Services/stompService';

const WebSocketComponent = () => {
    useEffect(() => {
        connect(onConnected, onError);

        return () => {
            disconnect();
        };
    }, []);

    const onConnected = () => {
        console.log('Connected to WebSocket');
        subscribe('/topic/messages', onMessageReceived);
    };

    const onError = (error) => {
        console.error('WebSocket connection error:', error);
    };

    const onMessageReceived = (message) => {
        console.log('Message received:', message.body);
    };

    const handleSendMessage = () => {
        sendMessage('/app/sendMessage', { content: 'Hello, World!' });
    };

    return (
        <div>
            <button onClick={handleSendMessage}>Send Message</button>
        </div>
    );
};

export default WebSocketComponent;