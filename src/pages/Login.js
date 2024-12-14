import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Login({ onLogin }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/api/profiles/login", { email, password });
            const { profileId, isProfileComplete } = response.data;

            // Pass profileId and isProfileComplete to App.js
            onLogin(profileId, isProfileComplete);

            navigate("/dashboard");
        } catch (error) {
            console.error("Login failed:", error);
            setErrorMessage("Failed to log in. Please try again.");
        }
    };

    const handleCreateProfile = () => {
        navigate("/create-profile");
    };

    return (
        <div className="container mt-5">
            <header className="bg-primary text-white p-4 text-center">
                <h1>VoyageGo</h1>
            </header>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
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
                <button type="submit" className="btn btn-primary">Login</button>
                {errorMessage && <div className="alert alert-danger mt-3">{errorMessage}</div>}
            </form>
            <p className="mt-3">
                Don’t have an account?{" "}
                <button className="btn btn-link" onClick={handleCreateProfile}>
                    Create Profile
                </button>
            </p>
        </div>
    );
}

export default Login;
