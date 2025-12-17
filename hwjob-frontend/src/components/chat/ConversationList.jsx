import useChat from "../../hooks/useChat.jsx";

const mockConversations = [
    {
        id: "1",
        name: "HR - Công ty A",
        avatar: "https://i.pravatar.cc/40?img=1",
        lastMessage: "Chào bạn",
    },
    {
        id: "2",
        name: "Recruiter B",
        avatar: "https://i.pravatar.cc/40?img=2",
        lastMessage: "CV của bạn...",
    },
    {
        id: "3",
        name: "Support",
        avatar: null,
        lastMessage: "Chúng tôi đã nhận...",
    },
];

const ConversationList = () => {
    const {setActiveConversation} = useChat();

    return (
        <div className="divide-y overflow-y-auto h-full">
            {mockConversations.map((c) => (
                <div
                    key={c.id}
                    onClick={() => setActiveConversation(c)}
                    className="
                        flex items-center gap-3
                        px-4 py-3 cursor-pointer
                        hover:bg-gray-100 dark:hover:bg-stoneBrown-800
                    "
                >
                    {/* Avatar */}
                    {c.avatar ? (
                        <img
                            src={c.avatar}
                            alt={c.name}
                            className="w-10 h-10 rounded-full object-cover"
                        />
                    ) : (
                        <div className="
                            w-10 h-10 rounded-full
                            bg-gray-300 text-gray-700
                            flex items-center justify-center
                            font-semibold
                        ">
                            {c.name.charAt(0)}
                        </div>
                    )}

                    {/* Info */}
                    <div className="flex-1 min-w-0">
                        <div className="font-medium truncate">
                            {c.name}
                        </div>
                        <div className="text-sm text-gray-500 truncate">
                            {c.lastMessage}
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ConversationList;