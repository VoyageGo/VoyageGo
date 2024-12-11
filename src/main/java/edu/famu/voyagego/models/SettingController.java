package edu.famu.voyagego.models;

import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    @Autowired
    private Firestore firestore;

    // Get account settings
    @GetMapping("/account/{userId}")
    public Setting getAccountSettings(@PathVariable String userId) throws ExecutionException, InterruptedException {
        DocumentSnapshot userSettingSnapshot = firestore.collection("settings").document(userId).get().get();

        if (!userSettingSnapshot.exists()) {
            return new Setting(); // Return default settings if not found
        }

        return userSettingSnapshot.toObject(Setting.class);
    }

    // Update privacy settings
    @PutMapping("/privacy/{userId}")
    public String updatePrivacySettings(@PathVariable String userId, @RequestBody String[] privacySettings) {
        try {
            firestore.collection("settings").document(userId).update("privacySettings", privacySettings);
            return "Privacy settings updated successfully!";
        } catch (Exception e) {
            return "Error updating privacy settings: " + e.getMessage();
        }
    }

    // Delete account
    @DeleteMapping("/delete/{userId}")
    public String deleteAccount(@PathVariable String userId) {
        try {
            firestore.collection("users").document(userId).delete();
            firestore.collection("settings").document(userId).delete(); // Delete associated settings
            return "Account deleted successfully!";
        } catch (Exception e) {
            return "Error deleting account: " + e.getMessage();
        }
    }
}