import {createContext} from "react";

const SocketContext = createContext({
    isConnected: false,
});

export default SocketContext;