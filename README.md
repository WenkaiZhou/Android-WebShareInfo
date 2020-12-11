# Android-WebShareInfo
获取WebView中H5的分享(开放内容)信息

## 微信H5链接卡

<img src="https://raw.githubusercontent.com/xuehuayous/Android-WebShareInfo/master/app/pic/1607677503515.jpg" width="300" />

同样是两片H5文章，在样式显示上为啥区别那么大呢？明显亲生的公众号文章更好看一些？它们肯定是在H5上有差异的，带着这个疑问，打开了第一篇文章的源码。果然，这一段看着比较可疑。

```html
<meta property="og:title" content="男朋友胸大，对女生有什么好处？" />
<meta property="og:url" content="http://mp.weixin.qq.com/s?__biz=MzA3MzUzOTg1Nw==&amp;mid=2653781537&amp;idx=2&amp;sn=20c72d6caf2a200b1206a216fcc8859b&amp;chksm=84d4565fb3a3df494bb0357e05ba8c776882fbb3f0d220a27ff679347fd94ad3b55ebf459922#rd" />
<meta property="og:image" content="http://mmbiz.qpic.cn/mmbiz_jpg/iaJ2Y8kZ6cicVUDRLic6QxVnXweRJ9YqTjarF9icqBkpkUSicibXEIOGfWia1RJOWuqoIv7RP6Sava287KnAaqFAibnb3Q/0?wx_fmt=jpeg" />
<meta property="og:description" content="采访了大胸男友的女生，故事很多……" />
<meta property="og:site_name" content="微信公众平台" />
<meta property="og:type" content="article" />
<meta property="og:article:author" content="🐻" />
```

它们都带有一个`og`标识，对前端开发不熟悉的我感觉应该是个什么协议，搜索一把。Open Graph Protocol，即开放内容协议，用了`og`标签，就是你同意了网页内容可以被其他社会化网站引用，说白了，这个属性的加入能让用户的页面内容能正确的分享，这样网页内容的传播、推广就更加有力。

原来如此，真是个好东西呢。突然想起来，产品同学和我聊的，运营计划在春节期间发起好友分享拉新的活动，能不能把我们的拉新网页在app内分享到微信。那岂不是正好可以使用这种协议，获取信息呢。

## Android如何获取内容值

哪怎么获取对应的内容呢？又是一把搜索。看到有人提议使用Jsoup解析，Jsoup我比较熟悉，在用Java爬网站的时候用过，是一把解析Html的好手，但...用来干这个，未免大材小用了。

那是不是可以Native调用H5方法呢？

```kotlin
webView.evaluateJavascript(
    """
    (function () {
        var metas = document.getElementsByTagName('meta');
        for (var i = 0; i < metas.length; i++) {
            if (metas[i].getAttribute("property") == "og:title") {
                return metas[i].getAttribute("content");
            }
        }
        return "";
    })()
    """.trimIndent()
) { title ->
    Log.d("获取Title", "title = $title")
}
```

运行，见证奇迹～

```bash
2020-12-11 00:23:57.200 6337-6337/com.kevin.webshareinfo.sample D/获取Title: title = "男朋友胸大，对女生有什么好处？"
```

哈哈，正确输出了title，第一版，完成了。

在代码里看到这么一堆代码还是挺🤮的，而且我们需要的信息不只有 `title` 还有 `url` `image` `description` `site_name` `type` 这些信息，就简单封装下吧。

## 引入

```groovy
implementation 'com.kevin:webshareinfo:1.0.0'
```

## 使用

```kotlin
webView.getShareInfo { info ->
    Log.d("shareInfo", "title = ${info.title}")
    Log.d("shareInfo", "url = ${info.url}")
    Log.d("shareInfo", "image = ${info.image}")
    Log.d("shareInfo", "description = ${info.description}")
    Log.d("shareInfo", "siteName = ${info.siteName}")
    Log.d("shareInfo", "type = ${info.type}")
}
```

## 注意

1. 记得开启js

    ```
    webView.settings.javaScriptEnabled = true
    ```
2. 要在onPageFinished，页面加载之后调用

## License

```text
Copyright (c) 2020 Kevin zhou

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```