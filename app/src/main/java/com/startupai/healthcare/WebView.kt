package com.startupai.healthcare

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.startupai.healthcare.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val progressBar = ProgressDialog(this)
        progressBar.setCancelable(false)
        progressBar.show()


        val webSettings: WebSettings = binding.webView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        webSettings.allowContentAccess=true
        webSettings.loadsImagesAutomatically=true
        webSettings.allowFileAccess=true
        webSettings.allowFileAccessFromFileURLs=true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                progressBar.show()
                return true
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                progressBar.dismiss()
            }
        }

        binding.webView.loadUrl(intent?.getStringExtra("url").toString())
    }
}