import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Missions({ onLogout }) {
    const [availableMissions, setAvailableMissions] = useState([]);
    const [completedMissions, setCompletedMissions] = useState([]);
    const [activeTab, setActiveTab] = useState("missions");
    const profileId = localStorage.getItem("profileId");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMissions = async () => {
            try {
                // Fetch completed missions
                const completedResponse = await axios.get("http://localhost:8080/api/missions/completed", {
                    params: { profileId },
                });
                setCompletedMissions(completedResponse.data);

                // Fetch available missions
                const availableResponse = await axios.get("http://localhost:8080/api/missions/available", {
                    params: { profileId },
                });
                setAvailableMissions(availableResponse.data);
            } catch (error) {
                console.error("Error fetching missions:", error);
            }
        };

        fetchMissions();
    }, [profileId]);

    const handleCompleteMission = async (missionId) => {
        try {
            const response = await axios.post("http://localhost:8080/api/missions/complete", {
                profileId,
                missionId,
            });
            alert(response.data);

            // Fetch updated missions
            const completedResponse = await axios.get("http://localhost:8080/api/missions/completed", {
                params: { profileId },
            });
            setCompletedMissions(completedResponse.data);

            const availableResponse = await axios.get("http://localhost:8080/api/missions/available", {
                params: { profileId },
            });
            setAvailableMissions(availableResponse.data);
        } catch (error) {
            console.error("Error completing mission:", error);
            alert("Failed to complete mission. Please try again.");
        }
    };

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "profile") navigate("/profile");
        if (tab === "map") navigate("/map");
        if (tab === "rewards") navigate("/rewards");
        if (tab === "chat") navigate("/chat");
        if (tab === "travel-journal") navigate("/travel-journal");
        if (tab === "settings") navigate("/settings");
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
                            className={`nav-link ${activeTab === "profile" ? "active" : ""}`}
                            onClick={() => handleTabClick("profile")}
                        >
                            Profile
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "map" ? "active" : ""}`}
                            onClick={() => handleTabClick("map")}
                        >
                            Map
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "missions" ? "active" : ""}`}
                            onClick={() => handleTabClick("missions")}
                        >
                            Missions
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${activeTab === "rewards" ? "active" : ""}`}
                            onClick={() => handleTabClick("rewards")}
                        >
                            Rewards
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
                        <button
                            className={`nav-link ${activeTab === "logout" ? "active" : ""}`}
                            onClick={() => handleTabClick("logout")}
                        >
                            Logout
                        </button>
                    </li>
                </ul>
            </nav>

            <div className="container mt-4">
                <h2>Available Missions</h2>
                {availableMissions.length > 0 ? (
                    <div className="list-group">
                        {availableMissions.map((mission) => (
                            <div key={mission.missionId} className="list-group-item">
                                <p>{mission.description}</p>
                                <p><strong>Points:</strong> {mission.points}</p>
                                <button className="btn btn-primary"
                                        onClick={() => handleCompleteMission(mission.missionId)}>Complete Mission
                                </button>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No available missions. Check back later!</p>
                )}

                <h2 className="mt-5">Completed Missions</h2>
                {completedMissions.length > 0 ? (
                    <div className="list-group">
                        {completedMissions.map((mission) => (
                            <div key={mission.missionId} className="list-group-item list-group-item-success">
                                <p>{mission.description}</p>
                                <p><strong>Points:</strong> {mission.points}</p>
                                <span className="badge bg-success">Completed</span>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No completed missions yet.</p>
                )}
            </div>
        </div>
    );
}

export default Missions;
