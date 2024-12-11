package edu.famu.voyagego.models;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chatlogs")
public class ChatLogsController {

    @Autowired
    private Firestore firestore;

    // Create a new chat log
    @PostMapping
    public String createChatLog(@RequestBody ChatLogs newChatLog) {
        try {
            firestore.collection("chatlogs").document(newChatLog.getChatId()).set(newChatLog);
            return "Chat log created successfully!";
        } catch (Exception e) {
            return "Error creating chat log: " + e.getMessage();
        }
    }

    // Get all chat logs for a specific user
    @GetMapping("/{userId}")
    public List<ChatLogs> getUserChatLogs(@PathVariable String userId) throws ExecutionException, InterruptedException {
        List<ChatLogs> chatLogs = new ArrayList<>();
        firestore.collection("chatlogs")
                .whereEqualTo("userId", userId)
                .get()
                .get()
                .forEach(doc -> {
                    ChatLogs chatLog = doc.toObject(ChatLogs.class);
                    if (chatLog != null) {
                        chatLogs.add(chatLog);
                    }
                });
        return chatLogs;
    }

    // Get a specific chat log by chatId
    @GetMapping("/details/{chatId}")
    public ChatLogs getChatLogById(@PathVariable String chatId) throws ExecutionException, InterruptedException {
        DocumentSnapshot chatLogSnapshot = firestore.collection("chatlogs")
                .document(chatId)
                .get()
                .get();

        if (chatLogSnapshot.exists()) {
            return chatLogSnapshot.toObject(ChatLogs.class);
        } else {
            return null;
        }
    }

    // Delete a chat log by chatId
    @DeleteMapping("/delete/{chatId}")
    public String deleteChatLog(@PathVariable String chatId) {
        try {
            firestore.collection("chatlogs").document(chatId).delete();
            return "Chat log deleted successfully!";
        } catch (Exception e) {
            return "Error deleting chat log: " + e.getMessage();
        }
    }
}
