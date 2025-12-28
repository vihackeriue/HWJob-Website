import {useContext, useEffect} from "react";
import SocketContext from "../contexts/socket/SocketContext.jsx";


const useSocket = () => {
    const context = useContext(SocketContext);
    if (!context) {
        throw new Error("useSocket must be used within SocketProvider");
    }
    return context;
};

export default useSocket;