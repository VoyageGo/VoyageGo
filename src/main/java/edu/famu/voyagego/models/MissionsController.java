package edu.famu.voyagego.models;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.DocumentSnapshot;


import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionsController {

    @Autowired
    private Firestore firestore;


    // Create a new mission
    @PostMapping("/create")
    public String createMission(@RequestBody Mission mission) {
        try {
            // Save the Mission object to Firestore
            firestore.collection("missions").document(mission.getMissionId()).set(mission);
            return "Mission created successfully!";
        } catch (Exception e) {
            return "Error creating mission: " + e.getMessage();
        }
    }

    // Get mission details by missionId
    @GetMapping("/details/{missionId}")
    public Mission getMissionDetails(@PathVariable String missionId) {
        try {
            // Retrieve the mission document from Firestore
            DocumentSnapshot doc = firestore.collection("missions").document(missionId).get().get();

            if (doc.exists()) {
                return doc.toObject(Mission.class);
            } else {
                throw new RuntimeException("Mission not found for missionId: " + missionId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving mission: " + e.getMessage());
        }
    }

    // Get available missions (dummy data for now)
    @GetMapping("/available")
    public List<String> getAvailableMissions() {
        return List.of("Mission 1", "Mission 2");
    }

    // Complete a mission (update status to "completed")
    @PostMapping("/complete/{missionId}")
    public String completeMission(@PathVariable String missionId) {
        try {
            // Update the mission status to "completed"
            firestore.collection("missions").document(missionId).update("status", "completed");
            return "Mission " + missionId + " completed!";
        } catch (Exception e) {
            return "Error completing mission: " + e.getMessage();
        }
    }

    // Update mission progress (e.g., from 0% to 100%)
    @PutMapping("/progress/{missionId}")
    public String updateMissionProgress(@PathVariable String missionId, @RequestBody int progress) {
        try {
            // Update the mission progress field
            firestore.collection("missions").document(missionId).update("progress", progress);
            return "Mission " + missionId + " progress updated to " + progress + "%!";
        } catch (Exception e) {
            return "Error updating mission progress: " + e.getMessage();
        }
    }

    // Delete a mission by missionId
    @DeleteMapping("/delete/{missionId}")
    public String deleteMission(@PathVariable String missionId) {
        try {
            // Delete the mission document from Firestore
            firestore.collection("missions").document(missionId).delete();
            return "Mission " + missionId + " deleted successfully!";
        } catch (Exception e) {
            return "Error deleting mission: " + e.getMessage();
        }
    }
}
