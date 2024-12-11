package edu.famu.voyagego.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

//@Data
@NoArgsConstructor // Explicit no-argument constructor
@AllArgsConstructor
public class User {
    @Getter
    @Setter
    @DocumentId
    private String userId;
    private String name;
    private String email;
    private String password;
    private String displayName;
    private String personalityType;
    private int rewardPoints;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }


    public String getPersonalityType() {
        return personalityType;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}


