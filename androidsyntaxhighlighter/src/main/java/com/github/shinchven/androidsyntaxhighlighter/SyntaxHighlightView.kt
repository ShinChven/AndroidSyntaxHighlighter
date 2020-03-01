package com.github.shinchven.androidsyntaxhighlighter

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.gson.Gson


class SyntaxHighlightView : WebView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    val styles: ArrayList<String> = ArrayList()
    val supportedLanguages: ArrayList<String> = ArrayList()

    var callback: Callback? = null

    @SuppressLint("SetJavaScriptEnabled")
    fun initialize(callback: Callback?) {
        this.callback = callback
        this.settings.javaScriptEnabled = true
        this.settings.javaScriptCanOpenWindowsAutomatically = true
        this.loadUrl("file:///android_asset/syntax-highlighter/index.html")
        this.addJavascriptInterface(
            JavaScriptInterface(context,
                object : JavaScriptInterfaceCallback {
                    override fun listStyles(styleNames: String?) {
                        if (styleNames != null) {
                            val gson = Gson()
                            val list = gson.fromJson(styleNames, Array<String>::class.java).toList()
                            styles.addAll(list)
                            callback?.listStyles(styles)
                        }
                    }

                    override fun listSupportedLanguages(supportedLanguagesString: String?) {
                        if (supportedLanguagesString != null) {
                            val gson = Gson()
                            val list =
                                gson.fromJson(supportedLanguagesString, Array<String>::class.java)
                                    .toList()
                            supportedLanguages.addAll(list)
                            callback?.listSupportedLanguages(supportedLanguages)
                        }
                    }

                    override fun componentDidMount() {
                        callback?.componentDidMount()
                    }
                }), "AndroidSyntaxHighlightView"
        )
    }

    @Suppress("unused")
    fun setCodeString(codeString: String, language: String) {
        // encode codeString to base64 to keep line breakers.
        val base64: String =
            Base64.encodeToString(codeString.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        loadUrl("javascript:window.setCodeString('$base64','$language')")
    }

    @Suppress("unused")
    fun setStyle(styleName: String) {
        loadUrl("javascript:window.setStyle('$styleName')")
    }

    @Suppress("unused")
    fun setShowLineNumbers(showLineNumbers: Boolean) {
        loadUrl("javascript:window.setShowLineNumbers('$showLineNumbers')")
    }

    @Suppress("unused")
    fun setStartingLineNumber(startingLineNumber: Int) {
        loadUrl("javascript:window.setStartingLineNumber($startingLineNumber)")
    }

    @Suppress("unused")
    fun setWrapLines(wrapLines: Boolean) {
        loadUrl("javascript:window.setWrapLines(${wrapLines.toString()})")
    }

    class JavaScriptInterface(val context: Context?, val callback: JavaScriptInterfaceCallback) {

        @JavascriptInterface
        fun listStyles(styleNames: String?) {
            callback.listStyles(styleNames)
        }

        @JavascriptInterface
        fun listSupportedLanguages(supportedLanguagesString: String?) {
            callback.listSupportedLanguages(supportedLanguagesString)
        }

        @JavascriptInterface
        fun componentDidMount() {
            callback.componentDidMount()
        }

    }

    interface JavaScriptInterfaceCallback {
        fun listStyles(styleNames: String?)
        fun listSupportedLanguages(supportedLanguagesString: String?)
        fun componentDidMount()
    }

    interface Callback {
        fun listStyles(styles: ArrayList<String>)
        fun listSupportedLanguages(supportedLanguages: ArrayList<String>)
        fun componentDidMount()
    }
}

