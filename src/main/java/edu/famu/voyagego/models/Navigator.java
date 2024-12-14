package edu.famu.voyagego.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Navigator {

    @DocumentId
    private String navigatorId;
    private String profileId;
    private double currentLatitude;           // Latitude for current location
    private double currentLongitude;          // Longitude for current location
    private List<Location> nearbyDestinations; // List of nearby destinations
    private List<Route> recommendedRoutes;     // List of recommended routes
    private List<String> popularAttractions;   // List of popular attractions

    public String getNavigatorId() {
        return navigatorId;
    }

    public void setNavigatorId(String navigatorId) {
        this.navigatorId = navigatorId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public List<Location> getNearbyDestinations() {
        return nearbyDestinations;
    }

    public void setNearbyDestinations(List<Location> nearbyDestinations) {
        this.nearbyDestinations = nearbyDestinations;
    }

    public List<Route> getRecommendedRoutes() {
        return recommendedRoutes;
    }

    public void setRecommendedRoutes(List<Route> recommendedRoutes) {
        this.recommendedRoutes = recommendedRoutes;
    }

    public List<String> getPopularAttractions() {
        return popularAttractions;
    }

    public void setPopularAttractions(List<String> popularAttractions) {
        this.popularAttractions = popularAttractions;
    }
}
