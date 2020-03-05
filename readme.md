# AndroidSyntaxHighlighter

This is a wrapper of the [react-syntax-highlighter](https://www.npmjs.com/package/react-syntax-highlighter) for Android to display code with syntax highlight.

This is an early build for my app to view source code of websites. If you want to try it out please donload [Magic Feed](https://play.google.com/store/apps/details?id=net.atlassc.shinchven.sharemoments) from Google play store and see for the `view-source` feature which is an intent-filter for URL(ACTION_SEND, text/plain).

<img align="center" width="300" src="https://github.com/ShinChven/AndroidSyntaxHighlighter/raw/master/screenshot/Screenshot_1583156360.png">

## How to use it

### Add dependency from JitPack.io

https://jitpack.io/#ShinChven/AndroidSyntaxHighlighter

#### Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.ShinChven:AndroidSyntaxHighlighter:Tag'
}
```

### Add view in your layout

```xml
<com.github.shinchven.androidsyntaxhighlighter.SyntaxHighlightView
...
/>
```

### Use it in your Android component

```kotlin
// initialize the SyntaxHighightView
syntaxHighightView.initialize(object : SyntaxHighlightView.Callback {
    // list all syntax highlight theme when initialization completed
    override fun listStyles(styles: ArrayList<HljsStyle>) {
        Log.i("styles", "${styles.size}")
    }

    // list all supported languages when initialization completed
    override fun listSupportedLanguages(supportedLanguages: ArrayList<String>) {
        Log.i("supportedLanguages", "${supportedLanguages.size}")
    }

    //
    override fun componentDidMount() {
        // must be interacted in UI thread.
        GlobalScope.launch(Dispatchers.Main.immediate) {
            try {
                syntaxHighightView.setCodeString(CODE, "html")
                syntaxHighightView.setShowLineNumbers(true)
                syntaxHighightView.setWrapLines(true)
                syntaxHighightView.setStyle(webView.styles[2])
                syntaxHighightView.setStartingLineNumber(1)
            } catch (e: Exception) {
                e.printStackTrace()
                }
            }
        }
    })
}
```

## Bridged features from react-syntax-highlighter

- language
- style
- showLineNumbers
- wrapLines
- startingLineNumber

## How it is implemented

[SyntaxHighlighterView](https://github.com/ShinChven/AndroidSyntaxHighlighter/blob/master/androidsyntaxhighlighter/src/main/java/com/github/shinchven/androidsyntaxhighlighter/SyntaxHighlightView.kt) is based on WebView, loading a local html from assets with a prebuilt react SPA. JavaScriptInterfaces are implemented for android to call JavaScript function and get data within the SPA.

This library literally contains 2 parts, a React lib to display syntax and an Android lib for android projects to implement.
- The React library is in [syntax-highlighter](https://github.com/ShinChven/AndroidSyntaxHighlighter/tree/master/syntax-highlighter) directory.
- The Android library is in [androidsyntaxhighlighter](https://github.com/ShinChven/AndroidSyntaxHighlighter/tree/master/androidsyntaxhighlighter) directory.

## Key source code

- [Example](https://github.com/ShinChven/AndroidSyntaxHighlighter/blob/master/app/src/main/java/com/github/shinchven/androidsyntaxhighlighter/app/MainActivity.kt)
- [Android implementation](https://github.com/ShinChven/AndroidSyntaxHighlighter/blob/master/androidsyntaxhighlighter/src/main/java/com/github/shinchven/androidsyntaxhighlighter/SyntaxHighlightView.kt)
- [React implementation](https://github.com/ShinChven/AndroidSyntaxHighlighter/blob/master/syntax-highlighter/src/pages/index.js)


