package com.maripartner.locationqa.feature.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.maripartner.locationqa.domain.model.GeoPointData
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

/**
 * Komponen Composable pembungkus osmdroid MapView.
 * Menyediakan interaksi pemilihan koordinat via ketukan pada peta atau menyeret marker.
 */
@Composable
fun OsmMapView(
    selectedLocation: GeoPointData,
    onPointSelected: (latitude: Double, longitude: Double) -> Unit,
    modifier: Modifier = Modifier,
    zoomLevel: Double = 15.0
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var mapViewRef by remember { mutableStateOf<MapView?>(null) }
    var markerRef by remember { mutableStateOf<Marker?>(null) }

    // Kelola siklus hidup MapView (resume, pause, detach)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapViewRef?.onResume()
                Lifecycle.Event.ON_PAUSE -> mapViewRef?.onPause()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapViewRef?.onDetach()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)

                    val startPoint = GeoPoint(selectedLocation.latitude, selectedLocation.longitude)
                    controller.setZoom(zoomLevel)
                    controller.setCenter(startPoint)

                    // Event listener untuk menangkap ketukan pengguna pada peta
                    val eventsReceiver = object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                            if (p != null) {
                                onPointSelected(p.latitude, p.longitude)
                                return true
                            }
                            return false
                        }

                        override fun longPressHelper(p: GeoPoint?): Boolean {
                            if (p != null) {
                                onPointSelected(p.latitude, p.longitude)
                                return true
                            }
                            return false
                        }
                    }
                    overlays.add(MapEventsOverlay(eventsReceiver))

                    // Marker penunjuk lokasi terpilih
                    val marker = Marker(this).apply {
                        position = startPoint
                        title = "Titik Simulasi"
                        snippet = selectedLocation.toFormattedCoordinates()
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        isDraggable = true
                        setOnMarkerDragListener(object : Marker.OnMarkerDragListener {
                            override fun onMarkerDrag(m: Marker?) {}
                            override fun onMarkerDragStart(m: Marker?) {}
                            override fun onMarkerDragEnd(m: Marker?) {
                                m?.position?.let {
                                    onPointSelected(it.latitude, it.longitude)
                                }
                            }
                        })
                    }
                    overlays.add(marker)
                    markerRef = marker
                    mapViewRef = this
                }
            },
            update = { mapView ->
                val currentTarget = GeoPoint(selectedLocation.latitude, selectedLocation.longitude)
                markerRef?.let { marker ->
                    if (marker.position.latitude != currentTarget.latitude ||
                        marker.position.longitude != currentTarget.longitude
                    ) {
                        marker.position = currentTarget
                        marker.snippet = selectedLocation.toFormattedCoordinates()
                        mapView.controller.animateTo(currentTarget)
                        mapView.invalidate()
                    }
                }
            }
        )

        // Atribusi Wajib OpenStreetMap (ODbL) transparan di pojok kanan bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "© OpenStreetMap contributors",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
