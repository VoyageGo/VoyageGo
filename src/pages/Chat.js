import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Chat({ onLogout }) {
    const [message, setMessage] = useState("");
    const [response, setResponse] = useState("");
    const [activeTab, setActiveTab] = useState("chat");
    const navigate = useNavigate();

    const handleSendMessage = async () => {
        try {
            const res = await axios.post("http://localhost:8080/api/chat", { message });
            setResponse(res.data || "No response from AI.");
        } catch (error) {
            console.error("Error fetching AI response:", error);
            setResponse("Failed to get a response from AI. Please try again.");
        }
    };


    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "profile") navigate("/profile");
        if (tab === "map") navigate("/map");
        if (tab === "missions") navigate("/missions");
        if (tab === "rewards") navigate("/rewards");
        if (tab === "settings") navigate("/settings");
        if (tab === "chat") navigate("/chat");
        if (tab === "logout") {
            localStorage.removeItem("profileId");
            onLogout();
            navigate("/login");
        }
    };

    return (
        <div className="container mt-5">
            <header className="bg-primary text-white p-4 text-center">
                <h1>VoyageGo</h1>
                <h2>AI Chat</h2>
            </header>
            <nav>
                <ul className="nav nav-tabs">
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "dashboard" ? "active" : ""}`}
                                onClick={() => handleTabClick("dashboard")}>Dashboard
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "profile" ? "active" : ""}`}
                                onClick={() => handleTabClick("profile")}>Profile
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "map" ? "active" : ""}`}
                                onClick={() => handleTabClick("map")}>Map
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "missions" ? "active" : ""}`}
                                onClick={() => handleTabClick("missions")}>Missions
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "rewards" ? "active" : ""}`}
                                onClick={() => handleTabClick("rewards")}>Rewards
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "travel-journal" ? "active" : ""}`}
                            onClick={() => handleTabClick("travel-journal")}
                        >
                            Travel Journal
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "chat" ? "active" : ""}`}
                            onClick={() => handleTabClick("chat")}
                        >
                            Chat with AI
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "settings" ? "active" : ""}`}
                            onClick={() => handleTabClick("settings")}
                        >
                            Settings
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={`nav-link ${activeTab === "logout" ? "active" : ""}`}
                                onClick={() => handleTabClick("logout")}>Logout
                        </button>
                    </li>
                </ul>
            </nav>

            <div className="container mt-4">
                <h3>Ask AI Anything</h3>
                <textarea
                    className="form-control mb-3"
                    rows="4"
                    placeholder="Type your question here..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                ></textarea>
                <button className="btn btn-primary" onClick={handleSendMessage}>Send</button>
                <div className="mt-4">
                    <h5>AI Response:</h5>
                    <p>{response}</p>
                </div>
            </div>
        </div>
    );
}

export default Chat;
