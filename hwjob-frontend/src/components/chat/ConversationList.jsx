import useChat from "../../hooks/useChat.jsx";


const ConversationList = () => {
    const {
        conversations,
        setActiveConversation,
        loading
    } = useChat();

    if (loading) {
        return <div className="p-4 text-center">Đang tải...</div>;
    }

    if (conversations.length === 0) {
        return (
            <div className="p-4 text-center text-gray-500">
                Chưa có cuộc trò chuyện nào
            </div>
        );
    }

    const items = [];
    for (const c of conversations) {
        items.push(
            <div
                key={c.id}
                onClick={() => setActiveConversation(c)}
                className="
                    flex items-center gap-3
                    px-4 py-3 cursor-pointer
                    hover:bg-gray-100 dark:hover:bg-stoneBrown-800
                "
            >
                {c.conversationImage ? (
                    <img
                        src={c.conversationImage}
                        alt={c.conversationName}
                        className="w-10 h-10 rounded-full"
                    />
                ) : (
                    <div className="w-10 h-10 bg-gray-300 rounded-full flex items-center justify-center">
                        {c.conversationName?.charAt(0)}
                    </div>
                )}

                <div className="flex-1 truncate">
                    <div className="font-medium truncate">
                        {c.conversationName}
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="divide-y overflow-y-auto h-full">
            {items}
        </div>
    );
};

export default ConversationList;