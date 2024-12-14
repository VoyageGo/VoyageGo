package edu.famu.voyagego.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    private String profileId;
    private boolean darkMode;
    private String language;

    // Getters and Setters
    public String getProfileId() { return profileId; }
    public void setProfileId(String profileId) { this.profileId = profileId; }
    public boolean isDarkMode() { return darkMode; }
    public void setDarkMode(boolean darkMode) { this.darkMode = darkMode; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
