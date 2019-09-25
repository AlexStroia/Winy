# Winy

Allow users to find the perfect match for their food or their wine.

![winy](https://user-images.githubusercontent.com/35500199/65607328-35444880-df61-11e9-98e4-468776241637.jpg)
![unnamed](https://user-images.githubusercontent.com/35500199/65607329-35444880-df61-11e9-8494-e18f536fb3e7.jpg)
![winy2](https://user-images.githubusercontent.com/35500199/65607336-370e0c00-df61-11e9-9c9b-6af6bb1caebb.jpg)
![winy3](https://user-images.githubusercontent.com/35500199/65607338-37a6a280-df61-11e9-8d44-5bba1cc7f0d7.jpg)
![winy1](https://user-images.githubusercontent.com/35500199/65607339-37a6a280-df61-11e9-850a-fe1be0d7177b.jpg)

Rubric
General App Usage

• App should display informations about wines & foods from provided network resource. 
• User should authenticate via Firebase.
• App should store user data in the database.

•  App builds from a clean repository checkout with no additional configuration.
•   App builds and deploys using the installRelease Gradle task.
•    App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.
•    All app dependencies are managed by Gradle.
•   App implements a ContentProvider to access locally stored data.
•    Must implement at least one of the three : If it regularly pulls or sends data to/from a web service or API, app updates data in its cache at regular intervals using a SyncAdapter. OR If it needs to pull or send data to/from a web service or API only once, or on a per request basis (such as a search application), app uses an IntentService to do so. OR It it performs short duration, on-demand requests (such as search), app uses an AsyncTask.
•   App uses a Loader to move its data to its views.




Features
• MVVM
• Livedata
• RXJava
• Repository Pattern
• Usecases
• Dagger
• Data binding
• Retrofit
• Room
• Material Design Guidelines
