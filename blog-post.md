# Building My First Mapbox Android App 

<img width="1024" height="764" alt="image" src="https://github.com/user-attachments/assets/62ad3553-fc04-4f5b-b423-90d66a860df8" />



I live in [Yucaipa, California](https://yucaipa.gov/), so a lot of my idea of “local” is shaped by the mountains and trailheads around the [San Gorgonio Wilderness](https://www.fs.usda.gov/r05/sanbernardino/wilderness/san-gorgonio-wilderness). Instead of building the standard first-map demo with downtown landmarks or coffee shops, I decided to use a few hiking-related points that actually mean something to me. My app was intentionally tiny: load a basemap, add three point annotations, and show some data when the user taps one. That’s it.

It turned out to be a great first Mapbox project, because the app stayed simple while still teaching me the important concepts: SDK setup, basemap styling, annotations, and user interaction.

## Installing and configuring the Mapbox SDK

For this project, I intentionally kept setup simple: I started with a basic working Mapbox Android app, then replaced the generated `MainActivity.kt` with my own version for the challenge.

If you’re new to Mapbox on Android, I’d recommend starting exactly that way. Follow either Mapbox’s written Android setup guide, [Get started with the Maps SDK for Android](https://docs.mapbox.com/android/maps/guides/install/), or a starter setup video, [Mapbox Android setup video](https://www.youtube.com/watch?v=mUflY-oa5Dk), make sure you can launch a basic map successfully, and only then start customizing the app. That approach removes a lot of noise because you separate “can I get the SDK working?” from “can I build the app idea I want?”

That ended up being a very practical workflow for me. Once the starter app was running, the rest of the work became much more straightforward: center the camera, load the style, add annotations, and wire up tap interaction.

## Why I used Mapbox Standard and changed the basemap

For the basemap, I used Mapbox Standard. That felt like the right choice for a first project because it gives you a polished default look without forcing you into a giant style-design exercise on day one.

One thing I appreciated right away is that “default” doesn’t mean “locked.” Mapbox Standard exposes configuration options you can tweak at runtime, so you can keep the strong default cartography while still giving the map a tone that fits your app.

I changed the `lightPreset` to `dusk`.

That was a small customization, but it changed the feel of the map in a way I really liked. Since my sample app was centered around outdoor trailheads near San Gorgonio, the dusk styling felt more atmospheric than the normal daytime look. It matched the kind of experience I had in mind without becoming hard to read.

The code for that looked like this:

```kotlin
mapView.mapboxMap.loadStyle(Style.STANDARD) { style ->
    style.setStyleImportConfigProperty(
        "basemap",
        "lightPreset",
        Value.valueOf("dusk")
    )
    addAnnotations()
}
```

What I liked about this approach is that it gave me just enough control. I didn’t need to become a style expert immediately. I just needed to understand that the basemap is not only a backdrop; it’s part of the product experience.

## How annotations work conceptually

Once the basemap was in place, the next step was adding annotations.

If you haven’t worked with map SDKs before, annotations are one of the easiest concepts to understand. They’re just objects placed on top of the map at specific geographic coordinates. In practical terms, they’re your pins, markers, or labeled points of interest.

In my app, I kept a small list of places, each with a name and a `Point`. I used those to create `PointAnnotationOptions`, then let the annotation manager render them.

Here’s the small version of that code:

```kotlin
val options = places.map { place ->
    PointAnnotationOptions()
        .withPoint(place.point)
        .withIconImage(drawable.bitmap)
}

val annotations = pointAnnotationManager.create(options)
```

That’s the part where the app started to feel real to me. Before annotations, the app was “a map.” After annotations, it became “my map.” Annotations are also a great place to learn Mapbox because they map cleanly to how most developers already think: your app has data, some of that data has coordinates, and an annotation is the visual representation of one of those data objects on the map.

## How tap interaction works and what the data flow looks like

For the tap interaction, I chose the simplest possible UI: a `Toast`. If I were polishing this into a real hiking app, I’d probably switch to a bottom sheet or callout card. But for a first project, `Toast` was perfect because it proved the interaction model without adding much UI complexity.

The main thing I wanted to understand was the data flow.

Mine looked like this:

1. Start with a list of places in Kotlin
2. Convert those places into map annotations
3. Keep a lookup from annotation ID to the original place data
4. Register a click listener on the annotation manager
5. When a marker is tapped, look up the matching place and show its information

The click handler was simple:

```kotlin
pointAnnotationManager.addClickListener(
    OnPointAnnotationClickListener { annotation ->
        placeByAnnotationId[annotation.id]?.let { place ->
            Toast.makeText(
                this,
                "${place.name}\n${place.point.latitude()}, ${place.point.longitude()}",
                Toast.LENGTH_SHORT
            ).show()
        }
        true
    }
)
```

This is the part that made the whole SDK feel approachable. The tap event isn’t mysterious. Mapbox tells you which annotation was tapped, and your app maps that back to your own data model. If you’re coming from traditional Android UI development, that mental model feels very natural.

## What I’d do next

This app was intentionally small, but it gave me a solid feel for how the Mapbox Android SDK works. If I kept going, the first improvement would be replacing the `Toast` with a bottom sheet so each trailhead could show richer detail. After that, I’d probably add custom marker icons, maybe trail notes, and eventually something like user location or offline support.

If you’re a mobile developer who has never touched Mapbox before, my advice is simple: build a map around places you actually care about. Don’t worry about making it ambitious right away. Pick three locations, style the basemap a little, wire up a tap interaction, and get the full loop working end to end.

That was enough to turn Mapbox from “something I’ve been meaning to try” into “okay, I can build with this.” And honestly, that’s exactly what I want from a first project.
