import {sendMessage, subscribeConversation} from "../services/socketService.jsx";

const useSocket = () => {
    return {
        subscribeConversation,
        sendMessage,
    };
};

export default useSocket;