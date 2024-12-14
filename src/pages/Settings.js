import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Settings({ onLogout }) {
    const [darkMode, setDarkMode] = useState(false);
    const [language, setLanguage] = useState("en");
    const profileId = localStorage.getItem("profileId") || "";
    const [activeTab, setActiveTab] = useState("settings");
    const [message, setMessage] = useState(""); // State to display messages
    const navigate = useNavigate();

    // Apply dark mode class to the body element
    useEffect(() => {
        document.body.className = darkMode ? "dark-mode" : "";
    }, [darkMode]);

    // Fetch settings on mount
    useEffect(() => {
        const fetchSettings = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/settings/${profileId}`);
                const data = response.data;
                setDarkMode(data.darkMode || false);
                setLanguage(data.language || "en");
            } catch (error) {
                setMessage("Error fetching settings.");
                console.error("Error fetching settings:", error);
            }
        };

        if (profileId) fetchSettings();
    }, [profileId]);

    const handleSaveSettings = async () => {
        try {
            await axios.put("http://localhost:8080/api/settings/update", {
                profileId,
                updates: {
                    darkMode,
                    language,
                },
            });
            setMessage("Settings updated successfully!");
        } catch (error) {
            setMessage("Failed to save settings. Please try again.");
            console.error("Error saving settings:", error);
        }
    };

    const handleDeleteAccount = async () => {
        try {

            const response = await axios.delete(`http://localhost:8080/api/settings/${profileId}`);
            if (response.data) {
                localStorage.removeItem("profileId");
                setMessage("Account deleted successfully!");
                navigate("/login")
            }

        } catch (error) {
            setMessage("Failed to delete account. Please try again.");
            console.error("Error deleting account:", error);
        }
    };

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        navigate(`/${tab}`);
    };

    return (
        <div className="container mt-5">
            <header className="bg-primary text-white p-4 text-center">
                <h1>Settings</h1>
            </header>

            <nav>
                <ul className="nav nav-tabs">
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "dashboard" ? "active" : ""}`}
                            onClick={() => handleTabClick("dashboard")}
                        >
                            Dashboard
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
                        <button
                            className={`nav-link ${activeTab === "logout" ? "active" : ""}`}
                            style={{ fontWeight: "bold" }}
                            onClick={onLogout}
                        >
                            Logout
                        </button>
                    </li>
                </ul>
            </nav>

            <div className="container mt-4">
                <h2>Customize Your Settings</h2>
                {message && <div className="alert alert-info mt-3">{message}</div>}
                <div className="form-group">
                    <label>Dark Mode</label>
                    <input
                        type="checkbox"
                        checked={darkMode}
                        onChange={(e) => setDarkMode(e.target.checked)}
                    />
                </div>
                <div className="form-group mt-3">
                    <label>Language</label>
                    <select
                        className="form-control"
                        value={language}
                        onChange={(e) => setLanguage(e.target.value)}
                    >
                        <option value="en">English</option>
                        <option value="es">Spanish</option>
                        <option value="fr">French</option>
                        <option value="de">German</option>
                    </select>
                </div>
                <button className="btn btn-primary mt-3" onClick={handleSaveSettings}>
                    Save Settings
                </button>
                <button className="btn btn-danger mt-3" onClick={handleDeleteAccount}>
                    Delete Account
                </button>
            </div>
        </div>
    );
}

export default Settings;
