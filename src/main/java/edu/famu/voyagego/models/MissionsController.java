package edu.famu.voyagego.models;

import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/missions")
public class MissionsController {

    @Autowired
    private Firestore firestore;

    /**
     * Create a new mission.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createMission(@RequestBody Mission mission) {
        try {
            if (mission.getMissionId() == null || mission.getMissionId().isEmpty()) {
                mission.setMissionId(firestore.collection("missions").document().getId());
            }

            mission.setCompleted(false); // New missions are incomplete by default

            firestore.collection("missions").document(mission.getMissionId()).set(mission).get();

            return ResponseEntity.status(201).body("Mission created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating mission: " + e.getMessage());
        }
    }

    /**
     * Get all available missions (missions not completed by the user).
     */
    @GetMapping("/available")
    public ResponseEntity<List<Mission>> getAvailableMissions(@RequestParam String profileId) {
        try {
            List<Mission> allMissions = firestore.collection("missions").get().get().toObjects(Mission.class);

            List<String> completedMissionIds = firestore.collection("completedMissions")
                    .whereEqualTo("profileId", profileId)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(doc -> doc.getString("missionId"))
                    .toList();

            List<Mission> availableMissions = allMissions.stream()
                    .filter(mission -> !completedMissionIds.contains(mission.getMissionId()))
                    .toList();

            return ResponseEntity.ok(availableMissions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }


    /**
     * Get the number of missions completed by a specific user.
     */
    @GetMapping("/completed/{profileId}")
    public ResponseEntity<Map<String, Object>> getCompletedMissionsCount(@PathVariable String profileId) {
        try {
            List<QueryDocumentSnapshot> completedMissionsDocs = firestore.collection("completedMissions")
                    .whereEqualTo("profileId", profileId)
                    .get()
                    .get()
                    .getDocuments();

            int totalPoints = 0;
            List<String> completedMissionIds = new ArrayList<>();

            for (DocumentSnapshot doc : completedMissionsDocs) {
                String missionId = doc.getString("missionId");
                completedMissionIds.add(missionId);

                // Fetch mission points
                DocumentSnapshot missionDoc = firestore.collection("missions").document(missionId).get().get();
                if (missionDoc.exists() && missionDoc.contains("points")) {
                    totalPoints += missionDoc.getLong("points").intValue();
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("totalCompletedMissions", completedMissionIds.size());
            response.put("totalPoints", totalPoints);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Mission>> getCompletedMissions(@RequestParam String profileId) {
        try {
            List<QueryDocumentSnapshot> completedMissionsDocs = firestore.collection("completedMissions")
                    .whereEqualTo("profileId", profileId)
                    .get().get().getDocuments();

            List<Mission> completedMissions = new ArrayList<>();
            for (DocumentSnapshot doc : completedMissionsDocs) {
                String missionId = doc.getString("missionId");
                DocumentSnapshot missionDoc = firestore.collection("missions").document(missionId).get().get();
                if (missionDoc.exists()) {
                    completedMissions.add(missionDoc.toObject(Mission.class));
                }
            }

            return ResponseEntity.ok(completedMissions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeMission(@RequestBody Map<String, String> request) {
        String profileId = request.get("profileId");
        String missionId = request.get("missionId");

        try {
            // Validate input
            if (profileId == null || missionId == null) {
                return ResponseEntity.badRequest().body("Profile ID and Mission ID are required.");
            }

            // Update mission status in 'missions' collection
            DocumentReference missionDoc = firestore.collection("missions").document(missionId);
            missionDoc.update("completed", true).get();

            // Retrieve mission points
            DocumentSnapshot missionSnapshot = missionDoc.get().get();
            int points = missionSnapshot.getLong("points").intValue();

            // Update user points in 'profiles' collection
            DocumentReference profileDoc = firestore.collection("profiles").document(profileId);
            profileDoc.update("points", FieldValue.increment(points)).get();

            // Add to 'completedMissions' collection
            Map<String, Object> completedMission = new HashMap<>();
            completedMission.put("profileId", profileId);
            completedMission.put("missionId", missionId);
            completedMission.put("completedAt", new Date());
            firestore.collection("completedMissions").add(completedMission).get();

            return ResponseEntity.ok("Mission completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error completing mission: " + e.getMessage());
        }
    }






}
