import useAuth from "../../hooks/useAuth.jsx";
import {useEffect, useState} from "react";
import {connectSocket, disconnectSocket} from "../../api/websocket.jsx";
import SocketContext from "./SocketContext.jsx";

const SocketProvider = ({children}) => {
    const {auth} = useAuth();
    const [isConnected, setIsConnected] = useState(false);

    useEffect(() => {

        if (!auth?.accessToken) {
            disconnectSocket();
            setIsConnected(false);
            return;
        }

        connectSocket(
            auth.accessToken,
            () => setIsConnected(true)
        );

        return () => disconnectSocket();
    }, [auth?.accessToken]);

    return (
        <SocketContext.Provider value={{isConnected}}>
            {children}
        </SocketContext.Provider>
    );
}

export default SocketProvider;


