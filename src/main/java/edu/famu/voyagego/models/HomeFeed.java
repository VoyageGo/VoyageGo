package edu.famu.voyagego.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class HomeFeed {
    @DocumentId
    private String feedId;
    private String userId;
    private String personalizedMessage;
    private String searchQuery;
    private List<String> locationRecommendations; // Change from String[] to List<String>
    private List<String> eventRecommendations;    // Change from String[] to List<String>
    private List<String> upcomingChallenges;      // Change from String[] to List<String>
    private List<String> recentActivity;         // Change from String[] to List<String>
    private List<String> favoriteLocations;      // Change from String[] to List<String>
    private List<String> favoriteEvents;
    private int rewardPoints;

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPersonalizedMessage() {
        return personalizedMessage;
    }

    public void setPersonalizedMessage(String personalizedMessage) {
        this.personalizedMessage = personalizedMessage;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getLocationRecommendations() {
        return locationRecommendations;
    }

    public void setLocationRecommendations(List<String> locationRecommendations) {
        this.locationRecommendations = locationRecommendations;
    }

    public List<String> getEventRecommendations() {
        return eventRecommendations;
    }

    public void setEventRecommendations(List<String> eventRecommendations) {
        this.eventRecommendations = eventRecommendations;
    }

    public List<String> getUpcomingChallenges() {
        return upcomingChallenges;
    }

    public void setUpcomingChallenges(List<String> upcomingChallenges) {
        this.upcomingChallenges = upcomingChallenges;
    }

    public List<String> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<String> recentActivity) {
        this.recentActivity = recentActivity;
    }

    public List<String> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(List<String> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public List<String> getFavoriteEvents() {
        return favoriteEvents;
    }

    public void setFavoriteEvents(List<String> favoriteEvents) {
        this.favoriteEvents = favoriteEvents;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
