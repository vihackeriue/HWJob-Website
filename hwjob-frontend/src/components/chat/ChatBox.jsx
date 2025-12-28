import useChat from "../../hooks/useChat.jsx";
import {MdArrowBack, MdSend} from "react-icons/md";
import {useEffect, useRef, useState} from "react";
import useSocket from "../../hooks/useSocket.jsx";
import {chatService} from "../../services/chatService.jsx";
import {ENDPOINTS} from "../../config/endpoints.jsx";
import {socketService} from "../../services/socketService.jsx";

const ChatBox = () => {
    const {activeConversation, setActiveConversation} = useChat();
    const {isConnected} = useSocket();

    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const [loading, setLoading] = useState(false);

    const messagesEndRef = useRef(null);

    // Scroll xuống cuối danh sách
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"});
    };

    // Load tin nhắn từ API khi mở conversation
    useEffect(() => {
        if (!activeConversation?.id) return;

        const fetchMessages = async () => {
            setLoading(true);
            try {
                const res = await chatService.getMessages(activeConversation.id);
                const data = res?.data?.result || [];
                // API trả về mới nhất trước → reverse để hiển thị cũ nhất trước
                setMessages(data.reverse());
            } catch (error) {
                console.error("Failed to load messages:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchMessages();
    }, [activeConversation?.id]);

    // Subscribe user-specific queue để nhận message với isMine đúng
    useEffect(() => {
        if (!isConnected || !activeConversation?.id) return;

        const subscription = socketService.subscribe(
            ENDPOINTS.WS.DESTINATION.USER_MESSAGES,
            (frame) => {
                const newMessage = JSON.parse(frame.body);

                // Chỉ thêm message nếu thuộc conversation đang mở
                if (newMessage.conversationId === activeConversation.id) {
                    setMessages((prev) => [...prev, newMessage]);
                }
            }
        );

        return () => {
            subscription?.unsubscribe();
        };
    }, [isConnected, activeConversation?.id]);

    // Scroll xuống khi có tin nhắn mới
    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    // Gửi tin nhắn
    const handleSend = () => {
        const messageText = inputValue.trim();
        if (!messageText || !isConnected) return;

        socketService.sendMessage(activeConversation.id, messageText);
        setInputValue("");
    };

    // Nhấn Enter để gửi
    const handleKeyDown = (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            handleSend();
        }
    };

    return (
        <div className="flex flex-col h-full">
            {/* Header */}
            <div className="px-4 py-2 border-b flex items-center gap-3">
                <button
                    onClick={() => setActiveConversation(null)}
                    className="text-gray-600 hover:text-gray-800"
                >
                    <MdArrowBack size={20}/>
                </button>

                {activeConversation?.conversationImage ? (
                    <img
                        src={activeConversation.conversationImage}
                        alt={activeConversation.conversationName}
                        className="w-8 h-8 rounded-full object-cover"
                    />
                ) : (
                    <div className="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center font-semibold">
                        {activeConversation?.conversationName?.charAt(0)}
                    </div>
                )}

                <span className="font-medium truncate">
                    {activeConversation?.conversationName || "Cuộc trò chuyện"}
                </span>
            </div>

            {/* Messages */}
            <div className="flex-1 p-4 overflow-y-auto space-y-3">
                {loading ? (
                    <p className="text-center text-gray-500">Đang tải...</p>
                ) : messages.length === 0 ? (
                    <p className="text-center text-gray-400">Chưa có tin nhắn</p>
                ) : (
                    messages.map((msg) => (
                        <MessageBubble key={msg.id} messageData={msg}/>
                    ))
                )}
                <div ref={messagesEndRef}/>
            </div>

            {/* Input */}
            <div className="border-t px-3 py-2 flex items-center gap-2">
                <input
                    type="text"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="Nhập tin nhắn..."
                    className="flex-1 px-4 py-2 bg-gray-100 dark:bg-stoneBrown-800 rounded-full outline-none text-sm"
                />
                <button
                    onClick={handleSend}
                    disabled={!inputValue.trim() || !isConnected}
                    className="p-2 rounded-full bg-teal-600 text-white hover:bg-teal-700 disabled:opacity-50"
                >
                    <MdSend size={20}/>
                </button>
            </div>
        </div>
    );
};

// Component hiển thị 1 tin nhắn
const MessageBubble = ({messageData}) => {
    const {message: messageText, isMine, createdDate, sender} = messageData;

    const formatTime = (dateString) => {
        if (!dateString) return "";
        return new Date(dateString).toLocaleTimeString("vi-VN", {
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    // Tin nhắn của mình → bên phải
    if (isMine) {
        return (
            <div className="flex justify-end">
                <div className="max-w-[70%]">
                    <div className="bg-teal-600 text-white px-4 py-2 rounded-2xl rounded-br-sm">
                        {messageText || "(Tin nhắn trống)"}
                    </div>
                    <div className="text-xs text-gray-400 text-right mt-1">
                        {formatTime(createdDate)}
                    </div>
                </div>
            </div>
        );
    }

    // Tin nhắn của người khác → bên trái
    return (
        <div className="flex items-end gap-2">
            {sender?.imageUrl ? (
                <img
                    src={sender.imageUrl}
                    alt={sender.fullName}
                    className="w-8 h-8 rounded-full object-cover"
                />
            ) : (
                <div
                    className="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center text-sm font-semibold">
                    {sender?.fullName?.charAt(0) || "?"}
                </div>
            )}
            <div className="max-w-[70%]">
                <div className="bg-gray-200 dark:bg-stoneBrown-700 px-4 py-2 rounded-2xl rounded-bl-sm">
                    {messageText || "(Tin nhắn trống)"}
                </div>
                <div className="text-xs text-gray-400 mt-1">
                    {formatTime(createdDate)}
                </div>
            </div>
        </div>
    );
};

export default ChatBox;