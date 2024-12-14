import React, { useEffect, useState, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";

function Map({ onLogout }) {
    const [userLocation, setUserLocation] = useState(null);
    const [recommendations, setRecommendations] = useState([]);
    const mapRef = useRef(null);
    const markersRef = useRef([]);
    const routeLayerRef = useRef(null);
    const [activeTab, setActiveTab] = useState("map");
    const navigate = useNavigate();

    useEffect(() => {
        if (!mapRef.current) {
            mapRef.current = L.map("map").setView([0, 0], 13);

            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                attribution:
                    '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            }).addTo(mapRef.current);
        }

        navigator.geolocation.getCurrentPosition(
            (position) => {
                const userLatLng = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };
                setUserLocation(userLatLng);
                mapRef.current.setView([userLatLng.lat, userLatLng.lng], 13);

                L.marker([userLatLng.lat, userLatLng.lng], {
                    title: "Your Location",
                    icon: L.icon({
                        iconUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png",
                        shadowUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png",
                        iconSize: [25, 41],
                        iconAnchor: [12, 41],
                    }),
                })
                    .addTo(mapRef.current)
                    .bindPopup("You are here.")
                    .openPopup();
            },
            (error) => console.error("Error fetching geolocation:", error)
        );
    }, []);

    useEffect(() => {
        if (!userLocation) return;

        const fetchRecommendations = async () => {
            try {
                const profileId = localStorage.getItem("profileId");
                const response = await axios.get(`http://localhost:8080/recommendations/filter`, {
                    params: {
                        latitude: userLocation.lat,
                        longitude: userLocation.lng,
                        radius: 50,
                    },
                });
                setRecommendations(response.data);
            } catch (error) {
                console.error("Error fetching recommendations:", error);
            }
        };

        fetchRecommendations();
    }, [userLocation]);

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        if (tab === "dashboard") navigate("/dashboard");
        if (tab === "profile") navigate("/profile");
        if (tab === "missions") navigate("/missions");
        if (tab === "rewards") navigate("./rewards");
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

            <div className="container my-4">
                <h2>Map and Directions</h2>
                <div id="map" style={{width: "100%", height: "500px"}}></div>
            </div>
        </div>
    );
}

export default Map;
