package com.maripartner.locationqa

import android.app.Application
import org.osmdroid.config.Configuration
import java.io.File

/**
 * Entry point Application class untuk Mari Location Lab.
 * Menginisialisasi konfigurasi OpenStreetMap (osmdroid) sesuai kebijakan tile usage OSM.
 */
class LocationQaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initOsmdroidConfiguration()
    }

    private fun initOsmdroidConfiguration() {
        // Konfigurasi User-Agent unik resmi sesuai kebijakan Tile Usage OpenStreetMap
        Configuration.getInstance().userAgentValue = "MariLocationLab/1.0.0 (com.maripartner.locationqa; Android QA Simulator)"

        // Konfigurasi cache penyimpanan tile lokal pada direktori internal aplikasi
        val basePath = File(cacheDir, "osmdroid")
        val tileCache = File(basePath, "tiles")
        Configuration.getInstance().osmdroidBasePath = basePath
        Configuration.getInstance().osmdroidTileCache = tileCache

        // Muat preferensi dengan shared preferences aplikasi
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid_prefs", MODE_PRIVATE))
    }
}
