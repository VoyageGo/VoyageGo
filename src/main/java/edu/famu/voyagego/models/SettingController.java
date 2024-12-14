package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    @Autowired
    private Firestore firestore;

    // Get user settings
    @GetMapping("/{profileId}")
    public ResponseEntity<Map<String, Object>> getUserSettings(@PathVariable String profileId) {
        try {
            var profileDoc = firestore.collection("profiles").document(profileId).get().get();

            if (!profileDoc.exists()) {
                return ResponseEntity.status(404).body(Map.of("error", "Profile not found"));
            }

            Map<String, Object> settings = new HashMap<>();
            settings.put("darkMode", profileDoc.getBoolean("darkMode"));
            settings.put("language", profileDoc.getString("language"));

            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error fetching user settings: " + e.getMessage()));
        }
    }

    // Update user settings
    @PutMapping("/update")
    public ResponseEntity<String> updateUserSettings(@RequestBody Map<String, Object> requestData) {
        String profileId = (String) requestData.get("profileId");
        Map<String, Object> updates = (Map<String, Object>) requestData.get("updates");

        try {
            DocumentReference docRef = firestore.collection("profiles").document(profileId);
            if (!docRef.get().get().exists()) {
                return ResponseEntity.status(404).body("Profile not found.");
            }

            // Update Firestore document
            docRef.update(updates).get();
            return ResponseEntity.ok("Settings updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating settings: " + e.getMessage());
        }
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable String profileId) {
        try {
            // Check if the profile exists before attempting to delete
            var profileDoc = firestore.collection("profiles").document(profileId).get().get();
            if (!profileDoc.exists()) {
                return ResponseEntity.status(404).body("Profile not found.");
            }

            // Delete the profile
            firestore.collection("profiles").document(profileId).delete().get();
            return ResponseEntity.ok("Profile deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting profile: " + e.getMessage());
        }
    }

}
