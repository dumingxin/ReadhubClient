package name.dmx.readhubclient.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.library.AgentWeb
import kotlinx.android.synthetic.main.webview_activity.*
import name.dmx.readhubclient.R
import android.support.v4.content.ContextCompat.startActivity
import android.app.Activity


class WebViewActivity : AppCompatActivity() {

    lateinit var mAgentWeb: AgentWeb
    var url: String = ""
    var title: String = ""
    var siteName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.webview_activity)
        url = intent.getStringExtra(KEY_URL)
        title = intent.getStringExtra(KEY_TITLE)
        siteName = intent.getStringExtra(KEY_SITE_NAME)
        setupToolbar()
        setupWebView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.navigation_item_share -> {
                shareToFriend(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun shareToFriend(activity: Activity) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, url)
        sendIntent.type = "text/plain"
        activity.startActivity(Intent.createChooser(sendIntent, title))
    }

    private fun setupToolbar() {
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            this.finish()
        }
    }

    private fun setupWebView() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(container, LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
//                .setReceivedTitleCallback(mCallback)
//                .setWebChromeClient(mWebChromeClient)
//                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
//                .setWebLayout(WebLayout(this))
                .createAgentWeb()//
                .ready()
                .go(url)
    }


    private inner class ChromeClient : WebChromeClient() {

        // NOTE: WebView can be trying to show an AlertDialog after the activity is finished, which
        // will result in a WindowManager$BadTokenException.
        override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
            return this@WebViewActivity.isFinishing || super.onJsAlert(view, url, message,
                    result)
        }

        override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {
            return this@WebViewActivity.isFinishing || super.onJsConfirm(view, url, message,
                    result)
        }

        override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String,
                                result: JsPromptResult): Boolean {
            return this@WebViewActivity.isFinishing || super.onJsPrompt(view, url, message,
                    defaultValue, result)
        }

        override fun onJsBeforeUnload(view: WebView, url: String, message: String, result: JsResult): Boolean {
            return this@WebViewActivity.isFinishing || super.onJsBeforeUnload(view, url, message,
                    result)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        //mAgentWeb.destroy();
        mAgentWeb.webLifeCycle.onDestroy()
    }

    companion object {
        private val KEY_URL = "KEY_URL"
        private val KEY_TITLE = "KEY_TITLE"
        private val KEY_SITE_NAME = "KEY_SITE_NAME"

        fun makeIntent(context: Context, url: String, title: String, siteName: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_TITLE, title)
            intent.putExtra(KEY_SITE_NAME, siteName)
            return intent
        }
    }
}
