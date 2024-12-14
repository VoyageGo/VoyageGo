import React, { useState, useEffect } from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import axios from "axios";

function Dashboard({ onLogout, isProfileComplete }) {
    const [activeTab, setActiveTab] = useState("dashboard");
    const [recommendations, setRecommendations] = useState([]);
    const [profileName, setProfileName] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfileAndRecommendations = async () => {
            const profileId = localStorage.getItem("profileId");
            if (!profileId) return;

            try {
                const profileResponse = await axios.get(`http://localhost:8080/api/profiles/${profileId}`);
                const profileData = profileResponse.data;
                setProfileName(profileData.name || "Traveler");

                if (isProfileComplete) {
                    navigator.geolocation.getCurrentPosition(
                        async (position) => {
                            const { latitude, longitude } = position.coords;

                            const recResponse = await axios.post(
                                `http://localhost:8080/recommendations/filter`,
                                { preferences: profileData.preferences },
                                {
                                    params: { latitude, longitude, radius: 50 },
                                }
                            );
                            setRecommendations(recResponse.data.slice(0, 5));
                        },
                        (error) => console.error("Geolocation error:", error)
                    );
                }
            } catch (error) {
                console.error("Error fetching profile or recommendations:", error);
            }
        };

        fetchProfileAndRecommendations();
    }, [isProfileComplete]);

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "profile") navigate("/profile");
        if (tab === "map") navigate("/map");
        if (tab === "missions") navigate("/missions");
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
            {/* Header */}
            <header className="bg-primary text-white p-4 text-center">
                <h1>VoyageGo</h1>
                <h2>Welcome, {profileName}!</h2>
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

            {/* Main Content */}
            <div className="container my-4">
                <h2>Welcome to VoyageGo Dashboard!</h2>
                <p>Your personalized travel companion. Explore recommended places and complete missions for points!</p>

                {isProfileComplete ? (
                    <div>
                    <h3>Recommended for You</h3>
                        <div className="row">
                            {recommendations.length > 0 ? (
                                recommendations.map((rec, index) => (
                                    <div className="col-md-4 mb-4" key={index}>
                                        <div className="card h-100 shadow-sm">
                                            <div className="card-body">
                                                <h5 className="card-title">{rec.type}</h5>
                                                <p className="card-text">{rec.description}</p>
                                                <p className="text-muted">
                                                    <small>{rec.location}</small>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p>No recommendations available. Go to Profile and add your Travel Preferences!</p>
                            )}
                        </div>
                    </div>
                ) : (
                    <div className="alert alert-warning mt-4">
                        <h4>Complete Your Profile</h4>
                        <p>Your profile setup is incomplete. Please complete your profile to unlock all features.</p>
                        <button className="btn btn-primary" onClick={() => handleTabClick("profile")}>
                            Complete Profile Setup
                        </button>
                    </div>
                )}
            </div>

            <footer>
                <p>Thank you for using VoyageGo. Safe travels!</p>
            </footer>
        </div>
    );
}

export default Dashboard;
