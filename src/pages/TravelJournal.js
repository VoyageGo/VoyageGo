import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function TravelJournal({ onLogout }) {
    const [entries, setEntries] = useState([]);
    const [newEntry, setNewEntry] = useState({ title: "", content: "", location: "" });
    const profileId = localStorage.getItem("profileId");
    const [activeTab, setActiveTab] = useState("travel-journal");
    const navigate = useNavigate();

    const handleTabClick = (tab) => {
        setActiveTab(tab);
        const routes = {
            dashboard: "/dashboard",
            profile: "/profile",
            map: "/map",
            rewards: "/rewards",
            chat: "/chat",
            "travel-journal": "/travel-journal",
            settings: "/settings",
            logout: "/login",
        };
        if (tab === "logout") {
            localStorage.removeItem("profileId");
            onLogout();
        }
        navigate(routes[tab]);
    };

    // Fetch journal entries
    useEffect(() => {
        const fetchEntries = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/journal/${profileId}`);
                setEntries(response.data || []);
            } catch (error) {
                console.error("Error fetching journal entries:", error);
            }
        };

        if (profileId) fetchEntries();
    }, [profileId]);

    // Add new journal entry
    const handleCreateEntry = async () => {
        if (!newEntry.title || !newEntry.content || !newEntry.location) {
            alert("Please fill in all fields!");
            return;
        }
        try {
            await axios.post("http://localhost:8080/api/journal/create", {
                ...newEntry,
                profileId,
                date: new Date().toISOString(),
            });
            setNewEntry({ title: "", content: "", location: "" });
            const response = await axios.get(`http://localhost:8080/api/journal/${profileId}`);
            setEntries(response.data || []);
        } catch (error) {
            console.error("Error creating journal entry:", error);
        }
    };

    // Delete a journal entry
    const handleDeleteEntry = async (entryId) => {
        try {
            await axios.delete(`http://localhost:8080/api/journal/${entryId}`);
            setEntries(entries.filter((entry) => entry.entryId !== entryId));
        } catch (error) {
            console.error("Error deleting journal entry:", error);
        }
    };

    return (
        <div className="container mt-5">
            <header className="bg-primary text-white p-4 text-center">
                <h1>Travel Journal</h1>
                <h2>Document your Travels</h2>
            </header>

            <nav>
                <ul className="nav nav-tabs">
                    {["dashboard", "profile", "map", "missions", "rewards", "travel-journal", "chat", "settings", "logout"].map((tab) => (
                        <li className="nav-item" key={tab}>
                            <button
                                className={`nav-link ${activeTab === tab ? "active" : ""}`}
                                onClick={() => handleTabClick(tab)}
                            >
                                {tab.replace("-", " ").replace(/\b\w/g, (c) => c.toUpperCase())}
                            </button>
                        </li>
                    ))}
                </ul>
            </nav>

            <div className="container mt-4">
                <h3>Create New Journal Entry</h3>
                <input
                    type="text"
                    placeholder="Title"
                    className="form-control"
                    value={newEntry.title || ""}
                    onChange={(e) => setNewEntry({ ...newEntry, title: e.target.value })}
                />
                <textarea
                    placeholder="Content"
                    className="form-control mt-2"
                    value={newEntry.content || ""}
                    onChange={(e) => setNewEntry({ ...newEntry, content: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Location"
                    className="form-control mt-2"
                    value={newEntry.location || ""}
                    onChange={(e) => setNewEntry({ ...newEntry, location: e.target.value })}
                />
                <button className="btn btn-primary mt-2" onClick={handleCreateEntry}>
                    Add Entry
                </button>

                <h3 className="mt-4">Your Journal Entries</h3>
                {entries.length > 0 ? (
                    entries.map((entry) => (
                        <div key={entry.entryId} className="card mb-3">
                            <div className="card-body">
                                <h5>{entry.title}</h5>
                                <p>{entry.content}</p>
                                <p>
                                    <strong>Location:</strong> {entry.location}
                                </p>
                                <p>
                                    <strong>Date:</strong> {new Date(entry.date).toLocaleString()}
                                </p>
                                <button
                                    className="btn btn-danger"
                                    onClick={() => handleDeleteEntry(entry.entryId)}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No journal entries yet. Start documenting your travels!</p>
                )}
            </div>
        </div>
    );
}

export default TravelJournal;
