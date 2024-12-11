package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private Firestore firestore;

    // Add a new reward
    @PostMapping
    public String createReward(@RequestBody Rewards newReward) {
        try {
            // Create a document in the 'rewards' collection with the new reward
            DocumentReference docRef = firestore.collection("rewards").document(newReward.getRewardId());
            docRef.set(newReward).get(); // Save the reward document in Firestore

            return "Reward created successfully!";
        } catch (Exception e) {
            return "Error creating reward: " + e.getMessage();
        }
    }

    // Get user's reward points
    @GetMapping("/balance/{userId}")
    public String getRewardPoints(@PathVariable String userId) {
        try {
            // Fetch the user's document from Firestore
            DocumentSnapshot userSnapshot = firestore.collection("users").document(userId).get().get();

            if (!userSnapshot.exists()) {
                return "Error: User with userId " + userId + " not found.";
            }

            // Fetch reward points from the user data (assuming the reward points are part of the user's document)
            int rewardPoints = userSnapshot.getLong("rewardPoints").intValue(); // Or the actual field where reward points are stored

            return "User reward points: " + rewardPoints;
        } catch (Exception e) {
            return "Error retrieving reward points: " + e.getMessage();
        }
    }

    // Get all available rewards
    @GetMapping("/available")
    public List<Rewards> getAvailableRewards() throws ExecutionException, InterruptedException {
        try {
            List<Rewards> availableRewards = new ArrayList<>();
            firestore.collection("rewards")
                    .get()
                    .get()
                    .forEach(doc -> {
                        Rewards reward = doc.toObject(Rewards.class);
                        if (reward != null) {
                            availableRewards.add(reward);
                        }
                    });
            return availableRewards;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving available rewards: " + e.getMessage());
        }
    }

    // Redeem a reward
    @PostMapping("/redeem")
    public ResponseEntity<String> redeemReward(@RequestBody RedeemRequest redeemRequest) {
        try {
            // Fetch the reward from Firestore
            DocumentSnapshot rewardSnapshot = firestore.collection("rewards")
                    .document(redeemRequest.getRewardId())
                    .get()
                    .get();

            if (rewardSnapshot.exists()) {
                Rewards reward = rewardSnapshot.toObject(Rewards.class);

                // Check if the reward has already been redeemed
                if (reward != null && !reward.isRedeemed()) {
                    // Fetch the user document
                    DocumentSnapshot userSnapshot = firestore.collection("users")
                            .document(redeemRequest.getUserId())
                            .get()
                            .get();

                    if (userSnapshot.exists()) {
                        long currentPoints = userSnapshot.contains("rewardPoints") ? userSnapshot.getLong("rewardPoints") : 0;

                        // Check if user has enough points to redeem the reward
                        if (currentPoints >= reward.getPointsRequired()) {
                            // Mark the reward as redeemed
                            firestore.collection("rewards")
                                    .document(redeemRequest.getRewardId())
                                    .update("isRedeemed", true, "redeemedBy", redeemRequest.getUserId());

                            // Update the user's reward points
                            firestore.collection("users")
                                    .document(redeemRequest.getUserId())
                                    .update("rewardPoints", currentPoints - reward.getPointsRequired());

                            // Optionally, log the redemption
                            firestore.collection("users")
                                    .document(redeemRequest.getUserId())
                                    .update("redeemedRewards", FieldValue.arrayUnion(redeemRequest.getRewardId()));

                            return ResponseEntity.status(HttpStatus.OK)
                                    .body("Reward redeemed successfully by user " + redeemRequest.getUserId());
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("Insufficient points for redemption.");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("User not found.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Reward is already redeemed or not available.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Reward not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error redeeming reward: " + e.getMessage());
        }
    }

    // Get the user's reward history
    @GetMapping("/history/{userId}")
    public List<String> getRewardHistory(@PathVariable String userId) {
        try {
            // Here you would need to fetch the history from Firestore or another source
            // This is just a mock response for now
            List<String> history = new ArrayList<>();
            history.add("Reward redeemed: 'Free Coffee'");
            history.add("Reward redeemed: 'Discount Voucher'");
            return history;
        } catch (Exception e) {
            return List.of("Error fetching reward history: " + e.getMessage());
        }
    }

    // Helper class to handle reward redemption requests
    public static class RedeemRequest {
        private String rewardId;
        private String userId;  // User ID of the person redeeming the reward

        public String getRewardId() {
            return rewardId;
        }

        public void setRewardId(String rewardId) {
            this.rewardId = rewardId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}