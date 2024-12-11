package edu.famu.voyagego.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @DocumentId
    private String settingsId;
    private String userId;
    private String theme; // e.g., "light", "dark"
    private String language;
    private String region;
    private boolean notificationsEnabled;
    private boolean dataSharingEnabled;
    private String[] privacySettings;

    public String getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(String settingsId) {
        this.settingsId = settingsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean isDataSharingEnabled() {
        return dataSharingEnabled;
    }

    public void setDataSharingEnabled(boolean dataSharingEnabled) {
        this.dataSharingEnabled = dataSharingEnabled;
    }

    public String[] getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(String[] privacySettings) {
        this.privacySettings = privacySettings;
    }
}
