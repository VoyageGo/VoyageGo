# VoyageGo

## Table Of Contents

1. [Overview](#overview)
2. [Product Spec](#product-spec)
3. [Wireframes](#wireframes)

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
(Read/GET) Query all suggested locations for the user based on personality type
-Home Feed Screen
  
```
let query = db.collection("Locations")
query.where("personalityMatches", "array-contains", currentUser.personalityType)
query.orderBy("popularity", "desc")
query.get().then((locations) => {
  console.log("Successfully retrieved locations:", locations.docs.length);
  // TODO: Display locations in feed
}).catch((error) => {
  console.log("Error getting locations:", error);
});
```