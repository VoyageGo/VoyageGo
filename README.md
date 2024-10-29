# VoyageGo

## Table Of Contents

1. [Overview](#overview)
2. [Product Spec](#product-spec)
3. [Wireframes](#wireframes)
4. [Schema](#schema)

## Overview
### Description
VoyageGo serves as a travel companion that not only provides practical travel advice but also offers companionship through simulated conversations and personalized experiences. Users can choose from a range of virtual personalities and receive customized recommendations based on their destination, interests, and real-time data. The app also includes features like cultural tips/activities, language translation, safety alerts, and offline mode for uninterrupted use, ensuring travelers are well-equipped for every aspect of their journey. Travelers can explore cities through an interactive map, complete gamified travel challenges, and access potential discounts on local services like Uber rides and hotel stays. In addition, the AI-driven travel journal and immersive storytelling features provide unique insights into the places travelers visit.

### App Evaluation
 * **Category**: Travel/ Social Networking/ AI Companionship/ Culture
 * **Story:** VoyageGo transforms the travel experience by offering an AI-powered virtual buddy that provides real-time advice, cultural insights, and engaging conversations. Users can rely on their travel buddy to guide them through both well-known destinations and off-the-beaten-path experiences.
 * **Market:** This app appeals to a wide audience, particularly solo travelers, backpackers, and budget travelers who want guidance, safety, and companionship. Cultural enthusiasts and travelers seeking deep immersion in local customs will also benefit from VoyageGo.
 * **Habit:** VoyageGo could be used as frequently or as sporadically as the user prefers. Whether for quick travel tips or ongoing companionship throughout the journey, the app adapts to each user’s needs. Users can engage daily with the app to plan and explore during their travels, interact with cultural activities, complete challenges for rewards, and receive real-time notifications for safety alerts or new travel missions.
 * **Scope:** Initially focusing on personalized travel suggestions and virtual companionship, VoyageGo could evolve to integrate augmented reality (AR) for real-time exploration, expand its language translation tools, and partner with major travel services. VoyageGo could also evolve into a full travel ecosystem, with integration into local transportation, hotels, restaurants, and activity booking systems. 

## Product Spec
### 1. User Stories (Required or Optional)
#### Required Must-Have Stores 
 * Users log in to access their personalized AI travel buddy, past conversations, and travel preferences.
 * AI buddy provides real-time travel advice based on the user’s location and preferences.
 * Users are guided by an interactive map to explore cities, including personalized travel recommendations.
 * Users receive cultural tips, language translation assistance, and safety alerts.
 * Users complete travel missions (e.g., visiting landmarks, trying local food) to unlock rewards such as discounts on Uber or nearby hotels.
 * AI travel journal automatically captures and organizes travel memories, including photos, voice notes, and locations.
 * Profile pages for each user, including travel history, achievements, and favorite experiences.
 * Customizable personality selection for the AI travel buddy (e.g., humorous, adventurous, or calm).
#### Optional Nice-To-Have Stories
 * Immersive storytelling and trivia features when visiting historical sites.
 * Multiplayer mode where users can collaborate on travel missions or challenges.
 * Real-time travel challenges with interactive maps and hidden cultural gems.
 * Integrated social connection features for meeting fellow travelers or locals.
 * Users can earn virtual badges or rewards for completing travel challenges.
 * Voice interaction with the AI travel buddy for hands-free assistance while navigating.
 * Offline mode for maps, AI interactions, and challenge tracking without internet access.

### 2. Screens

**Login Screen:** Users sign in or create an account to access their AI travel buddy and profile information.
**Travel Dashboard:** Personalized recommendations for nearby attractions, restaurants, and events based on the user’s preferences and real-time data
**Interactive Map:**  Features the interactive map with recommendations, missions, and activities.
**Messaging Screen:** A chat interface where users can interact with their AI travel buddy for real-time travel advice, cultural trivia, or casual conversations and connect with others in the same city.
**Profile Screen:** Allows users to set up their preferences, select the personality of their AI travel buddy, and manage settings.
**Missions/Challenges Screen:** Users can track their progress on gamified travel challenges and see potential rewards like Uber discounts or special local deals.
**Cultural Tips & Language Screen:** Provides users with useful tips on local customs, etiquette, and common language phrases with real-time translation.
**Settings Screen:** Users can modify their account preferences, adjust notification settings, and customize the AI personality.
**Travel Journal:** A journal that compiles the user’s photos, locations, and voice notes into a travel diary, which they can edit or share with others.

### 3. Navigation
**Tab Navigation** (Tab to Screen)
  * Explore Map
  * Mission/Challenges
  * Rewards
  * Profile
  * Settings
  * Travel Journal 

**Flow Navigation** (Screen to Screen)
  * Forced Log-in -> Account creation if no login is available
  * Explore Map -> Missions/Challenges -> Progress Checker -> Rewards Screen
  * Missions/Challenges -> Rewards Screen(Direct)
  * Explore Map -> Rewards Screen (After mission completion)
  * Profile -> View Travel Achievements/History
  * Settings -> Toggle Options -> Offline Mode

    
  * Settings -> Offline Mode -> Explore Map (without connectivity)
  * Travel Journal -> Share or Edit Journal Entries
  * Real-time Notifications -> Travel Mission/Rewards

## Wireframes 
![concepts wireframe -1](https://github.com/user-attachments/assets/a2aa45ff-eead-4a05-8c2b-102f8a3a6939)

[WireFrame Interactive Video](https://github.com/VoyageGo/VoyageGo/blob/main/Wirefram.mp4)


## Schema

### Models

#### User
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| userId                | string          | unique identifier for the user                              |
| name                  | string          | user's full name                                            |
| email                 | string          | user's email address                                        |
| personalityType       | string          | user's personality type                                     |
| rewardsPoints         | number          | total reward points earned by the user                      |
| completedChallenges   | number          | list of challenge IDs the user has completed                |
| favoriteDestinations  | number          | list of location IDs that the user has marked as favorites  |

#### Location
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| locationId            | string          | unique identifier for the location                          |
| name                  | string          | name of the location                                        |
| type                 `| string          | type of location                                            |
| description           | string          | brief description of the location                           |
| coordinates           | map             | latitude and longitude of location                          |
| relatedEvents         | array           | list of event IDs related to the location                   |
| personalityMatches    | array           | list of personality types that would enjoy this location    |

#### Event
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| eventId               | string          | unique identifier for the event                             |
| name                  | string          | name of the event                                           |
| description           | string          | description of the event                                    |
| startDate             | string          | start date of the event                                     |
| endDate               | string          | end date of the event                                       |
| locationId            | string          | the location where the event takes place                    |
| personalityMatches    | array           | list of personality types that match the event              |

#### Challenge
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| challengeId           | string          | unique identifier for the challenge                         |
| name                  | string          | name of the challenge                                       |
| description           | string          | description of what the challenge entails                   |
| rewardPoints          | number          | number of reward points awarded upon completion             |
| eventId               | string          | the ID of an assosiated event                               |

#### Reward
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| rewardId              | string          | unique identifier for the reward                            |
| name                  | string          | name of the reward                                          |
| type                  | string          | type of the reward                                          |
| pointsCost            | number          | number of reward points required to redeem  the award       |
| description           | string          | details about the award                                     |
| availability          | string          | whether the award is currently available                    |


### Networking

#### List of network requests by screen
```
- Log-in Screen
  - (Read/GET) User Login Request (Authenticate User)
    ```
   function loginUser(username, password) {
  let loginRequest = {
    username: username,
    password: password
  };

  auth.signInWithEmailAndPassword(loginRequest.username, loginRequest.password)
    .then((userCredential) => {
      console.log("User authenticated:", userCredential.user);
      // TODO: Redirect user to home screen
    })
    .catch((error) => {
      console.log("Login failed:", error.message);
      // TODO: Display error to user
    });
}




    ```
  - (Create/POST) Create a new user account (sign-up).
  - (Read/GET) Send a password reset link to the user's email.
  - (Read/GET) Retrieve the username based on the user's email.

- Home Feed Screen
  - (Read/GET) Fetch user data to display a personalized welcome message (e.g., "Welcome back, [User's Name]!")
  - (Read/GET) Implement a search feature that queries locations, events, and challenges based on user input in the search bar
  - (Read/GET) Query personalized location recommendations based on the user's personality type
  - (Read/GET) Query personalized event recommendations based on the user's interests and past activities
  - (Read/GET) Query upcoming challenges or milestones tailored to the user
  - (Read/GET) Fetch the user's recent activity, such as events they’ve recently participated in, challenges completed, and locations visited
  - (Read/GET) Display the user’s favorite locations and events for quick access
  - (Read/GET) Query events the user is marked as interested in or has pending RSVPs
  - (Read/GET) Query challenges the user has started but not yet completed
  - (Read/GET) Fetch a list of upcoming events the user has signed up for or shown interest in
  - (Read/GET) Fetch a list of upcoming events based on the user's current location (e.g., nearby festivals, concerts, etc.)
  - (Read/GET) Display the user's current reward points
  - (Read/GET) Query available rewards that the user can redeem with their points

- Rewards Screen
  - (Read/GET) Fetch the user's current reward points balance
  - (Read/GET) Query all available rewards that the user can redeem based on their current point balance.
  - (Create/POST) Redeem a reward using the user’s reward points
  - (Read/GET) Query the user's reward redemption history
  - (Read/GET) Query special or featured rewards based on the user’s behavior (e.g., most redeemed rewards, rewards related to locations the user has visited).
 
- Challenges Screen
  - (Read/GET) Challenge notifications: Fetch notifications or reminders related to challenges.
  - (Read/GET) Recommended challenges: Fetch challenges recommended for the user based on their preferences or activity.
  - (Read/GET) Available challenges: Retrieves all current challenges available to the user.
  - (Read/GET) Expiring challenges: Fetch challenges that are nearing their expiration date.
  - (Read/GET) Ongoing challenges: Fetch the user's in-progress challenges.
  - (Read/GET) Completed challenges: Fetch all challenges the user has completed.
  - (Read/GET) Challenge details: Fetch detailed information about a specific challenge.
  - (Create/POST) Mark challenge as completed: Mark the challenge as complete and reward the user.
  - (Update/PUT) Update challenge progress: Update the user’s progress for ongoing challenges.

- Navigator Screen
  - (Read/GET) Fetch user’s current location: Retrieve the user’s GPS coordinates to display on the map.
  - (Read/GET) Fetch nearby recommended destinations: Retrieve a list of nearby recommended destinations based on the user's preferences and location.
  - (Read/GET) Fetch recommended routes/directions: Provide recommended routes or directions to a selected destination.
  - (Read/GET) Fetch popular nearby attractions: Retrieve a list of popular nearby destinations or attractions.
  - (Read/GET) Search for destinations on the map: Allow users to search for specific destinations or attractions on the map.

- Profile Screen
  - (Update/PUT) Update or change the user’s profile picture.
  - (Read/GET) Fetch the user's profile details (e.g., name, email, profile picture, personality type).
  - (Update/PUT) Allow the user to update their profile information (e.g., name, email, bio, preferences)
  - (Read/GET) Fetch user account settings (e.g., notification preferences, language, privacy settings)

- Settings Screen
  - (Read/GET) Account settings: Fetch user account information (email, phone number, linked accounts).
  - (Update/PUT) Update account settings: Change the user’s contact information or linked accounts.
  - (Read/GET) Notification settings: Retrieve current notification preferences (push, email, SMS).
  - (Update/PUT) Update notification settings: Enable or disable specific types of notifications.
  - (Read/GET) Privacy settings: Fetch user’s current privacy settings (profile visibility, data sharing).
  - (Update/PUT) Update privacy settings: Adjust privacy controls (public/private profile, data sharing preferences).
  - (Read/GET) App settings: Fetch theme (light/dark) and language preferences.
  - (Update/PUT) Update app settings: Switch between themes, change app language.
  - (Update/PUT) Change password: Update the user’s password securely.
  - (Delete) Account deletion request: Deactivate or delete the user’s account.
  - (Read/GET) Fetch permissions: Retrieve permissions granted to the app (e.g., location, camera, microphone).
  - (Update/PUT) Update permissions: Enable or disable app permissions.
  - (Read/GET) Language and region preferences: Fetch language and region settings.
  - (Update/PUT) Update language and region: Adjust language or region preferences.
 
 
