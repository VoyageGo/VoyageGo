package edu.famu.voyagego.models;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/journal")
public class TravelJournalEntryController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/create")
    public ResponseEntity<String> createEntry(@RequestBody TravelJournalEntry entry) {
        try {
            if (entry.getEntryId() == null || entry.getEntryId().isEmpty()) {
                entry.setEntryId(firestore.collection("journalEntries").document().getId());
            }
            firestore.collection("journalEntries").document(entry.getEntryId()).set(entry).get();
            return ResponseEntity.status(201).body("Journal entry created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating journal entry: " + e.getMessage());
        }
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<TravelJournalEntry>> getEntriesByProfile(@PathVariable String profileId) {
        try {
            List<TravelJournalEntry> entries = firestore.collection("journalEntries")
                    .whereEqualTo("profileId", profileId).get().get()
                    .toObjects(TravelJournalEntry.class);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<String> deleteEntry(@PathVariable String entryId) {
        try {
            firestore.collection("journalEntries").document(entryId).delete().get();
            return ResponseEntity.ok("Journal entry deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting journal entry: " + e.getMessage());
        }
    }
}
