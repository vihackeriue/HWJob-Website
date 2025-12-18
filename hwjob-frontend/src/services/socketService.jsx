import {ENDPOINTS} from "../config/endpoints.jsx";
import {Client} from "@stomp/stompjs";

let client = null;

export const socketService = {
    connect(accessToken, {onConnect, onDisconnect, onError}) {
        if (client?.connected) {
            onConnect?.();
            return;
        }

        const brokerURL = `ws://localhost:8080/hwjob/api/${ENDPOINTS.WS.ENDPOINT}`;

        client = new Client({
            brokerURL,
            connectHeaders: {
                Authorization: `Bearer ${accessToken}`,
            },
            onConnect: () => {
                console.log("[WebSocket] Connected");
                onConnect?.();
            },
            onDisconnect: () => {
                console.log("[WebSocket] Disconnected");
                onDisconnect?.();
            },
            onStompError: (frame) => {
                console.error("[WebSocket] Error:", frame.headers?.message);
                onError?.(frame);
            },
            reconnectDelay: 5000,
        });

        client.activate();
    },

    disconnect() {
        if (client) {
            client.deactivate();
            client = null;
        }
    },

    subscribe(destination, callback) {
        if (!client?.connected) {
            console.warn("[WebSocket] Not connected, cannot subscribe");
            return null;
        }
        return client.subscribe(destination, callback);
    },

    sendMessage(conversationId, message) {
        if (!client?.connected) {
            console.warn("[WebSocket] Not connected, cannot send");
            return;
        }
        client.publish({
            destination: ENDPOINTS.WS.DESTINATION.SEND_MESSAGE,
            body: JSON.stringify({conversationId, message}),
        });
    },

    isConnected() {
        return client?.connected ?? false;
    },
};