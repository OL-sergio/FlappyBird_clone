# FlappyBird Clone

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![LibGDX](https://img.shields.io/badge/libGDX-E74A2B?style=for-the-badge&logo=libGDX&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

## Project Description

This project is a clone of the famous Flappy Bird game, developed for the Android platform using the LibGDX framework. The main objective is to replicate the original gameplay, where the player controls a bird and must avoid obstacles (pipes) to achieve the highest possible score.

### Objectives

*   Recreate the Flappy Bird game mechanics.
*   Use the LibGDX framework for cross-platform game development.
*   Implement basic physics (gravity, collisions).
*   Manage different game states (menu, game, game over).

## Technologies Used

*   **Java**: Main programming language.
*   **LibGDX**: Framework for 2D/3D game development.
*   **Gradle**: Build automation system.
*   **Android SDK**: Development kit for Android.

## Project Structure

```

lappyBird_clone/
├── android/
│   ├── AndroidManifest.xml       // Android application manifest file
│   ├── build.gradle              // Gradle build file for the Android module
│   ├── ic_launcher-playstore.png // Icon for the Google Play Store
│   ├── ic_launcher-web.png       // Icon for web deployment (if applicable)
│   ├── proguard-rules.pro        // ProGuard rules for code shrinking and obfuscation
│   ├── project.properties        // Project-specific properties
│   ├── res/                      // Resources directory
│   │   ├── mipmap-anydpi-v26/    // Adaptive launcher icons (adaptive icons)
│   │   │   ├── ic_launcher_round.xml   // Round adaptive icon
│   │   │   └── ic_launcher.xml        // Standard adaptive icon
│   │   ├── mipmap-hdpi/          // Launcher icons for high-density screens
│   │   │   ├── ic_launcher_foreground.webp // Foreground layer for adaptive icon (hdpi)
│   │   │   ├── ic_launcher_round.webp    // Round launcher icon (hdpi)
│   │   │   └── ic_launcher.webp         // Standard launcher icon (hdpi)
│   │   ├── mipmap-mdpi/          // Launcher icons for medium-density screens
│   │   │   ├── ic_launcher_foreground.webp // Foreground layer for adaptive icon (mdpi)
│   │   │   ├── ic_launcher_round.webp    // Round launcher icon (mdpi)
│   │   │   └── ic_launcher.webp         // Standard launcher icon (mdpi)
│   │   ├── mipmap-xhdpi/         // Launcher icons for extra-high-density screens
│   │   │   ├── ic_launcher_foreground.webp // Foreground layer for adaptive icon (xhdpi)
│   │   │   ├── ic_launcher_round.webp    // Round launcher icon (xhdpi)
│   │   │   └── ic_launcher.webp         // Standard launcher icon (xhdpi)
│   │   ├── mipmap-xxhdpi/        // Launcher icons for extra-extra-high-density screens
│   │   │   ├── ic_launcher_foreground.webp // Foreground layer for adaptive icon (xxhdpi)
│   │   │   ├── ic_launcher_round.webp    // Round launcher icon (xxhdpi)
│   │   │   └── ic_launcher.webp         // Standard launcher icon (xxhdpi)
│   │   └── mipmap-xxxhdpi/       // Launcher icons for extra-extra-extra-high-density screens
│   │   │   ├── ic_launcher_foreground.webp // Foreground layer for adaptive icon (xxxhdpi)
│   │   │   ├── ic_launcher_round.webp    // Round launcher icon (xxxhdpi)
│   │   │   └── ic_launcher.webp         // Standard launcher icon (xxxhdpi)
│   │   └── values/              // Values directory
│   │       ├── color.xml        // Color resources
│   │       ├── ic_launcher_background.xml // Background for adaptive icons
│   │       ├── strings.xml      // String resources
│   │       └── styles.xml       // Style resources
│   └── src/                     // Source code directory
│       └── main/
│           └── java/
│               └── com/example/flappybird_clone/android/
│                   └── AndroidLauncher.java // Android launcher class
├── core/
│   └── src/main/java/com/example/flappybird_clone/
│       ├── Constants.java       // Constant values used in the game
│       └── FlappyBirdActivity.java// Main game logic
└── assets/
    └── ...                      // Game resources (images, sounds, etc.)


```

## Functionalities by Class

*   `AndroidLauncher.java`: Game initialization class on Android.
*   `FlappyBird.java` (core): Main class that manages the game lifecycle and screens.
*   `GameScreen.java` (core): Main game logic, including updating and rendering elements.
*   `Bird.java` (core): Controls the bird's behavior (movement, collision).
*   `Pipe.java` (core): Manages the creation and movement of pipes.
*   `MenuScreen.java` (core): Initial game screen.

## How to Run

### On GitHub

1.  Clone the repository:

    ```bash
    git clone <REPOSITORY_URL>
    cd FlappyBird_clone
    ```
2.  Make your changes and submit:

    ```bash
    git add .
    git commit -m "Your changes"
    git push origin main
    ```

### On Android Studio

1.  Open Android Studio.
2.  Select "Open an Existing Project".
3.  Navigate to the `FlappyBird_clone` folder and select it.
4.  Wait for Gradle to sync.
5.  Run the project on a connected Android emulator or device.

To compile via terminal:

```bash
./gradlew android:assembleDebug
```
### Libraries and Frameworks
- LibGDX: Game development framework that provides tools for rendering, audio, input, and other essential features. It abstracts the differences between platforms, allowing the same code to be run on multiple platforms with little or no modification.
- Android SDK: Required to compile and run the game on Android devices. It provides the APIs and tools to interact with the Android operating system.
