import {axiosPrivate} from "../api/axios.jsx";
import {ENDPOINTS} from "../config/endpoints.jsx";

export const chatService = {
    // Lấy danh sách conversations
    getConversations: () => {
        return axiosPrivate.get(ENDPOINTS.CHAT.CONVERSATIONS);
    },

    // Tạo conversation mới
    createConversation: (type, participantIds) => {
        const payload = {
            type,
            participantIds,
        };
        console.log("[chatService] createConversation payload:", payload);
        return axiosPrivate.post(ENDPOINTS.CHAT.CONVERSATIONS, payload);
    },

    // Lấy messages của conversation
    getMessages: (conversationId) => {
        return axiosPrivate.get(ENDPOINTS.CHAT.MESSAGES(conversationId));
    },
};


