# Mapbox San Gorgonio Trailheads Demo

A small Android "Hello, World!" mapping app built with the Mapbox Maps SDK for Android.

The app is intentionally simple and focuses on three required features:

- Load the Mapbox Standard basemap and customize it with the `dusk` light preset
- Add three point annotations for trailheads near the San Gorgonio Wilderness
- Show trailhead information in a `Toast` when a user taps a marker

## Screenshot

<img width="854" height="480" alt="recording" src="https://github.com/user-attachments/assets/472c2a33-3cc9-41d7-9fc5-29f79c37dcbd" />

## Project structure

- Android app source: your Android Studio project
- Blog post draft for submission: [blog-post.md](https://github.com/esoekianto/San-G-Trailhead/blob/main/blog-post.md)
- Copy-ready activity source: [MainActivity.kt](https://github.com/esoekianto/San-G-Trailhead/blob/main/MainActivity.kt)

## Setup

The simplest way to run this project is:

1. Start with a basic working Mapbox Android app by following either of these options:
   - Option A: Follow the written guide from Mapbox:
     [Get started with the Maps SDK for Android](https://docs.mapbox.com/android/maps/guides/install/)
   - Option B: Follow this setup video:
     [Mapbox Android setup video](https://www.youtube.com/watch?v=mUflY-oa5Dk)
2. Replace the contents of `MainActivity.kt` with the version in [MainActivity.kt](./MainActivity.kt).
3. Run the app in Android Studio.

Notes:

- This submission assumes you already have a working Mapbox Android starter app.
- The only app code you need to swap in for this challenge is `MainActivity.kt`.

## Running the app

1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the app on an emulator or Android device.
4. Tap any marker to see its name, short description, and coordinates.

## App behavior

### Basemap configuration

The app uses `Style.STANDARD` and changes the Mapbox Standard `lightPreset` to `dusk`.

Why `dusk`:

- It gives the map a slightly more atmospheric outdoor feel
- It fits the trailhead / mountain context better than a flat default daytime look
- It shows that Mapbox Standard can be customized without building a full custom style

### Annotations

The app adds three point annotations using the Annotations API:

- Vivian Creek Trailhead
- South Fork Trailhead
- Momyer Creek Trailhead

### Tap interaction

Each created annotation is mapped back to a local Kotlin data object. When the annotation is tapped, the app looks up the corresponding place data and displays it in a `Toast`.

## Reflection

The part that was trickier than expected was handling SDK-version differences in the Mapbox annotations API. A few APIs around annotation IDs and builder helpers varied enough that I had to simplify the implementation and verify what the installed version actually supported. With more time, I would replace the `Toast` with a bottom sheet or callout view and add custom marker art that better matches the hiking theme. I kept the UI intentionally minimal so the core Mapbox concepts stayed easy to understand and the setup burden for a reviewer stayed low.
