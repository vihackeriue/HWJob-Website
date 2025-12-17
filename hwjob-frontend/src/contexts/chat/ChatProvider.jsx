import {useState} from "react";
import ChatContext from "./ChatContext.jsx";


const ChatProvider = ({children}) => {

    const [isChatWindowOpen, setIsChatWindowOpen] = useState(false);
    const [selectedConversation, setSelectedConversation] = useState(null);

    const openChat = () => {
        setIsChatWindowOpen(true);
    }

    const closeChat = () => {
        setIsChatWindowOpen(false);
    }

    return (
        <ChatContext.Provider value={{
            isChatWindowOpen,
            openChat,
            closeChat,
            selectedConversation,
            setSelectedConversation
        }}>
            {children}
        </ChatContext.Provider>
    )

}

export default ChatProvider;