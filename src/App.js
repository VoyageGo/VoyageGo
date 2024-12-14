import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import ProfileCreation from "./pages/ProfileCreation";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import Map from "./pages/Map";
import Missions from "./pages/Missions";
import Rewards from "./pages/Rewards";
import Chat from "./pages/Chat";
import TravelJournal from "./pages/TravelJournal";
import Settings from "./pages/Settings";


function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isProfileComplete, setIsProfileComplete] = useState(false);

    const handleLogin = (profileId, profileComplete) => {
        // Store the profileId in localStorage
        localStorage.setItem("profileId", profileId);

        // Set the login state
        setIsLoggedIn(true);
        setIsProfileComplete(profileComplete);
    };

    const handleLogout = () => {
        // Remove profileId and reset states
        localStorage.removeItem("profileId");
        setIsLoggedIn(false);
        setIsProfileComplete(false);
    };

    return (
        <Router>
            <Routes>
                {!isLoggedIn && (
                    <>
                        {/* Routes for unauthenticated users */}
                        <Route path="/login" element={<Login onLogin={handleLogin} />} />
                        <Route path="/create-profile" element={<ProfileCreation />} />
                        <Route path="*" element={<Navigate to="/login" replace />} />
                    </>
                )}
                {isLoggedIn && (
                    <>
                        {/* Routes for authenticated users */}
                        <Route
                            path="/dashboard"
                            element={
                                <Dashboard
                                    onLogout={handleLogout}
                                    isProfileComplete={isProfileComplete}
                                />
                            }
                        />
                        <Route path="/profile" element={<Profile />} />
                        <Route path="/map" element={<Map />} />
                        <Route path="/rewards" element={<Rewards />} />
                        <Route path="/missions" element={<Missions onLogout={handleLogout} />} />
                        <Route path="/settings" element={<Settings />} />
                        <Route path="/travel-journal" element={<TravelJournal />} />
                        <Route path="/chat" element={<Chat />} />
                        <Route path="*" element={<Navigate to="/dashboard" replace />} />
                    </>
                )}
            </Routes>
        </Router>
    );
}

export default App;
