import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Rewards({ onLogout }) {
    const [availableRewards, setAvailableRewards] = useState([]);
    const [redeemedRewards, setRedeemedRewards] = useState([]);
    const [points, setPoints] = useState(0);
    const [activeTab, setActiveTab] = useState("rewards");
    const profileId = localStorage.getItem("profileId");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRewardsAndPoints = async () => {
            try {
                // Fetch available rewards
                const rewardsResponse = await axios.get("http://localhost:8080/api/rewards/available", {
                    params: { location: "Tallahassee", profileId },
                });
                setAvailableRewards(rewardsResponse.data);

                // Fetch redeemed rewards
                const redeemedResponse = await axios.get("http://localhost:8080/api/rewards/redeemed", {
                    params: { profileId },
                });
                setRedeemedRewards(redeemedResponse.data);

                // Fetch user points
                const profileResponse = await axios.get(`http://localhost:8080/api/profiles/${profileId}`);
                setPoints(profileResponse.data.points || 0);
            } catch (error) {
                console.error("Error fetching rewards or points:", error);
            }
        };

        fetchRewardsAndPoints();
    }, [profileId]);

    const handleRedeem = async (rewardId) => {
        try {
            const response = await axios.post("http://localhost:8080/api/rewards/redeem", {
                profileId,
                rewardId,
            });
            alert(response.data);

            // Fetch updated data
            const rewardsResponse = await axios.get("http://localhost:8080/api/rewards/available", {
                params: { location: "Tallahassee", profileId },
            });
            setAvailableRewards(rewardsResponse.data);

            const redeemedResponse = await axios.get("http://localhost:8080/api/rewards/redeemed", {
                params: { profileId },
            });
            setRedeemedRewards(redeemedResponse.data);

            const profileResponse = await axios.get(`http://localhost:8080/api/profiles/${profileId}`);
            setPoints(profileResponse.data.points || 0);
        } catch (error) {
            console.error("Error redeeming reward:", error);
        }
    };

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "profile") navigate("/profile");
        if (tab === "map") navigate("/map");
        if (tab === "missions") navigate("/missions");
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
                <h2>Total Points: {points}</h2>
            </header>

            {/* Navigation Tabs */}
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
                <h2>Available Rewards</h2>
                {availableRewards.length > 0 ? (
                    <div className="list-group">
                        {availableRewards.map((reward) => (
                            <div key={reward.rewardId} className="list-group-item">
                                <h5>{reward.description}</h5>
                                <p><strong>Required Points:</strong> {reward.requiredPoints}</p>
                                <button className="btn btn-primary"
                                        onClick={() => handleRedeem(reward.rewardId)}>Redeem Reward
                                </button>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No rewards available. Earn more points to unlock rewards!</p>
                )}

                <h2 className="mt-5">Redeemed Rewards</h2>
                {redeemedRewards.length > 0 ? (
                    <div className="list-group">
                        {redeemedRewards.map((reward) => (
                            <div key={reward.rewardId} className="list-group-item list-group-item-success">
                                <h5>{reward.description}</h5>
                                <p><strong>Required Points:</strong> {reward.requiredPoints}</p>
                                <span className="badge bg-success">Redeemed</span>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>You haven't redeemed any rewards yet.</p>
                )}
            </div>
        </div>
    );
}

export default Rewards;
