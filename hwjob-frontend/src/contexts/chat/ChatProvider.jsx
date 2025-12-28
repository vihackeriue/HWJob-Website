import {useEffect, useState} from "react";
import ChatContext from "./ChatContext.jsx";
import {chatService} from "../../services/chatService.jsx";


const ChatProvider = ({children}) => {

    const [isChatWindowOpen, setIsChatWindowOpen] = useState(false);
    const [activeConversation, setActiveConversation] = useState(null);
    const [conversations, setConversations] = useState([]);
    const [loading, setLoading] = useState(false);

    const loadConversations = async () => {
        try {
            setLoading(true);
            const res = await chatService.getConversations();
            console.log("[ChatProvider] API response:", res);

            const data = res?.data?.result || res?.result || res?.data || [];

            if (Array.isArray(data)) {
                setConversations(data);
            } else {
                setConversations([]);
            }
        } catch (error) {
            console.log("[ChatProvider] Error:", error.message);
            setConversations([]);
        } finally {
            setLoading(false);
        }
    };

    const openConversation = (conversation) => {
        setActiveConversation(conversation);
        setIsChatWindowOpen(true);
    };

    useEffect(() => {
        if (isChatWindowOpen) {
            loadConversations();
        }
    }, [isChatWindowOpen]);

    return (
        <ChatContext.Provider value={{
            isChatWindowOpen,
            openChat: () => setIsChatWindowOpen(true),
            closeChat: () => {
                setIsChatWindowOpen(false);
                setActiveConversation(null);
            },
            activeConversation,
            setActiveConversation,
            openConversation,
            conversations,
            loading
        }}>
            {children}
        </ChatContext.Provider>
    );
};

export default ChatProvider;