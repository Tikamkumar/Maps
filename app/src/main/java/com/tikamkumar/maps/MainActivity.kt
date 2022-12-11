package com.tikamkumar.maps

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.tikamkumar.maps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkForInternet(this)) {
            binding.webView.webViewClient = WebViewClient()
            binding.webView.webChromeClient = WebChromeClient()
            binding.webView.apply {
                settings.javaScriptEnabled = true
                loadUrl("https://www.google.co.in/maps/@27.1766701,78.0080745,11z")
            }
        } else {
            val alertbuilder = AlertDialog.Builder(this)
            alertbuilder.setTitle("Alert!")
            alertbuilder.setMessage("Please check your Internet Connection.")
            alertbuilder.setCancelable(false)
            alertbuilder.setPositiveButton("OK") { _, _ ->
                finish()
            }
            val alertDialog = alertbuilder.create()
            alertDialog.show()
        }

    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    }
}