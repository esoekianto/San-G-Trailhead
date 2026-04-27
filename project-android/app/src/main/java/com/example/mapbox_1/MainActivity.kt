package com.example.mapbox_1

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MainActivity : ComponentActivity() {

    private lateinit var mapView: MapView
    private lateinit var pointAnnotationManager: PointAnnotationManager

    private val places = listOf(
        PlaceInfo(
            "Vivian Creek Trailhead",
            "Shortest and steepest classic route toward San Gorgonio from Forest Falls.",
            Point.fromLngLat(-116.8910176, 34.081644)
        ),
        PlaceInfo(
            "Momyer Creek Trailhead",
            "A quieter trailhead with a more low-key feel and strong local character.",
            Point.fromLngLat(-116.9153586, 34.0869597)
        ),
        PlaceInfo(
            "South Fork Trailhead",
            "A popular San Gorgonio access point with a bigger paved parking area.",
            Point.fromLngLat(-116.8742779, 34.1612624)
        )
    )

    private val placeByAnnotationId = mutableMapOf<String, PlaceInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = MapView(this)
        setContentView(mapView)

        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-116.893, 34.110))
                .zoom(10.2)
                .build()
        )

        mapView.mapboxMap.loadStyle(Style.STANDARD) { style ->
            style.setStyleImportConfigProperty(
                "basemap",
                "lightPreset",
                Value.valueOf("dusk")
            )
            style.setStyleImportConfigProperty(
                "basemap",
                "theme",
                Value.valueOf("faded")
            )
            style.setStyleImportConfigProperty(
                "basemap",
                "showPointOfInterestLabels",
                Value.valueOf(false)
            )
            addAnnotations()
        }
    }

    private fun addAnnotations() {
        val drawable = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_mylocation)
                as? BitmapDrawable ?: return

        pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

        val options = places.map { place ->
            PointAnnotationOptions()
                .withPoint(place.point)
                .withIconImage(drawable.bitmap)
        }

        val annotations = pointAnnotationManager.create(options)

        annotations.zip(places).forEach { (annotation, place) ->
            placeByAnnotationId[annotation.id] = place
        }

        pointAnnotationManager.addClickListener(
            OnPointAnnotationClickListener { annotation ->
                placeByAnnotationId[annotation.id]?.let { place ->
                    Toast.makeText(
                        this,
                        "${place.name}\n${place.description}\n${place.point.latitude()}, ${place.point.longitude()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                true
            }
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        if (::pointAnnotationManager.isInitialized) {
            pointAnnotationManager.deleteAll()
        }
        mapView.onDestroy()
        super.onDestroy()
    }

    private data class PlaceInfo(
        val name: String,
        val description: String,
        val point: Point
    )
}
