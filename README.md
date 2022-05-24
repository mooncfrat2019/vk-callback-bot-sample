# Kotlin bot sample for VK Bots Callback API

## Callback types
For example, if you want to resolve `message_new` event, you need to define actions in section like this `ser/callback/type/message.kt`
In similar way you can define all other events for any other callback events.

Then you need to list used events here `ser/callback/types.kt`. So you will be sure< that there is no unexpected event handling. But you can create you own strategy.

## Logic
All logic handling is defined in `ser/scenarios/Scenarios.kt`
There are some several functions to handle payload events from buttons.

For proper workflow you need to attach database. In this sample project attached Exposed (MYSQL) (`ser/db/Database.kt`).

In DB you store current state of user last decision to properly display button and resolve commands. But you can avoid it, if hardcode all logic in code, but it is some kind of antipattern.

## Env
All environment written in .env.example. Don't store actual .env file in git. If you need to store it here, so use GPG to encrypt it.

## Run
To run it there several ways:
1. Heroku. One of the best cheap ways fo test. Now there 0 cost. All you need, is to select Heroku Gradle prebuild settin, push code to repository and properly define envs. 
2. If you own some server or paid cloud account, you can use Docker (sample of working Dockerfile is in project root).
3. Run on VPS/VDS like instance with dradle/gradlew.

Tested with JDK 11
Build for VK API version 5.131. 

It is a bot for Communities only. 

Feel free for give your feedback, hate or decisions, issues and so on. 