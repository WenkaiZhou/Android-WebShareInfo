package com.kevin.webshareinfo.sample

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevin.webshareinfo.getShareInfo

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.web_view)
        webView.settings.javaScriptEnabled = true
//        webView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIyNTMzOTI5OA==&mid=2247516638&idx=1&sn=7b71b8248944f03e18c19aa5de018ef3&chksm=e803f313df747a056993accd7c5c115abcceea0407b928de2072836f94d2a9cb9be0b2cd5e15&mpshare=1&scene=23&srcid=1210bHwchdFeJkcNAqceI5Qe&sharer_sharetime=1607614669806&sharer_shareid=44bf5b26eee724684513539dd1f40451%23rd")
        webView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzA3MzUzOTg1Nw==&mid=2653781537&idx=2&sn=20c72d6caf2a200b1206a216fcc8859b&chksm=84d4565fb3a3df494bb0357e05ba8c776882fbb3f0d220a27ff679347fd94ad3b55ebf459922&mpshare=1&scene=23&srcid=1129FovTTb2sv5ii8cLauRLz&sharer_sharetime=1607664443483&sharer_shareid=44bf5b26eee724684513539dd1f40451#rd")
    }

    // 需要在onPageFinished之后调用
    fun getShareInfo(view: View) {
        webView.getShareInfo { openGraph ->
            val dialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("分享信息")
                .setMessage(
                    "标题: ${openGraph.title}\n" +
                            "摘要:  ${openGraph.description}\n" +
                            "类型:  ${openGraph.type}\n" +
                            "网站:  ${openGraph.siteName}\n" +
                            "链接:  ${openGraph.url}\n" +
                            "图片:  ${openGraph.image}"
                )
                .create()
            dialog.show()
        }
    }


}