package edu.famu.voyagego.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TravelJournalEntry {
    @DocumentId
    private String entryId;
    private String profileId; // User who created the entry
    private String title; // Title of the entry
    private String content; // Content of the journal entry
    private String location; // Location associated with the entry
    private String date; // Date of the entry

    // Getters and Setters
    public String getEntryId() { return entryId; }
    public void setEntryId(String entryId) { this.entryId = entryId; }
    public String getProfileId() { return profileId; }
    public void setProfileId(String profileId) { this.profileId = profileId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
