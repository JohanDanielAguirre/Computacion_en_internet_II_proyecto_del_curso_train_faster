import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const socket = new SockJS(`${process.env.REACT_APP_API}/ws`);
const stompClient = Stomp.over(socket);

export const connect = (onConnected, onError) => {
    stompClient.connect({}, onConnected, onError);
};

export const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
};

export const sendMessage = (destination, message) => {
    stompClient.send(destination, {}, JSON.stringify(message));
};

export const subscribe = (topic, onMessageReceived) => {
    return stompClient.subscribe(topic, onMessageReceived);
};