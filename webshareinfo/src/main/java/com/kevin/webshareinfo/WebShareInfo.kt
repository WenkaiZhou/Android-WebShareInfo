package com.kevin.webshareinfo

import android.webkit.WebView
import org.json.JSONException
import org.json.JSONObject

/**
 * WebShareInfo
 *
 * @author zwenkai@foxmail.com, Created on 2020-12-11 14:27:30
 *         Major Function：<b>WebShareInfo</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

/**
 * Get the share info from webView
 *
 * @param callback  the callback function
 */
fun WebView.getShareInfo(callback: (shareInfo: WebShareInfo) -> Unit) {
    evaluateJavascript(
        """
        (function () {
            var params = {};
            var metas = document.getElementsByTagName('meta');
            for (var i = 0; i < metas.length; i++) {
                if (metas[i].getAttribute("property") == "og:title") {
                    params.title = metas[i].getAttribute("content");
                } else if (metas[i].getAttribute("property") == "og:url") {
                    params.url = metas[i].getAttribute("content");
                } else if (metas[i].getAttribute("property") == "og:description") {
                    params.description = metas[i].getAttribute("content");
                } else if (metas[i].getAttribute("property") == "og:image") {
                    params.image = metas[i].getAttribute("content");
                } else if (metas[i].getAttribute("property") == "og:site_name") {
                    params.site_name = metas[i].getAttribute("content");
                } else if (metas[i].getAttribute("property") == "og:type") {
                    params.type = metas[i].getAttribute("content");
                }
            }
            return params;
        })()
        """.trimIndent()
    ) { params ->
        val shareInfo = WebShareInfo()
        try {
            val ogJsonObject = JSONObject(params)
            if (!ogJsonObject.isNull("title")) {
                shareInfo.title = ogJsonObject.getString("title")
            }
            if (!ogJsonObject.isNull("url")) {
                shareInfo.url = ogJsonObject.getString("url")
            }
            if (!ogJsonObject.isNull("image")) {
                shareInfo.image = ogJsonObject.getString("image")
            }
            if (!ogJsonObject.isNull("description")) {
                shareInfo.description = ogJsonObject.getString("description")
            }
            if (!ogJsonObject.isNull("site_name")) {
                shareInfo.siteName = ogJsonObject.getString("site_name")
            }
            if (!ogJsonObject.isNull("type")) {
                shareInfo.type = ogJsonObject.getString("type")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        callback(shareInfo)
    }
}

class WebShareInfo {
    /**
     * The title of your object as it should appear within the graph, e.g., "The Rock".
     */
    var title: String? = null

    /**
     * The canonical URL of your object that will be used as its permanent ID in the graph, e.g., "https://www.imdb.com/title/tt0117500/".
     */
    var url: String? = null

    /**
     * An image URL which should represent your object within the graph.
     */
    var image: String? = null

    /**
     * A one to two sentence description of your object.
     */
    var description: String? = null

    /**
     * If your object is part of a larger web site, the name which should be displayed for the overall site. e.g., "IMDb".
     */
    var siteName: String? = null

    /**
     * The type of your object, e.g., "video.movie". Depending on the type you specify, other properties may also be required.
     */
    var type: String? = null
}