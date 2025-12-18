import ChatFloatingButton from "../chat/ChatFloatingButton.jsx";
import ChatWindow from "../chat/ChatWindow.jsx";
import {useState} from "react";
import SocketProvider from "../../contexts/socket/SocketProvider.jsx";
import useChat from "../../hooks/useChat.jsx";
import useAuth from "../../hooks/useAuth.jsx";

function ChatFloatingUI() {
    const {isChatWindowOpen} = useChat();

    const {auth} = useAuth();

    if (!auth) return null;

    return (
        <SocketProvider>
            <ChatFloatingButton/>
            {isChatWindowOpen && <ChatWindow/>}
        </SocketProvider>
    );
}

export default ChatFloatingUI;