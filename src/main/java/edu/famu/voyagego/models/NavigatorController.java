package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/navigator")
public class NavigatorController {

    @Autowired
    private Firestore firestore;

    // Create a new Navigator for a user
    @PostMapping("/create")
    public String createNavigator(@RequestBody Navigator navigator) {
        try {
            // Save the Navigator object to Firestore
            firestore.collection("navigators").document(navigator.getNavigatorId()).set(navigator);
            return "Navigator created successfully!";
        } catch (Exception e) {
            return "Error creating navigator: " + e.getMessage();
        }
    }

    // Get Navigator details by navigatorId
    @GetMapping("/details/{navigatorId}")
    public Navigator getNavigator(@PathVariable String navigatorId) {
        try {
            // Retrieve the navigator document from Firestore
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();

            if (doc.exists()) {
                return doc.toObject(Navigator.class);
            } else {
                throw new RuntimeException("Navigator not found for navigatorId: " + navigatorId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving navigator: " + e.getMessage());
        }
    }

    // Update Navigator's current location, nearby destinations, recommended routes, and attractions
    @PutMapping("/update/{navigatorId}")
    public String updateNavigator(@PathVariable String navigatorId, @RequestBody Navigator navigator) {
        try {
            // Prepare the updated Navigator data
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("currentLocation", navigator.getCurrentLocation());
            updatedData.put("nearbyDestinations", navigator.getNearbyDestinations());
            updatedData.put("recommendedRoutes", navigator.getRecommendedRoutes());
            updatedData.put("popularAttractions", navigator.getPopularAttractions());

            // Update the document in Firestore
            firestore.collection("navigators").document(navigatorId).update(updatedData);
            return "Navigator updated successfully!";
        } catch (Exception e) {
            return "Error updating navigator: " + e.getMessage();
        }
    }

    // Delete a Navigator by navigatorId
    @DeleteMapping("/delete/{navigatorId}")
    public String deleteNavigator(@PathVariable String navigatorId) {
        try {
            // Delete the navigator document from Firestore
            firestore.collection("navigators").document(navigatorId).delete();
            return "Navigator deleted successfully!";
        } catch (Exception e) {
            return "Error deleting navigator: " + e.getMessage();
        }
    }

    // Get the current location of the user (from the Navigator data)
    @GetMapping("/current-location/{navigatorId}")
    public String getCurrentLocation(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                Navigator navigator = doc.toObject(Navigator.class);
                return navigator.getCurrentLocation();
            } else {
                return "Navigator not found.";
            }
        } catch (Exception e) {
            return "Error retrieving current location: " + e.getMessage();
        }
    }

    // Get nearby destinations for the user
    @GetMapping("/nearby-destinations/{navigatorId}")
    public List<String> getNearbyDestinations(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                Navigator navigator = doc.toObject(Navigator.class);
                return navigator.getNearbyDestinations();
            } else {
                return List.of("No destinations found.");
            }
        } catch (Exception e) {
            return List.of("Error retrieving destinations: " + e.getMessage());
        }
    }

    // Get recommended routes for the user
    @GetMapping("/recommended-routes/{navigatorId}")
    public List<String> getRecommendedRoutes(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                Navigator navigator = doc.toObject(Navigator.class);
                return navigator.getRecommendedRoutes();
            } else {
                return List.of("No routes found.");
            }
        } catch (Exception e) {
            return List.of("Error retrieving recommended routes: " + e.getMessage());
        }
    }

    // Search for destinations based on a query
    @GetMapping("/search")
    public List<String> searchDestinations(@RequestParam String query) {
        // Implement search logic here (e.g., fetch destinations based on the query)
        return List.of("Search result 1", "Search result 2");
    }
}

