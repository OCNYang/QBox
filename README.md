# QBox  

<div  align="center">
<img src="http://obbu6r1mi.bkt.clouddn.com/qbox/qbox.png" width = "400" alt="小秋魔盒" align=center />
</div>

[![GitHub issues](https://img.shields.io/github/issues/OCNYang/QBox.svg)](https://github.com/OCNYang/QBox/issues)&ensp;&ensp;[![GitHub forks](https://img.shields.io/github/forks/OCNYang/QBox.svg)](https://github.com/OCNYang/QBox/network)&ensp;&ensp;[![GitHub stars](https://img.shields.io/github/stars/OCNYang/QBox.svg)](https://github.com/OCNYang/QBox/stargazers)  

小秋魔盒（QBox）是一个生活工具 Android App，由 OCN.Yang 开发，第一个版本上线时间：2017.04.11  

A life tool App: simple and beautiful, pure, love.(Welcome to **Star** and **Fork**)  

[![Release version](https://img.shields.io/badge/Release%20Version-v1.6%20%E6%AD%A3%E5%BC%8F%E7%89%88-blue.svg)](https://github.com/OCNYang/QBox/releases/tag/1.6)  
[![Current version](https://img.shields.io/badge/Current%20Version-v2.0%20测试版-brightgreen.svg)](https://github.com/OCNYang/QBox)  

#### 下载地址 | APK Download：

**地址一：**[http://fir.im/qbox](http://fir.im/qbox)  
**地址二：**[https://raw.githubusercontent.com/OCNYang/QBox/master/apk/QBox.apk](https://raw.githubusercontent.com/OCNYang/QBox/master/apk/QBox.apk)  

## 应用预览 | UI

> 图片依次是（多动图，加载较慢，图片不显示刷新页面）：  
> 1.新闻模块(**如果你下载的 apk 没有显示新闻模块，开启方式：设置页长按头部直至出现提醒 > 设置页长按底部直至出现提醒 > 退出重启应用**)  
> 2.文章模块 3.日历模块  
> 4.天气模块 5.颜色画板 6.盒子布局    
> 7.魔力数字排序列表 8.下拉果冻弹跳效果 9.引导页  
> 10.手势图片 11.能力图表格 12.视差图片  

![新闻资讯](http://obbu6r1mi.bkt.clouddn.com/qbox/list.png?imageView2/2/w/270/q/95)
![转场动画](http://obbu6r1mi.bkt.clouddn.com/qbox/skipanim.gif?imageView2/2/w/270/q/95)
![日历模块](http://obbu6r1mi.bkt.clouddn.com/qbox/cla.gif?imageView2/2/w/270/q/95)  
![天气模块](http://obbu6r1mi.bkt.clouddn.com/qbox/wealther.gif?imageView2/2/w/270/q/95)
![颜色画板](http://obbu6r1mi.bkt.clouddn.com/qbox/colorpicker.png?imageView2/2/w/270/q/95)
![盒子布局](http://obbu6r1mi.bkt.clouddn.com/qbox/flex.png?imageView2/2/w/270/q/95)  
![拖曳列表](http://obbu6r1mi.bkt.clouddn.com/qbox/recy_headerandfooter.gif?imageView2/2/w/270/q/95)
![下拉果冻弹跳效果](http://obbu6r1mi.bkt.clouddn.com/qbox/shuaxin.gif?imageView2/2/w/270/q/95)
![引导页](http://obbu6r1mi.bkt.clouddn.com/qbox/sliding.gif?imageView2/2/w/270/q/95)  
![手势效果图片](http://obbu6r1mi.bkt.clouddn.com/qbox/PinImageview.gif?imageView2/2/w/270/q/95)
![能力表格](http://obbu6r1mi.bkt.clouddn.com/qbox/mpandroidchart.gif?imageView2/2/w/270/q/95)
![视差图片](http://obbu6r1mi.bkt.clouddn.com/qbox/ken.gif?imageView2/2/w/270/q/95)  
![拖曳](http://obbu6r1mi.bkt.clouddn.com/qbox/recy_yidong.gif?imageView2/2/w/405/q/95)
![搜索框](http://obbu6r1mi.bkt.clouddn.com/qbox/searchLayout.gif?imageView2/2/w/405/q/95)

## Wiki (集成说明)

**[Document | 说明文档](https://github.com/OCNYang/QBox/wiki)** (也可直接查看下面)  

## 本应用集成的各开源库或控件的说明

### Logger 日志打印  

Github地址：[https://github.com/orhanobut/logger](https://github.com/orhanobut/logger)  

#### 粗略用法：

引入：

	compile 'com.orhanobut:logger:1.15'

初始化：  

	Logger.init();//在application中

用法方式：  

	Logger.d("hello");  
	Logger.e("hello");  
	Logger.w("hello");  
	Logger.v("hello");  
	Logger.wtf("hello");  
	Logger.json(JSON_CONTENT);  
	Logger.xml(XML_CONTENT);  
	Logger.log(DEBUG, "tag", "message", throwable);

参数设置：  

	Logger
	.init(YOUR_TAG)                 // default PRETTYLOGGER or use just init()  
	.methodCount(3)                 // default 2  
	.hideThreadInfo()               // default shown  
	.logLevel(LogLevel.NONE)        // default LogLevel.FULL  
	.methodOffset(2)                // default 0  
	.logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter  

---

### GsonFormat & Gson json 数据解析  

GsonFormat Github地址：[https://github.com/zzz40500/GsonFormat](https://github.com/zzz40500/GsonFormat)  
Gson Github地址：[https://github.com/google/gson](https://github.com/google/gson)

引入：  

	compile 'com.google.code.gson:gson:2.7'

**Gson 用法教程：**  

地址：[http://ocnyang.com/tags/Gson/](http://ocnyang.com/tags/Gson/)

---

### butterknife 依赖注入框架  

Github地址：[https://github.com/JakeWharton/butterknife](https://github.com/JakeWharton/butterknife)  

引入：  

	compile 'com.jakewharton:butterknife:8.4.0'
	annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'

---

### leakcanary 内存泄漏检测工具  

Github地址：[https://github.com/square/leakcanary](https://github.com/square/leakcanary)  

引入：  

	debugCompile 'com.squareup.leakcanary:leakcanary-android:1.5'  
	releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'  
	testCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'  

---
 
### Glide 图片请求库  

Github地址：[https://github.com/bumptech/glide](https://github.com/bumptech/glide)  

引入：  

	compile 'com.github.bumptech.glide:glide:3.7.0'
	compile 'com.android.support:support-v4:19.1.0' //v4包

**Glide 用法教程：**  

地址：[http://ocnyang.com/tags/Glide/](http://ocnyang.com/tags/Glide/)

---  

### circleimageview 圆形图片库  

Github地址：[https://github.com/hdodenhof/CircleImageView](https://github.com/hdodenhof/CircleImageView)  

引入：  

	compile 'de.hdodenhof:circleimageview:2.1.0'

--- 

### Rxjava + Retrofit + okhttp 网络请求框架  

三个库的地址请自行查找，这里提供一个介绍用法的 Demo 地址  
Github地址：[https://github.com/rengwuxian/RxJavaSamples](https://github.com/rengwuxian/RxJavaSamples)  

(基本的使用，不做三者的封装（因为目前还找不到好的封装框架）)

引入：  

	compile 'com.squareup.okhttp3:okhttp:3.4.1'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
    compile 'io.reactivex:rxjava:1.2.1'
    compile 'io.reactivex:rxandroid:1.2.1'
    compile 'com.jakewharton.rxbinding:rxbinding:0.4.0'

**Okhttp 教程：**  
地址：[http://ocnyang.com/tags/OkHttp/](http://ocnyang.com/tags/OkHttp/)  
**Retrofit2 教程：**  
地址：[http://ocnyang.com/tags/Retrofit2/](http://ocnyang.com/tags/Retrofit2/)  

--- 
###  SlidingTutorial-Android 引导页库

Github地址：[https://github.com/Cleveroad/slidingtutorial-android](https://github.com/Cleveroad/slidingtutorial-android)  

引入：  

	 compile 'com.cleveroad:slidingtutorial:1.0.5'

--- 
###  nineoldandrolid 开源动画库

Github地址：[https://github.com/JakeWharton/NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)  

引入：  

	compile 'com.nineoldandroids:library:2.4.0'

--- 
###  percent 百分比布局  

说明：非本项目使用，貌似引入的某个开源库依赖此库

Github地址：[https://github.com/JulienGenoud/android-percent-support-lib-sample](https://github.com/JulienGenoud/android-percent-support-lib-sample)  

引入：  

	compile 'com.android.support:percent:25.3.0'

--- 
###  ReactiveNetwork 手机网络连接状态动态监控

Github地址：[https://github.com/pwittchen/ReactiveNetwork](https://github.com/pwittchen/ReactiveNetwork)  

引入：  

	compile 'com.github.pwittchen:reactivenetwork-rx2:0.9.0'

设想：  
网络的检测逻辑的代码放在了BaseActivity中，  
网络变化时，需要自动显示网络错误的布局主要继承这个Base类就行了，  
同时这个类开放的有网络变化时的回调接口。  

自动显示网络错误的布局的几种设想：  
第一种：当网络无连接时可以显示一个大的网络错误布局， 
或者跳转到一个网络错误布局，当网络连接上的时候再自动跳转回来  
（用户也可以通过点击返回，在无网络的情况下的页面）。  
第二种：当网络无连接、网络请求数据错误时都显示一个小警示布局提醒网络错误。  
第三种：上面两种结合在一起，同时在网络请求数据错误的时候，就加载一定数量的网络错误数据集合。

--- 
###  picasso 图片请求库

Github地址：[https://github.com/square/picasso](https://github.com/square/picasso)  

引入：  

	compile 'com.squareup.picasso:picasso:2.3.2'

--- 
###  AndroidImageSlider 轮播图片控件

貌似只引入了库，并没有真正使用

Github地址：[https://github.com/daimajia/AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider)  

引入：  

	compile "com.android.support:support-v4:+"
    compile 'com.squareup.picasso:picasso:2.3.2'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'com.daimajia.slider:library:1.1.5@aar'

才发现，好几个库都是它在依赖呀，由于没有用到，下个版本可能会删除这个引入

--- 

### BaseRecyclerViewAdapterHelper RecycleView 拓展库

Github 地址：[https://github.com/CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)  

引入：  

	compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:VERSION_CODE'

中文 Wiki：  
地址：[https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5)  

---
### greenDAO 数据库

Github 地址：[https://github.com/greenrobot/greenDAO](https://github.com/greenrobot/greenDAO)  

引入：  

	较为繁琐，详细查看 Github 地址

---
### eventbus 事件发布/订阅框架

Github 地址：[https://github.com/greenrobot/EventBus](https://github.com/greenrobot/EventBus)  

引入：  

	compile 'org.greenrobot:eventbus:3.0.0'

**用法简述：：**  

* 1.Define events:

		public static class MessageEvent { /* Additional fields if needed */ }  

* 2.Prepare subscribers: Declare and annotate your subscribing method, optionally specify a thread mode:

		@Subscribe(threadMode = ThreadMode.MAIN)  
		public void onMessageEvent(MessageEvent event) {/* Do something */};

Register and unregister your subscriber. For example on Android, activities and fragments should usually register according to their life cycle:

	 @Override
	 public void onStart() {
	     super.onStart();
	     EventBus.getDefault().register(this);
	 }
	
	 @Override
	 public void onStop() {
	     super.onStop();
	     EventBus.getDefault().unregister(this);
	 }

* 3.Post events:

		EventBus.getDefault().post(new MessageEvent());

---
### RippleEffect 控件点击的涟漪效果

Github 地址：[https://github.com/traex/RippleEffect](https://github.com/traex/RippleEffect)  

引入：  

	compile 'com.github.traex.rippleeffect:library:1.3'

---
### takephoto 拍照上传库

Github 地址：[https://github.com/crazycodeboy/TakePhoto](https://github.com/crazycodeboy/TakePhoto)  

引入：  

	 compile 'com.jph.takephoto:takephoto_library:4.0.3'

---
### colorpicker 颜色画板选择库

Github 地址：[https://github.com/QuadFlask/colorpicker](https://github.com/QuadFlask/colorpicker)  

引入：  

	compile 'com.github.QuadFlask:colorpicker:0.0.13'

---

### android-autofittextview 自适应大小文本控件

Github 地址：[https://github.com/grantland/android-autofittextview](https://github.com/grantland/android-autofittextview)  

引入：  

	compile 'me.grantland:autofittextview:0.2.+'

---

### HTextView 动态文本控件

Github 地址：[https://github.com/hanks-zyh/HTextView](https://github.com/hanks-zyh/HTextView)  

引入：  

	 compile 'hanks.xyz:htextview-library:0.1.5'

---

### BGAQRCode-Android 二维码扫描项目

对比了一些通过Zxing封装的二维码识别的项目，独独比较喜欢这个。

Github 地址：[https://github.com/bingoogolapple/BGAQRCode-Android](https://github.com/bingoogolapple/BGAQRCode-Android)  

引入：  

	compile 'com.google.zxing:core:3.2.1'
    compile 'cn.bingoogolapple:bga-qrcodecore:latestVersion@aar'
    compile 'cn.bingoogolapple:bga-zxing:latestVersion@aar'

---

### MPAndroidChart 图表库

Github 地址：[https://github.com/PhilJay/MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)  

引入：  

	//在 Android 开发中使用过图表的，应该都会知道这个库吧。
	compile 'com.github.PhilJay:MPAndroidChart:v3.0.2'

---

### material-calendarview 材料设计日历控件

Github 地址：[https://github.com/prolificinteractive/material-calendarview](https://github.com/prolificinteractive/material-calendarview)  

引入：  

	compile 'com.prolificinteractive:material-calendarview:1.4.3'

---

### flexbox-layout 官方的盒子布局控件

Github 地址：[https://github.com/google/flexbox-layout](https://github.com/google/flexbox-layout)  

引入：  

	compile 'com.google.android:flexbox:0.3.0-alpha3'

---
### KenBurnsView 视差图片控件

Github 地址：[https://github.com/flavioarfaria/KenBurnsView](https://github.com/flavioarfaria/KenBurnsView)  

引入：  

	compile 'com.flaviofaria:kenburnsview:1.0.7'

---
### materialsearchview 材料设计搜索框

Github 地址：[https://github.com/MiguelCatalan/MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)  

引入：  

	compile 'com.miguelcatalan:materialsearchview:1.4.0'

---
### 材料设计需要的官方包

引入：  

	compile 'com.android.support:cardview-v7:25.1.1'//卡片

---
### MagicIndicator 指示器库

Github 地址：[https://github.com/hackware1993/MagicIndicator](https://github.com/hackware1993/MagicIndicator)  

引入：  

	compile 'com.github.hackware1993:MagicIndicator:1.5.0'

记得鸿洋大神也开源一个差不多的库，也挺不错的。

---
### 第三方平台引入集中说明  

1. 小米推送（本来没想用小米的，抵不住前几年小米手机买的好名气大，我就用用试试。这里说一下和其他平台的不足点，目前发现小米推送不支持富文本的）  
2. ShareSDK Mob 平台的社会化分享和登录  
3. SMSSDK Mob 平台的手机短信验证（感觉 Mob 平台名气稳健，一方面是名字取得好呀，一方面永久免费）  
4. 友盟统计 （这个小应用大部分都会用吧）

---

### 沉浸式状态栏

项目地址：[http://blog.csdn.net/lmj623565791/article/details/48649563](http://blog.csdn.net/lmj623565791/article/details/48649563)  

注意三点：  

	ToolBar高度设置为wrap_content  
	ToolBar添加属性android:fitsSystemWindows="true"  
	header_just_username.xml的根布局RelativeLayout，添加属性  android:fitsSystemWindows="true"  
	android:fitsSystemWindows这个属性，主要是通过调整当前设置这个属性的view的padding去为我们的status_bar留下空间。  

为了Android4.4适配：  
那么只需要在Activity里面去写上（设置Toolbar之后）：StatusBarCompat.compat(this);就可以了。  

如果你希望自己设置状态看颜色，那么就用这个方法：  

	StatusBarCompat.compat(this, getResources().getColor(R.color.status_bar_color));

关于沉浸式的说明，6.0及以上不能达到半透明的效果。
同时沉浸式和夜间模式结合要考虑两方面。

具体差别请看V21/style

---

### Android应用自动更新库(android-auto-update)
 
项目地址：[https://github.com/feicien/android-auto-update](https://github.com/feicien/android-auto-update)  

用法：  

* 导入library项目（本项目是直接把library直接复制放在了 /update 目录下）  

* 提供2种版本检查方式,在你的项目中添加以下代码即可  

		使用Dialog  `UpdateChecker.checkForDialog(this);`  
		使用Notification  UpdateChecker.checkForNotification(this);  

* 添加权限  

		添加访问网络的权限<uses-permission android:name="android.permission.INTERNET" />  
		添加写SDCard权限（可选，非必须）  
		如果添加这个权限 apk下载在sdcard中的Android/data/包名/cache目录下 否则下载到 内存中的 /data/data/包名/cache中  
		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  

---

### SlidingTutorial-Android引导页

根据Demo直接使用，可定制的东西很少，当然引导页本身也没什么可定制的，  
就看自己喜欢不喜欢这个引导页的设计了  
项目地址：[https://github.com/Cleveroad/slidingtutorial-android](https://github.com/Cleveroad/slidingtutorial-android)

这个引导页在布局上用到的全是百分比布局  
需要自己定制的可能是最后一页添加一个按钮的点击事件  

---

### Slidinglayout 果冻弹跳效果

依赖nineoldandorid开源动画库  
Slidinglayout其实就是一个自定义的布局  

XML样式参数：  
`background_view` 背景 `view`  
`sliding_mode` 滑动模式，`both`为上下可弹跳，`top` 为顶部弹跳，`bottom` 为底部弹跳，默认为 both
`sliding_pointer_mode` 手指模式，one为只识别一个手指，more为支持多指滑动，默认为more
`top_max` 当滑动模式为top时才有效，用于可滑动的最大距离，如`"top_max:200dp"`，默认为-1（不限制）

常用API：  
`public void setSlidingOffset(float slidingOffset) `设置控件的滑动阻力，有效值为0.1F~1.0F，值越小阻力越大，默认为0.5F  
`public void setTargetView(View view) `设置控件的前景View  
`public void setBackgroundView(View view)` 设置控件的背景View  
`public void setSlidingListener(SlidingListener slidingListener)` 给控件设置监听，可以监听滑动情况  
`public void setSlidingMode(int mode)` 设置滑动模式  
`public void setSlidingDistance(int max)` 设置最大滑动距离，仅在top模式下有效  

---

### 夜间主题（仿知乎/改变主题的方式）

（本应用中还未使用）  

博客参考地址：[http://www.jianshu.com/p/3b55e84742e5](http://www.jianshu.com/p/3b55e84742e5)  
项目地址：[https://github.com/D-clock/AndroidStudyCode](https://github.com/D-clock/AndroidStudyCode)  

DayNightActivity的例子的方式是在设置页的的代码方式。  
在其他有夜间模式的页面，只要在setcontentView之前判断目前设置的模式然后设置  此种模式就行了。即实现initData();initTheme();方法就行了，同时可以在BaseActivity中实现。

---

### 主导航页面（设计思路）

(目前应用的问题，个人感觉就是这个设计的有问题)

下面具有导航栏的主体app的设计思路。  
由于这种导航是一级导航，目前不建议在app的一级页面就采用 `viewpager+Fragment` 或导航栏+ `Fragment` 的形式设计。  
建议采用独立 `activity+include` 布局 + `android:launchMode="singleTask"` + `theme:<item name="android:windowAnimationStyle">@style/noAnimation</item>` (取消activity跳转自带的原生动画)的形式  

这样的好处：能将每个一级功能分类页相互分离开来不相互影响。经过观察和猜想知乎采用的应该是这种方式。这样的方式也能达到和上面一样的效果，同时又解决了Fragment带来的不便。  

关于主导航页返回键的监听处理方式：  
一种是在每个主导航页中设置监听，点击一次没反应，连点击两次退出程序。  
另外一种：点击一次返回到main主导航页面，在main页面点击一次直接退出程序（这也是知乎实现的方式，同时因为在main页面点击一次就直接退出了，所以在其他主导航页面如果连续点击两次也能达到直接退出程序的效果。）

## 开源说明  

本应用是上线应用，在上线之初并未开源，应许多用户和开发者朋友的呼吁后在 GitHub 上开源，因本应用本身就是我个人开发也并无商业性质，为了大家学习和参考应用内各功能的具体实现方式就毫无保留的开源给大家。  
因最近为修复部分 bug 在应用市场提交更新版本时，收到了应用市场平台“此APP已被打入盗版库，不予重复更新上传”的通知拒绝更新（想象可能是有人修改此源码重复提交应用上线了）。同时也有部分朋友询问此项目采用的开源协议。基于上面这些情况在这着重强调一下开源说明：  

1. 本应用的开源源码大家都可以随意复制传播、Fork，大家对应用内各功能的实现源码可以学习参考；
2. 《小秋魔盒》是上线应用，自第一个版本上线之时，就关于本应用的所有权、知识产权、侵权申明的相关信息在应用的 “设置” > “声明” 页面有明确详细说明；
3. 本应用目前不采用任何开源协议，我个人保留本应用的所有权利； 
4. 本应用不允许任何人对源码再整体封装、或修改部分代码或变异改编再次提交上线或使用等，严禁使用到商业目的用途、毕业论文、其他形式的使用等。

## 应用详细说明   

[![CSDN](https://img.shields.io/badge/CSDN-http://blog.csdn.net/shedoor-blue.svg?style=social)](http://blog.csdn.net/shedoor)  
[![掘金](https://img.shields.io/badge/%E6%8E%98%E9%87%91-https%3A%2F%2Fjuejin.im%2Fuser%2F593a09f9fe88c2006a0034ea-red.svg?style=social)](https://juejin.im/user/593a09f9fe88c2006a0034ea)  

<br>


[![ocnyang.com](https://img.shields.io/badge/%E6%88%91%E7%9A%84%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99-www.ocnyang.com-lightgrey.svg?style=flat-square)](http://ocnyang.com/)&ensp;&ensp;[![新浪微博](https://img.shields.io/badge/%E6%96%B0%E6%B5%AA%E5%BE%AE%E5%8D%9A-欧神杨-red.svg?style=flat-square)](http://weibo.com/ocnyang/)
