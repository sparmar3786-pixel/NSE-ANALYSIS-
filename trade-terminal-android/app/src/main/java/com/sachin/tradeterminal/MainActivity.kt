package com.sachin.tradeterminal

import android.app.Activity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import android.content.SharedPreferences
import android.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import androidx.webkit.WebViewAssetLoader

class MainActivity : Activity() {
    private lateinit var web: WebView
    private lateinit var prefs: SharedPreferences
    private lateinit var assetLoader: WebViewAssetLoader
    private val key = "backend_url"
    private val defaultBackend = "https://trade-terminal-phone.onrender.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("trade_terminal", MODE_PRIVATE)

        assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = false
        web.settings.allowContentAccess = false
        web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request.url)
            }
        }
        web.webChromeClient = WebChromeClient()
        setContentView(web)
        loadTerminal()
    }

    private fun loadTerminal() {
        val base = prefs.getString(key, defaultBackend) ?: defaultBackend
        val encoded = java.net.URLEncoder.encode(base, "UTF-8")
        web.loadUrl("https://appassets.androidplatform.net/assets/index.html?backend=$encoded")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Backend URL")
        menu.add("Reload")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Reload") { loadTerminal(); return true }
        if (item.title == "Backend URL") { showBackendDialog(); return true }
        return super.onOptionsItemSelected(item)
    }

    private fun showBackendDialog() {
        val input = EditText(this)
        input.setSingleLine(true)
        input.setText(prefs.getString(key, defaultBackend))
        input.setHint("https://your-server.example.com")
        AlertDialog.Builder(this)
            .setTitle("Trade Terminal backend")
            .setMessage("Angel One/OpenAI keys stay on the Python server. Do not enter them here.")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save & Connect") { _, _ ->
                var v = input.text.toString().trim().removeSuffix("/")
                if (v.isBlank()) v = defaultBackend
                prefs.edit().putString(key, v).apply()
                loadTerminal()
                Toast.makeText(this, "Backend saved", Toast.LENGTH_SHORT).show()
            }.show()
    }

    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }
}
