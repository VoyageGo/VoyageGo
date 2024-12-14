package edu.famu.voyagego.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RestController
@RequestMapping("/api/navigator")
public class NavigatorController {

    @Autowired
    private Firestore firestore;

    private static final String OSRM_API_BASE = "https://router.project-osrm.org/route/v1/driving/";

    // Create a new Navigator for a profileId
    @PostMapping("/create")
    public ResponseEntity<String> createNavigator(@RequestBody Navigator navigator) {
        try {
            firestore.collection("navigators").document(navigator.getNavigatorId()).set(navigator);
            return ResponseEntity.ok("Navigator created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating navigator: " + e.getMessage());
        }
    }

    // Get Navigator details by navigatorId
    @GetMapping("/details/{navigatorId}")
    public ResponseEntity<Navigator> getNavigator(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                return ResponseEntity.ok(doc.toObject(Navigator.class));
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Update Navigator's location and other details
    @PutMapping("/update/{navigatorId}")
    public ResponseEntity<String> updateNavigator(@PathVariable String navigatorId, @RequestBody Navigator navigator) {
        try {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("currentLatitude", navigator.getCurrentLatitude());
            updatedData.put("currentLongitude", navigator.getCurrentLongitude());
            updatedData.put("nearbyDestinations", navigator.getNearbyDestinations());
            updatedData.put("recommendedRoutes", navigator.getRecommendedRoutes());
            updatedData.put("popularAttractions", navigator.getPopularAttractions());

            firestore.collection("navigators").document(navigatorId).update(updatedData);
            return ResponseEntity.ok("Navigator updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating navigator: " + e.getMessage());
        }
    }

    // Get Directions using OSRM API
    @GetMapping("/directions")
    public ResponseEntity<Map<String, Object>> getDirections(
            @RequestParam double startLat,
            @RequestParam double startLng,
            @RequestParam double destLat,
            @RequestParam double destLng) {
        try {
            String url = OSRM_API_BASE + startLng + "," + startLat + ";" + destLng + "," + destLat + "?overview=full";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> result = Map.of(
                        "status", "success",
                        "directions", response.body()
                );
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to fetch directions."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // Delete Navigator by navigatorId
    @DeleteMapping("/delete/{navigatorId}")
    public ResponseEntity<String> deleteNavigator(@PathVariable String navigatorId) {
        try {
            firestore.collection("navigators").document(navigatorId).delete();
            return ResponseEntity.ok("Navigator deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting navigator: " + e.getMessage());
        }
    }

    // Get nearby destinations for a specific user
    @GetMapping("/nearby-destinations/{navigatorId}")
    public ResponseEntity<List<Location>> getNearbyDestinations(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                Navigator navigator = doc.toObject(Navigator.class);
                return ResponseEntity.ok(navigator.getNearbyDestinations());
            } else {
                return ResponseEntity.status(404).body(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    // Get recommended routes for the user
    @GetMapping("/recommended-routes/{navigatorId}")
    public ResponseEntity<List<Route>> getRecommendedRoutes(@PathVariable String navigatorId) {
        try {
            DocumentSnapshot doc = firestore.collection("navigators").document(navigatorId).get().get();
            if (doc.exists()) {
                Navigator navigator = doc.toObject(Navigator.class);
                return ResponseEntity.ok(navigator.getRecommendedRoutes());
            } else {
                return ResponseEntity.status(404).body(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
}
