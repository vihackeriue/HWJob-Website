import {useState} from "react";
import ChatContext from "./ChatContext.jsx";


const ChatProvider = ({children}) => {

    const [isChatWindowOpen, setIsChatWindowOpen] = useState(false);
    const [activeConversation, setActiveConversation] = useState(null);

    return (
        <ChatContext.Provider value={{
            isChatWindowOpen,
            openChat: () => setIsChatWindowOpen(true),
            closeChat: () => {
                setIsChatWindowOpen(false);
                setActiveConversation(null);
            },
            activeConversation,
            setActiveConversation
        }}>
            {children}
        </ChatContext.Provider>
    )

}

export default ChatProvider;