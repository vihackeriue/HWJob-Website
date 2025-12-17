import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import {BrowserRouter} from "react-router-dom";
import "./i18n/i18n";
import {AuthProvider} from "./contexts/auth/AuthProvider.jsx";
import ChatProvider from "./contexts/chat/ChatProvider.jsx";
import SocketProvider from "./contexts/socket/SocketProvider.jsx";

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <SocketProvider>
                    <ChatProvider>
                        <App/>
                    </ChatProvider>
                </SocketProvider>
            </AuthProvider>
        </BrowserRouter>
    </StrictMode>
);
