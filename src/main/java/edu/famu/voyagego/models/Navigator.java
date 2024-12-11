package edu.famu.voyagego.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
public class Navigator {
    @DocumentId
    private String navigatorId;
    private String userId;
    private String currentLocation;
    private List<String> nearbyDestinations;   // Changed from String[] to List<String>
    private List<String> recommendedRoutes;    // Changed from String[] to List<String>
    private List<String> popularAttractions;

    public String getNavigatorId() {
        return navigatorId;
    }

    public void setNavigatorId(String navigatorId) {
        this.navigatorId = navigatorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<String> getNearbyDestinations() {
        return nearbyDestinations;
    }

    public void setNearbyDestinations(List<String> nearbyDestinations) {
        this.nearbyDestinations = nearbyDestinations;
    }

    public List<String> getRecommendedRoutes() {
        return recommendedRoutes;
    }

    public void setRecommendedRoutes(List<String> recommendedRoutes) {
        this.recommendedRoutes = recommendedRoutes;
    }

    public List<String> getPopularAttractions() {
        return popularAttractions;
    }

    public void setPopularAttractions(List<String> popularAttractions) {
        this.popularAttractions = popularAttractions;
    }
}
