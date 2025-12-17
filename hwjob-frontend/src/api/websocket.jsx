import {Client} from "@stomp/stompjs";
import {ENDPOINTS} from "../config/endpoints.jsx";


let client = null;
const BASE_URL = "http://localhost:8080/hwjob/api/";

export const connectSocket = (accessToken, onConnect) => {

    if (client?.connected) return client;

    client = new Client({
        brokerURL: BASE_URL + ENDPOINTS.WEBSOCKET,
        connectHeaders: {
            Authorization: `Bearer ${accessToken}`
        },
        onConnect: (frame) => {
            console.log("WS Connected");
            onConnect?.(frame);
        },
    });

    client.activate();
    return client;
};

export const disconnectSocket = () => {
    if (client) {
        client.deactivate();
        client = null;
    }
};

export const getSocketClient = () => client;
