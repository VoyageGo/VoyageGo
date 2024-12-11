package edu.famu.voyagego.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private Firestore firestore;

    // Add a new recommendation
    @PostMapping
    public String addRecommendation(@RequestBody Recommendations recommendation) {
        try {
            // Save the recommendation in Firestore
            firestore.collection("recommendations").document(recommendation.getRecommendationId()).set(recommendation).get();
            return "Recommendation added successfully!";
        } catch (Exception e) {
            return "Error adding recommendation: " + e.getMessage();
        }
    }

    // Get all recommendations for a specific user by userId
    @GetMapping("/{userId}")
    public List<Recommendations> getRecommendations(@PathVariable String userId) throws ExecutionException, InterruptedException {
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection("recommendations")
                    .whereEqualTo("userId", userId)
                    .get();
            List<Recommendations> recommendations = query.get().toObjects(Recommendations.class);
            return recommendations;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving recommendations: " + e.getMessage());
        }
    }

    // Get a specific recommendation by recommendationId
    @GetMapping("/details/{recommendationId}")
    public Recommendations getRecommendation(@PathVariable String recommendationId) throws ExecutionException, InterruptedException {
        try {
            DocumentSnapshot documentSnapshot = firestore.collection("recommendations")
                    .document(recommendationId)
                    .get()
                    .get();
            if (documentSnapshot.exists()) {
                return documentSnapshot.toObject(Recommendations.class);
            } else {
                throw new RuntimeException("Recommendation not found for ID: " + recommendationId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving recommendation: " + e.getMessage());
        }
    }

    // Delete a recommendation by recommendationId
    @DeleteMapping("/delete/{recommendationId}")
    public String deleteRecommendation(@PathVariable String recommendationId) {
        try {
            firestore.collection("recommendations").document(recommendationId).delete().get();
            return "Recommendation deleted successfully!";
        } catch (Exception e) {
            return "Error deleting recommendation: " + e.getMessage();
        }
    }
}

