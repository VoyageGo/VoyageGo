package edu.famu.voyagego.models;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/travel-journal")
public class TravelJournalEntryController {

    private final Firestore db;

    public TravelJournalEntryController() {
        this.db = FirestoreClient.getFirestore(); // Use Firestore singleton
    }

    // Create a new journal entry
    @PostMapping("/create")
    public String createJournalEntry(@RequestBody TravelJournalEntry journalEntry) {
        try {
            db.collection("travelJournal").document(journalEntry.getEntryId()).set(journalEntry).get();
            return "Journal entry created successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Read a specific journal entry by ID
    @GetMapping("/{entryId}")
    public TravelJournalEntry getJournalEntry(@PathVariable String entryId) throws ExecutionException, InterruptedException {
        return db.collection("travelJournal").document(entryId).get().get().toObject(TravelJournalEntry.class);
    }

    // Update an existing journal entry
    @PutMapping("/update/{entryId}")
    public String updateJournalEntry(@PathVariable String entryId, @RequestBody TravelJournalEntry updatedEntry) {
        try {
            db.collection("travelJournal").document(entryId).set(updatedEntry).get();
            return "Journal entry updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Delete a journal entry by ID
    @DeleteMapping("/delete/{entryId}")
    public String deleteJournalEntry(@PathVariable String entryId) {
        try {
            db.collection("travelJournal").document(entryId).delete().get();
            return "Journal entry deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Get all journal entries for a specific user
    @GetMapping("/user/{userId}")
    public List<TravelJournalEntry> getUserJournalEntries(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return db.collection("travelJournal")
                .whereEqualTo("userId", userId)
                .get()
                .get()
                .toObjects(TravelJournalEntry.class);
    }
}
