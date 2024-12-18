# VoyageGo

## Table Of Contents

1. [Overview](#overview)
2. [Product Spec](#product-spec)
3. [Wireframes](#wireframes)
4. [Schema](#schema)
5. [Presentation](#presentation)
6. [Backend Link](#backend)
7. [Frontend Link](#frontend)

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
### 1. User Stories (Required and Optional)

#### Required Must-Have Stories

- [x] Users can log in with their username and password to access their account.
- [x] Users can create a new account to start using the app.
- [x] Users can view personalized location recommendations based on their travel preferences.
- [x] Users can see upcoming challenges or milestones tailored to their preferences.
- [x] Users can view their current reward points balance.
- [x] Users can browse all rewards they can redeem with their points.
- [x] Users can redeem rewards using their points.
- [x] Users can see their current location on a map for navigation.
- [x] Users can delete their profile.
- [x] Users can see all currently available challenges.

#### Optional Nice-to-Have Stories
- [ ] Users can reset their password if forgotten.
- [ ] Users can quickly access favorite locations and events.
- [ ] Users can view recent activity, such as events they’ve participated in and challenges they’ve completed.
- [ ] Users can view recommended challenges based on their preferences or activity.
- [ ] Users can mark challenges as completed and earn rewards.
- [ ] Users can search for specific destinations or attractions on the map.
- [ ] Users can securely update their password.
- [ ] Users can change app language and region preferences.


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

#### Chat
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| profilleId            | string          | unique identifier for the user                              |
| message               | string          | message for the chat                                        |
| response              | string          | AI response to entered message                              |

#### Location
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| latitude              | number          | latitude of location                                        |
| longitude             | number          | longitude of location                                       |
| description           | string          | brief description of the location                           |

#### Mission
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| missionId             | string          | unique identifier for the mission                           |
| profileId             | string          | User assigned to the mission                                |
| points                | number          | Points awarded for completing the mission                   |
| description           | string          | Description of the mission.                                 |
| completed             | boolean         | Indicates if the mission is completed.                      |


#### Navigator
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| navigatorId           | string          | unique identifier for the navigator                         |
| profileId             | string          | Identifier of the user using the navigator                  |
| currentLatitude       | number          | Latitude of the user's current location.                    |
| currentLongitude      | number          | Longitudeof the user's current location.                    |
| nearbyDestinations    | List<Location>	 | List of nearby destinations.                                |
| recommendedRoutes     | List<Route>	    | List of recommended routes for navigation.                  |
| popularAttractions    | List<String>		  | 	List of popular attractions near the user.                 |



#### Profile
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| ProfileId             | string          | unique identifier for the user profile                      |
| name                  | string          | name of the user                                            |
| email                 | string          | email address of user                                       |
| password              | String          | Password for the user's account.                            |
| bio                   | string          | User's bio or description.                                  |
| preference            | List<String>	   | List of the user's preferences.                             |


#### Recommendations
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| recommendationId      | string          | unique identifier for the recommendation                    |
| type                  | string          | Type of recommendation (e.g., "Adventure").                 |
| description           | string          | Description of the recommendation                           |
| location              | String          | Location description (e.g., "City, Country").               |
| latitude              | number          | latitude of location                                        |
| longitude             | number          | longitude of location                                       |


#### Rewards
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| rewardId              | string          | unique identifier for the mission                           |
| requiredPoints        | number          | Points required for redeem the award                        |
| description           | string          | Description of the reward.                                  |
| location              | String          | Location-specific availability of the reward.               |

### Route
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| origin                | Location        | Starting point of the route.                                |
| destination           | Location        | Ending point of the route.                                  |
| routeDetails          | string          | Details about the route (e.g., directions).                 |


### Setting
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| profileId             | string          | Identifier of the user using the navigator                  |
| darkMode              | Boolean         | Indicates if dark mode is enabled.                          |
| language              | string          | Language preference of the user.                            |


#### TravelJournalEntry
|        Property       |       Type      |                           Description                       |  
|-----------------------|-----------------|-------------------------------------------------------------|
| entryId               | string          | unique identifier for the journal entry                     |
| profileId             | string          | Identifier of the user who created the entry                |
| title	                | string          | Title of the journal entry                                  |
| location              | String          | Location associated with the journal entry.                 |
| content               | String          | Content of the journal entry.                               |
| date                  | String          | Date the entry was created.                                 |


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
 
- Missions Screen
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

```

## VoyageGo Presentation
[CIS Poster Template.pptx](https://github.com/user-attachments/files/18135176/CIS.Poster.Template.pptx)


## Backend
[Navigate to the code](https://github.com/VoyageGo/VoyageGo/tree/master/src)

## Frontend
 
[Navigate to the code](https://github.com/VoyageGo/VoyageGo/tree/frontend/src)
