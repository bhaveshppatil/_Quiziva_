package com.example.quizapp.Views

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import kotlinx.android.synthetic.main.activity_select_option.*

class SelectOption : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_option)

        var intent = Intent()
        intent = getIntent()
        val topicName: String = intent.getStringExtra("TopicName").toString()

        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("TopicName", topicName)
            startActivity(intent)
        }
        webView = findViewById(R.id.webView)

        btnInterview.setOnClickListener {

            if (topicName == "Android") {
                webView.visibility = View.VISIBLE
                var url: String =
                    "https://github.com/bhaveshppatil/Android_Interview_questions/blob/main/Android_Questions.md"
                startWebView(url)
            } else if (topicName == "Kotlin") {
                webView.visibility = View.VISIBLE
                var url: String =
                    "https://github.com/bhaveshppatil/Android_Interview_questions/blob/main/Kotlin_Questions.md"
                startWebView(url)
            } else {
                Toast.makeText(this, "Questions will update soon...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startWebView(url: String) {

        val settings = webView.settings
        settings.javaScriptEnabled = true
        WebView.setWebContentsDebuggingEnabled(false)

        webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@SelectOption, "Error:$description", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        webView.loadUrl(url)
    }
}