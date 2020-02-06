package com.example.task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import com.example.task.R
import kotlinx.android.synthetic.main.fragment_web.*
import android.webkit.WebView
import android.webkit.WebViewClient

import android.util.Log


class WebFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        webView.loadUrl("https://www.eurail.com/en/get-inspired");
        webView.addJavascriptInterface(JavaScriptInterface(), JAVASCRIPT_OBJ)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
               //get number of divs
            }
        }

    }
    companion object {
        private val JAVASCRIPT_OBJ = "javascript_obj"
    }
    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun textFromWeb(fromWeb: String) {
           Log.i("text from web",fromWeb)
        }
    }

}
