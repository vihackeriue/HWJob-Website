import {Button} from "@headlessui/react";
import {MdMessage} from "react-icons/md";
import useChat from "../../hooks/useChat.jsx";

const ChatFloatingButton = () => {

    const {openChat, isChatWindowOpen} = useChat();

    if (isChatWindowOpen) return null;

    return (
        <Button
            onClick={openChat}
            className="
                fixed bottom-6 right-6
                bg-teal-700
                dark:bg-teal-900 text-white
                p-4 rounded-full
                shadow-2xl
                hover:bg-teal-600
                dark:hover:bg-teal-800
            "
        >
            <MdMessage className="text-2xl"/>
        </Button>
    )

}

export default ChatFloatingButton;