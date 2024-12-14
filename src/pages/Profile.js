import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function Profile() {
    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [bio, setBio] = useState("");
    const [preferences, setPreferences] = useState([]);
    const [activeTab, setActiveTab] = useState("profile");
    const profileId = localStorage.getItem("profileId");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/profiles/${profileId}`);
                const data = response.data;

                setName(data.name || "");
                setEmail(data.email || ""); // Pre-fill email
                setBio(data.bio || "");
                setPreferences(data.preferences || []);
            } catch (error) {
                console.error("Error fetching profile:", error);
            }
        };

        fetchProfile();
    }, [profileId]);

    const handleUpdate = async () => {
        try {
            const updatedProfile = { name, bio, preferences, email }; // Include email but read-only
            await axios.put(`http://localhost:8080/api/profiles/${profileId}`, updatedProfile);
            setIsEditing(false);
        } catch (error) {
            console.error("Error updating profile:", error);
        }
    };

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "map") navigate("/map");
        if (tab === "missions") navigate("/missions");
        if (tab === "missions") navigate("/missions");
        if (tab === "chat") navigate("/chat");
        if (tab === "travel-journal") navigate("/travel-journal");
        if (tab === "settings") navigate("/settings");
        if (tab === "logout") {
            localStorage.removeItem("profileId");
            navigate("/login");
        }
    };

    return (
        <div className="container mt-5">
            {/* Header */}
            <header className="bg-primary text-white p-4 text-center">
                <h1>VoyageGo</h1>
                <h2>Welcome, {name || "Traveler"}!</h2>
            </header>

            {/* Navigation Tabs */}
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

            {/* Profile Content */}
            <div className="container mt-4">
                <h2>Your Profile</h2>
                {isEditing ? (
                    <form>
                        <div className="mb-3">
                            <label className="form-label">Name</label>
                            <input
                                type="text"
                                className="form-control"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Email (Read-only)</label>
                            <input
                                type="email"
                                className="form-control"
                                value={email}
                                readOnly
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Bio</label>
                            <textarea
                                className="form-control"
                                value={bio}
                                onChange={(e) => setBio(e.target.value)}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Preferences</label>
                            {["Cultural Experiences", "Adventure Activities", "Relaxation", "Nightlife", "Historical Sites"].map(
                                (preference) => (
                                    <div key={preference} className="form-check">
                                        <input
                                            type="checkbox"
                                            id={preference}
                                            className="form-check-input"
                                            value={preference}
                                            checked={preferences.includes(preference)}
                                            onChange={(e) => {
                                                if (e.target.checked) {
                                                    setPreferences([...preferences, preference]);
                                                } else {
                                                    setPreferences(preferences.filter((p) => p !== preference));
                                                }
                                            }}
                                        />
                                        <label htmlFor={preference} className="form-check-label">
                                            {preference}
                                        </label>
                                    </div>
                                )
                            )}
                        </div>
                        <button type="button" className="btn btn-primary" onClick={handleUpdate}>
                            Save
                        </button>
                    </form>
                ) : (
                    <div>
                        <p><strong>Name:</strong> {name}</p>
                        <p><strong>Email:</strong> {email}</p>
                        <p><strong>Bio:</strong> {bio}</p>
                        <p><strong>Preferences:</strong> {preferences.join(", ")}</p>
                        <button className="btn btn-primary" onClick={() => setIsEditing(true)}>
                            Update Profile
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Profile;
