import React from "react";
import { Navigate } from "react-router-dom";

function ProtectedRoutes({ children }) {
    const profileId = localStorage.getItem("profileId");

    if (!profileId) {
        console.warn("Access denied. Redirecting to login.");
        return <Navigate to="/login" />;
    }

    return children;
}

export default ProtectedRoutes;
