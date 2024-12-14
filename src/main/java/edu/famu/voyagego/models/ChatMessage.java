package edu.famu.voyagego.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String profileId;
    private String message;
    private String response;

    // Getters and Setters
    public String getProfileId() { return profileId; }
    public void setProfileId(String profileId) { this.profileId = profileId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}
