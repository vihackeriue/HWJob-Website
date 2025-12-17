import useChat from "../../hooks/useChat.jsx";
import {MdClose} from "react-icons/md";

const ChatWindow = () => {
    const {isChatWindowOpen, closeChat} = useChat();

    if (!isChatWindowOpen) return null;

    return (
        <div
            className="
                fixed bottom-6 right-6
                 lg:w-150
                 lg:h-130
                bg-white dark:bg-stoneBrown-900
                border
                shadow-lg
                p-4
                rounded-2xl
            "
        >
            <span>ChatWindows</span>

            <button
                onClick={closeChat}
                className="absolute top-2 right-2 text-gray-500"
            >
                <MdClose/>
            </button>
        </div>
    );
};

export default ChatWindow;