import useChat from "../../hooks/useChat.jsx";
import {MdClose} from "react-icons/md";
import ChatBox from "./ChatBox.jsx";
import ConversationList from "./ConversationList.jsx";

const ChatWindow = () => {
    const {isChatWindowOpen, closeChat, activeConversation} = useChat();

    if (!isChatWindowOpen) return null;

    return (
        <div
            className="
            fixed bottom-6 right-6
                w-full max-w-md h-150
                bg-gray-50 dark:bg-stoneBrown-900
                border shadow-lg
                rounded-2xl
                p-2
                flex flex-col
            "
        >
            {/* Header */}
            <div className="flex items-center justify-between px-4 py-3 border-b">
                <div className="flex items-center gap-3">
                    {activeConversation?.avatar ? (
                        <img
                            src={activeConversation.avatar}
                            alt={activeConversation.name}
                            className="w-8 h-8 rounded-full"
                        />
                    ) : (
                        <div
                            className="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center font-semibold">
                            {activeConversation?.name?.charAt(0)}
                        </div>
                    )}

                    <span className="font-semibold truncate">
            {activeConversation ? activeConversation.name : "Tin nhắn"}
        </span>
                </div>

                <button onClick={closeChat} className="text-gray-500">
                    <MdClose/>
                </button>
            </div>
            {/* Body */}
            <div className="flex-1 overflow-hidden">
                {!activeConversation ? (
                    <ConversationList/>
                ) : (
                    <ChatBox/>
                )}
            </div>
        </div>
    );
};

export default ChatWindow;