package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/signup")
    public String createUser(@RequestBody User user) {
        try {
            firestore.collection("users").document(user.getUserId()).set(user); // Corrected usage of getUserId()
            return "User created successfully";
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        try {

            DocumentSnapshot doc = firestore.collection("users").document(userId).get().get();
            return doc.toObject(User.class);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user");
        }
    }

    // PUT method to update an existing user
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @RequestBody User user) {
        try {
            // Update user data in Firestore
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("name", user.getName());
            userMap.put("email", user.getEmail());
            userMap.put("password", user.getPassword());
            userMap.put("displayName", user.getDisplayName());
            userMap.put("personalityType", user.getPersonalityType());
            userMap.put("rewardPoints", user.getRewardPoints());

            // Update the user document in Firestore
            firestore.collection("users").document(userId).update(userMap);
            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user: " + e.getMessage();
        }
    }

    // DELETE method to delete a user by userId
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        try {
            // Delete the user document from Firestore
            firestore.collection("users").document(userId).delete();
            return "User deleted successfully";
        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();
        }
    }


}
