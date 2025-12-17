import {getSocketClient} from "../api/websocket.jsx";


export const subscribeConversation = (conversationId, onMessage) => {
    const client = getSocketClient();

    if (!client || !client.connected) {
        console.warn("Client not connected");
        return null;
    }

    const subscription = client.subscribe(
        `/topic/conversation/${conversationId}`,
        (message) => {
            try {
                const data = JSON.parse(message.body);
                onMessage(data);
            } catch (error) {
                console.error("Error parsing message:", error);
            }
        }
    );

    console.log(`📡 Subscribed to conversation: ${conversationId}`);
    return subscription;
};

export const sendMessage = (payload) => {
    const client = getSocketClient();

    if (!client || !client.connected) {
        console.error("Cannot send message: client not connected");
        return false;
    }

    try {
        client.publish({
            destination: "/app/chat.send",
            body: JSON.stringify(payload),
        });
        console.log("Message sent:", payload);
        return true;
    } catch (error) {
        console.error("Error sending message:", error);
        return false;
    }
};