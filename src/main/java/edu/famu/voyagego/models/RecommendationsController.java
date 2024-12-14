package edu.famu.voyagego.models;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private Firestore firestore;

    @GetMapping("/{latitude}/{longitude}")
    public ResponseEntity<List<Recommendations>> getRecommendationsByLocationAndPreferences(
            @PathVariable double latitude,
            @PathVariable double longitude,
            @RequestParam double radius,
            @RequestParam List<String> preferences) {
        try {
            // Fetch all recommendations
            var querySnapshot = firestore.collection("recommendations").get().get();
            var allRecommendations = querySnapshot.toObjects(Recommendations.class);

            // Filter recommendations by distance and preferences
            List<Recommendations> filteredRecommendations = allRecommendations.stream()
                    .filter(r -> distance(latitude, longitude, r.getLatitude(), r.getLongitude()) <= radius)
                    .filter(r -> preferences.contains(r.getType()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filteredRecommendations);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    @PostMapping
    public ResponseEntity<String> addRecommendation(@RequestBody Recommendations recommendation) {
        try {
            // Generate a recommendation ID if not provided
            if (recommendation.getRecommendationId() == null || recommendation.getRecommendationId().isEmpty()) {
                recommendation.setRecommendationId(firestore.collection("recommendations").document().getId());
            }

            // Save the recommendation to Firestore
            firestore.collection("recommendations").document(recommendation.getRecommendationId()).set(recommendation).get();

            return ResponseEntity.status(201).body("Recommendation added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding recommendation: " + e.getMessage());
        }
    }

    @PostMapping("/filter")
    public List<Recommendations> filterRecommendations(@RequestBody Map<String, List<String>> filter) {
        try {
            List<String> preferences = filter.get("preferences");

            // Fetch all recommendations
            var querySnapshot = firestore.collection("recommendations").get().get();
            var allRecommendations = querySnapshot.toObjects(Recommendations.class);

            // Filter recommendations based on preferences
            return allRecommendations.stream()
                    .filter(r -> preferences.contains(r.getType()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error filtering recommendations: " + e.getMessage());
        }
    }



}
