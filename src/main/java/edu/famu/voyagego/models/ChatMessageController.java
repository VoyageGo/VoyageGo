package edu.famu.voyagego.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map; // Import the Map interface
import java.util.HashMap; // Optional, for concrete implementation
import java.util.Date; // If you are dealing with timestamps
import com.google.cloud.firestore.Firestore;


@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    @Autowired
    private Firestore firestore;

    @PostMapping
    public ResponseEntity<String> getAIResponse(@RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Message cannot be empty.");
            }

            // Simulate AI response (replace with actual AI logic if available)
            String aiResponse = "AI Response to: " + userMessage;

            // Save the chat message
            Map<String, Object> chatData = new HashMap<>();
            chatData.put("userMessage", userMessage);
            chatData.put("aiResponse", aiResponse);
            chatData.put("timestamp", new Date());

            firestore.collection("chatMessages").add(chatData).get();

            return ResponseEntity.ok(aiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing AI response: " + e.getMessage());
        }
    }
}
