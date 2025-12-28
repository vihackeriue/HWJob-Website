import useAuth from "../../hooks/useAuth.jsx";
import {useEffect, useState} from "react";
import SocketContext from "./SocketContext.jsx";
import {socketService} from "../../services/socketService.jsx";
import useChat from "../../hooks/useChat.jsx";

const SocketProvider = ({children}) => {
    const {auth} = useAuth();
    const {isChatWindowOpen} = useChat();
    const [isConnected, setIsConnected] = useState(false);

    useEffect(() => {
        if (!isChatWindowOpen || !auth?.accessToken) {
            return;
        }

        socketService.connect(auth.accessToken, {
            onConnect: () => setIsConnected(true),
            onDisconnect: () => setIsConnected(false),
            onError: () => setIsConnected(false),
        });

        return () => {
            socketService.disconnect();
            setIsConnected(false);
        };
    }, [isChatWindowOpen, auth?.accessToken]);

    return (
        <SocketContext.Provider value={{isConnected}}>
            {children}
        </SocketContext.Provider>
    );
};

export default SocketProvider;