import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function ProfileCreation() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newProfile = { name, email, password };
            console.log("Sending profile data:", newProfile); // Debugging log
            const response = await axios.post("http://localhost:8080/api/profiles/create", newProfile);

            if (response.data && response.data.profileId) {
                localStorage.setItem("profileId", response.data.profileId);
                navigate("/dashboard", { state: { newUser: true } });
            } else {
                console.error("Profile creation response missing profileId.");
                alert("Failed to create profile. Please try again.");
            }
        } catch (error) {
            console.error("Error creating profile:", error);
            alert("Failed to create profile. Please try again.");
        }
    };


    return (
        <div className="container mt-5">
            <h2>Create Profile</h2>
            <form onSubmit={handleSubmit}>
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
                    <label className="form-label">Email</label>
                    <input
                        type="email"
                        className="form-control"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Password</label>
                    <input
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );
}

export default ProfileCreation;
