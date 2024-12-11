package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import edu.famu.voyagego.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private Firestore firestore;

    // Get Profile by userId
    @GetMapping("/{userId}")
    public Profile getProfile(@PathVariable String userId) {
        try {
            // Fetch the profile from Firestore by userId
            DocumentSnapshot doc = firestore.collection("profiles").document(userId).get().get();

            if (doc.exists()) {
                return doc.toObject(Profile.class); // Return the Profile data
            } else {
                throw new RuntimeException("Profile not found for userId: " + userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving profile: " + e.getMessage());
        }
    }

    // Update Profile by userId
    @PutMapping("/{userId}")
    public String updateProfile(@PathVariable String userId, @RequestBody Profile updatedProfile) {
        try {
            // Prepare the updated Profile data
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", updatedProfile.getName());
            updatedData.put("email", updatedProfile.getEmail());
            updatedData.put("profilePicture", updatedProfile.getProfilePicture());
            updatedData.put("bio", updatedProfile.getBio());
            updatedData.put("preferences", updatedProfile.getPreferences());
            updatedData.put("personalityType", updatedProfile.getPersonalityType());

            // Update the profile in Firestore
            firestore.collection("profiles").document(userId).update(updatedData);
            return "Profile updated successfully!";
        } catch (Exception e) {
            return "Error updating profile: " + e.getMessage();
        }
    }

    // Create a new profile (for a new user)
    @PostMapping("/create")
    public String createProfile(@RequestBody Profile profile) {
        try {
            // Save the Profile object to Firestore
            firestore.collection("profiles").document(profile.getUserId()).set(profile);
            return "Profile created successfully!";
        } catch (Exception e) {
            return "Error creating profile: " + e.getMessage();
        }
    }

    // Delete Profile by userId
    @DeleteMapping("/{userId}")
    public String deleteProfile(@PathVariable String userId) {
        try {
            // Delete the profile from Firestore
            firestore.collection("profiles").document(userId).delete();
            return "Profile deleted successfully!";
        } catch (Exception e) {
            return "Error deleting profile: " + e.getMessage();
        }
    }
}
