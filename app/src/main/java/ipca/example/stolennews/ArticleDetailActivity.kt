package ipca.example.stolennews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleDetailActivity : AppCompatActivity() {

    var urlString : String? = null
    var articleTitle : String? = null

    var webView : WebView? = null

    companion object {
        const val ARTICLE_URL   = "article_url"
        const val ARTICLE_TITLE = "article_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        urlString = intent.getStringExtra(ARTICLE_URL)
        articleTitle = intent.getStringExtra(ARTICLE_TITLE)

        title = articleTitle

        webView = findViewById(R.id.webViewArticle)

        urlString?.let {
            webView?.loadUrl(it)
        }

        val webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            webView?.webViewClient = webViewClient
        }

    }


}