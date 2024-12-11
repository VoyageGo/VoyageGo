package edu.famu.voyagego.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelJournalEntry {
    @DocumentId
    private String entryId;
    private String userId;
    private String title;
    private String content;
    private String location;
    private Timestamp date;

    public String getEntryId() {
        return entryId;
    }

}
