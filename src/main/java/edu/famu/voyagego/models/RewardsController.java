package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import edu.famu.voyagego.models.Rewards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private Firestore firestore;

    @GetMapping("/available")
    public ResponseEntity<List<Rewards>> getAvailableRewards(
            @RequestParam String location,
            @RequestParam String profileId) {
        try {
            // Fetch rewards for the given location
            List<QueryDocumentSnapshot> rewardDocs = firestore.collection("rewards").get().get().getDocuments();
            List<Rewards> locationRewards = rewardDocs.stream()
                    .map(doc -> doc.toObject(Rewards.class))
                    .filter(reward -> reward.getLocation() != null && reward.getLocation().equalsIgnoreCase(location))
                    .collect(Collectors.toList());

            // Fetch user points
            DocumentSnapshot profileDoc = firestore.collection("profiles").document(profileId).get().get();
            int userPoints = profileDoc.contains("points") ? profileDoc.getLong("points").intValue() : 0;

            // Filter rewards the user can afford
            List<Rewards> availableRewards = locationRewards.stream()
                    .filter(reward -> reward.getRequiredPoints() <= userPoints)
                    .toList();

            return ResponseEntity.ok(availableRewards);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }









    @PostMapping("/redeem")
    public ResponseEntity<String> redeemReward(@RequestBody Map<String, String> request) {
        String profileId = request.get("profileId");
        String rewardId = request.get("rewardId");

        try {
            if (profileId == null || rewardId == null) {
                return ResponseEntity.badRequest().body("Profile ID and Reward ID are required.");
            }

            // Fetch reward details
            DocumentSnapshot rewardDoc = firestore.collection("rewards").document(rewardId).get().get();
            if (!rewardDoc.exists()) {
                return ResponseEntity.status(404).body("Reward not found.");
            }

            Rewards reward = rewardDoc.toObject(Rewards.class);

            // Fetch user profile
            DocumentSnapshot profileDoc = firestore.collection("profiles").document(profileId).get().get();
            if (!profileDoc.exists()) {
                return ResponseEntity.status(404).body("Profile not found.");
            }

            int userPoints = profileDoc.contains("points") ? profileDoc.getLong("points").intValue() : 0;

            // Check if the user has enough points
            if (userPoints < reward.getRequiredPoints()) {
                return ResponseEntity.badRequest().body("Insufficient points.");
            }

            // Deduct points from the user profile
            firestore.collection("profiles").document(profileId)
                    .update("points", userPoints - reward.getRequiredPoints()).get();

            // Add redeemed reward to the `redeemedRewards` collection
            Map<String, Object> redeemedReward = new HashMap<>();
            redeemedReward.put("profileId", profileId);
            redeemedReward.put("rewardId", rewardId);
            redeemedReward.put("redeemedAt", new Date());
            firestore.collection("redeemedRewards").add(redeemedReward).get();

            return ResponseEntity.ok("Reward redeemed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error redeeming reward: " + e.getMessage());
        }
    }






    @PostMapping("/create")
    public ResponseEntity<String> createReward(@RequestBody Rewards reward) {
        try {
            if (reward.getLocation() == null || reward.getLocation().isEmpty()) {
                return ResponseEntity.status(400).body("Location is required for rewards.");
            }

            if (reward.getRewardId() == null || reward.getRewardId().isEmpty()) {
                reward.setRewardId(firestore.collection("rewards").document().getId());
            }

            firestore.collection("rewards").document(reward.getRewardId()).set(reward).get();
            return ResponseEntity.status(201).body("Reward created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating reward: " + e.getMessage());
        }
    }


    @GetMapping("/redeemed")
    public ResponseEntity<List<Rewards>> getRedeemedRewards(@RequestParam String profileId) {
        try {
            // Fetch redeemed rewards for the user
            List<QueryDocumentSnapshot> redeemedDocs = firestore.collection("redeemedRewards")
                    .whereEqualTo("profileId", profileId)
                    .get().get().getDocuments();

            List<Rewards> redeemedRewards = new ArrayList<>();
            for (QueryDocumentSnapshot doc : redeemedDocs) {
                String rewardId = doc.getString("rewardId");
                DocumentSnapshot rewardDoc = firestore.collection("rewards").document(rewardId).get().get();
                if (rewardDoc.exists()) {
                    redeemedRewards.add(rewardDoc.toObject(Rewards.class));
                }
            }

            return ResponseEntity.ok(redeemedRewards);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

}
