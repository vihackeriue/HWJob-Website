import useChat from "../../hooks/useChat.jsx";
import {MdArrowBack, MdSend} from "react-icons/md";
import {useState} from "react";

const ChatBox = () => {
    const {activeConversation, setActiveConversation} = useChat();
    const [message, setMessage] = useState("");


    const handleSend = () => {
        if (!message.trim()) return;

        console.log("Send message:", message);
        // TODO: gọi sendMessage(socket)
        setMessage("");
    };

    return (
        <div className="flex flex-col h-full">
            {/* Back */}
            <div className="px-4 py-2 border-b">
                <button
                    onClick={() => setActiveConversation(null)}
                    className="flex items-center gap-2 text-sm text-gray-600"
                >
                    <MdArrowBack/>
                    Quay lại
                </button>
            </div>

            {/* Messages */}
            <div className="flex-1 p-4 overflow-y-auto">
                <div className="mt-4 space-y-2">
                    {/* Other */}
                    <div className="flex items-end gap-2">
                        <img
                            src={activeConversation.avatar}
                            className="w-6 h-6 rounded-full"
                        />
                        <div className="bg-gray-100 p-2 rounded-lg max-w-[70%]">
                            Xin chào 👋
                        </div>
                    </div>

                    {/* Mine */}
                    <div className="flex justify-end">
                        <div className="bg-teal-500 text-white p-2 rounded-lg max-w-[70%]">
                            Chào bạn!
                        </div>
                    </div>
                </div>
            </div>

            {/* Input */}
            <div className="border-t px-3 py-2 flex items-center gap-2">
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Nhập tin nhắn..."
                    className="
                        flex-1
                        px-3 py-2
                        rounded-full
                        border
                        outline-none
                        focus:ring-2 focus:ring-teal-500
                    "
                    onKeyDown={(e) => {
                        if (e.key === "Enter") handleSend();
                    }}
                />

                <button
                    onClick={handleSend}
                    className="
                        p-2
                        rounded-full
                        bg-teal-500
                        hover:bg-teal-600
                        text-white
                        disabled:opacity-50
                    "
                    disabled={!message.trim()}
                >
                    <MdSend size={20}/>
                </button>
            </div>
        </div>
    );
};

export default ChatBox;