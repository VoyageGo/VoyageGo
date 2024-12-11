package edu.famu.voyagego.models;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/feed")
public class HomeFeedController {

    @Autowired
    private Firestore firestore;

    @GetMapping("/welcome/{userId}")
    public String getWelcomeMessage(@PathVariable String userId) {
        return "Welcome back, " + userId + "!";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam String query) {
        return List.of("Search result 1", "Search result 2");
    }

    @GetMapping("/recommendations/locations/{userId}")
    public List<String> getLocationRecommendations(@PathVariable String userId) {
        return List.of("Location 1", "Location 2");
    }

    @GetMapping("/recommendations/events/{userId}")
    public List<String> getEventRecommendations(@PathVariable String userId) {
        return List.of("Event 1", "Event 2");
    }

    // Create a new HomeFeed for the user
    @PostMapping("/create")
    public String createHomeFeed(@RequestBody HomeFeed homeFeed) {
        try {
            // Save the HomeFeed object to Firestore
            firestore.collection("homeFeeds").document(homeFeed.getFeedId()).set(homeFeed);
            return "HomeFeed created successfully!";
        } catch (Exception e) {
            return "Error creating HomeFeed: " + e.getMessage();
        }
    }

    // Get HomeFeed by feedId
    @GetMapping("/{feedId}")
    public HomeFeed getHomeFeed(@PathVariable String feedId) {
        try {
            DocumentSnapshot doc = firestore.collection("homeFeeds").document(feedId).get().get();
            if (doc.exists()) {
                return doc.toObject(HomeFeed.class);
            } else {
                throw new RuntimeException("HomeFeed not found for feedId: " + feedId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving HomeFeed: " + e.getMessage());
        }
    }

    // Update an existing HomeFeed by feedId
    @PutMapping("/{feedId}")
    public String updateHomeFeed(@PathVariable String feedId, @RequestBody HomeFeed homeFeed) {
        try {
            // Prepare the updated HomeFeed data
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("userId", homeFeed.getUserId());
            updatedData.put("personalizedMessage", homeFeed.getPersonalizedMessage());
            updatedData.put("searchQuery", homeFeed.getSearchQuery());
            updatedData.put("locationRecommendations", homeFeed.getLocationRecommendations());
            updatedData.put("eventRecommendations", homeFeed.getEventRecommendations());
            updatedData.put("upcomingChallenges", homeFeed.getUpcomingChallenges());
            updatedData.put("recentActivity", homeFeed.getRecentActivity());
            updatedData.put("favoriteLocations", homeFeed.getFavoriteLocations());
            updatedData.put("favoriteEvents", homeFeed.getFavoriteEvents());
            updatedData.put("rewardPoints", homeFeed.getRewardPoints());

            // Update the document in Firestore
            firestore.collection("homeFeeds").document(feedId).update(updatedData);
            return "HomeFeed updated successfully!";
        } catch (Exception e) {
            return "Error updating HomeFeed: " + e.getMessage();
        }
    }

    // Delete a HomeFeed by feedId
    @DeleteMapping("/{feedId}")
    public String deleteHomeFeed(@PathVariable String feedId) {
        try {
            // Delete the HomeFeed document from Firestore
            firestore.collection("homeFeeds").document(feedId).delete();
            return "HomeFeed deleted successfully!";
        } catch (Exception e) {
            return "Error deleting HomeFeed: " + e.getMessage();
        }
    }
}
