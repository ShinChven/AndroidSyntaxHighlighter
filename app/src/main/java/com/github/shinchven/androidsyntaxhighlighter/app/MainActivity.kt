package com.github.shinchven.androidsyntaxhighlighter.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.shinchven.androidsyntaxhighlighter.HljsStyle
import com.github.shinchven.androidsyntaxhighlighter.SyntaxHighlightView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView.initialize(object : SyntaxHighlightView.Callback {
            override fun listStyles(styles: ArrayList<HljsStyle>) {
                Log.i("styles", "${styles.size}")
            }

            override fun listSupportedLanguages(supportedLanguages: ArrayList<String>) {
                Log.i("supportedLanguages", "${supportedLanguages.size}")
            }

            override fun componentDidMount() {
                // must be interacted in UI thread.
                GlobalScope.launch(Dispatchers.Main.immediate) {
                    try {
                        webView.setCodeString(CODE, "html")
                        webView.setShowLineNumbers(true)
                        webView.setWrapLines(true)
//                        webView.setStyle(webView.styles[2])
                        webView.setStartingLineNumber(1)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })

    }

    companion object {
        const val CODE: String =
            "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\"/>\n  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n  <meta\n    name=\"viewport\"\n    content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\"\n  />\n  <title>这是一个中文标题</title>\n  <link rel=\"icon\" href=\"/favicon.png\" type=\"image/x-icon\"/>\n</head>\n<body>\n<noscript>Sorry, we need js to run correctly!</noscript>\n<div id=\"root\">\n  <div\n    style=\"background-color: #fff;width: 100%;height: 100%;position: absolute;text-align: center; vertical-align: middle\">\n    <div class=\"vertical-center\">\n      <img src=\"assets/home_loading.jpg\" style=\"width: 100%;\"/>\n      <img src=\"assets/spinner.svg\" style=\"width:100px;margin-top: 50px;\">\n      <div style=\"text-align: center;\">\n      </div>\n    </div>\n  </div>\n</div><div id=\"root\">\n  <div\n    style=\"background-color: #fff;width: 100%;height: 100%;position: absolute;text-align: center; vertical-align: middle\">\n    <div class=\"vertical-center\">\n      <img src=\"assets/home_loading.jpg\" style=\"width: 100%;\"/>\n      <img src=\"assets/spinner.svg\" style=\"width:100px;margin-top: 50px;\">\n      <div style=\"text-align: center;\">\n      </div>\n    </div>\n  </div>\n</div>\n<div id=\"root\">\n  <div\n    style=\"background-color: #fff;width: 100%;height: 100%;position: absolute;text-align: center; vertical-align: middle\">\n    <div class=\"vertical-center\">\n      <img src=\"assets/home_loading.jpg\" style=\"width: 100%;\"/>\n      <img src=\"assets/spinner.svg\" style=\"width:100px;margin-top: 50px;\">\n      <div style=\"text-align: center;\">\n      </div>\n    </div>\n  </div>\n</div>\n<div id=\"root\">\n  <div\n    style=\"background-color: #fff;width: 100%;height: 100%;position: absolute;text-align: center; vertical-align: middle\">\n    <div class=\"vertical-center\">\n      <img src=\"assets/home_loading.jpg\" style=\"width: 100%;\"/>\n      <img src=\"assets/spinner.svg\" style=\"width:100px;margin-top: 50px;\">\n      <div style=\"text-align: center;\">\n      </div>\n    </div>\n  </div>\n</div>\n<div id=\"root\">\n  <div\n    style=\"background-color: #fff;width: 100%;height: 100%;position: absolute;text-align: center; vertical-align: middle\">\n    <div class=\"vertical-center\">\n      <img src=\"assets/home_loading.jpg\" style=\"width: 100%;\"/>\n      <img src=\"assets/spinner.svg\" style=\"width:100px;margin-top: 50px;\">\n      <div style=\"text-align: center;\">\n      </div>\n    </div>\n  </div>\n</div>\n\n</body>\n</html>\n"
    }
}
