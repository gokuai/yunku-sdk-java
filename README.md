# 够快云库 Java SDK

**注意: Tag 4.0 以后的版本对接口返回类型和分块上传做了重构, 不兼容以前的版本**

## 兼容

* Java 7 或者更高
* 支持 Android 系统

## 引用 

以下配置中的`{version}`使用最新的JitPack版本:

[![](https://jitpack.io/v/gokuai/yunku-sdk-java.svg)](https://jitpack.io/#gokuai/yunku-sdk-java)

* Gradle方式

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
	dependencies {
	        implementation 'com.github.gokuai.yunku-sdk-java:YunkuJavaSDK:{version}'
	}
```

* Maven方式

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```xml
	<dependency>
	    <groupId>com.github.gokuai.yunku-sdk-java</groupId>
	    <artifactId>YunkuJavaSDK</artifactId>
	    <version>{version}</version>
	</dependency>
```

## 初始化API配置

要使用云库的API，您需要先在 [企业授权][5]中获取 `client_id` 和 `client_secret`


**初始化配置**

```
import com.yunkuent.sdk.ConfigHelper;

new ConfigHelper()
        .apiHost("API地址")
        .language("Zh-CN")
        .config();
```

* apiHost: 公有云/混合云用户使用 `http://yk3.gokuai.com/m-open`, 私有部署用户使用 `http://{站点域名}/m-open`, 可以在浏览器中打开 `http://{站点域名}/m-open/status` 检测API服务器是否正常
* language: 多语言环境, `Zh-CN`表示中文, `En-US`表示英文

## 参数约定

以下使用到的方法中，如果是string类型的非必要参数，如果是不传，则传`null`

---

## 库文件操作

**在 <a href="https://developer.gokuai.com/grant/library.html" target="_blank">库授权</a> 获取 `client_id` 和 `client_secret`**

### 初始化文件处理类

```
import com.yunkuent.sdk.EntFileManager;

String clientId = "";
String secret = "";
EntFileManager manager = new EntFileManager(clientId, secret);
```

### 上传文件

```
try {
	//文件存放在文件库中的位置, 文件夹会自动创建
	String fullpath = "doc/new.docx";
	//本地文件路径
	String localFile = "D:\\new.docx";
	//操作用户的显示名, 用于记录日志
	String opName = "user";
	FileInfo file = manager.uploadByBlock(fullpath, opName, 0, localFile, true);
	
	//文件流的方式上传
	//FileInfo file = manager..uploadByBlock(fullpath, opName, 0, readStream, true);
	
} catch (YunkuException e) {
	System.out.println("upload fail");
	e.printStackTrace();
	
	ReturnResult result = e.getReturnResult();
	if (result != null) {
		if (result.getException() != null) {
		//出现网络或IO错误
		result.getException().printStackTrace();
		} else {
			//如果API接口返回异常, 获取最后一次API请求的结果
			System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());
		}
	}
}
```

### 获取文件列表

```
//需要列表的文件夹路径, 根目录传空字符串, 下级文件夹如: "doc/202004"
String path = "";
ReturnResult result = getFileList(path, 0, 100);
System.out.println(result.getBody());
//返回的body需要做json解析
```

### 批量删除文件

```
String[] fullpaths = new String[]{"doc/new.docx"};
String opName = "user";
ReturnResult result = manager.del(fullpaths, true, opName);
System.out.println(result.isOK());
//返回的body需要做json解析
```

### 获取下载地址

```
String fullpath = "doc/new.docx";
String opName = "user";
ReturnResult result = manager.getDownloadUrlByFullpath(fullpath, opName);
System.out.println(result.getBody());
//返回的body需要做json解析
```

### 获取预览地址

```
String fullpath = "doc/new.docx";
String opName = "user";
//是否显示水印
boolean showWatermark = false;
//水印上显示的用户名
String WatermarkMemberName = "user";
ReturnResult result = manager.getPreviewUrlByFullpath(fullpath, showWatermark, WatermarkMemberName, opName);
System.out.println(result.getBody());
//返回的body需要做json解析
```


---

## 修改默认配置

```
import com.yunkuent.sdk.ConfigHelper;

new ConfigHelper()
	.apiHost(apiHost)
	.config();
```

方法 | 说明
--- | ---
.apiHost(String apiHost) |  设置API地址, 通常是: `http://站点域名/m-open`
.webHost(String webHost) | 设置站点地址, 通常是: `http://站点域名`
.userAgent(String userAgent) | 设置 User-Agent
.trustSsl(boolean trust) | 如果是HTTPS站点, 且调接口时抛出SSL证书相关异常时, 需要设置为`true`
.language(String language) | 返回数据或提示的语言, 默认中文`zh-CN`
.connectTimeout(long timeoutSeconds) | HTTP请求连接超时时间, 单位秒, 默认10秒
.connectTimeout(long timeoutSeconds) | HTTP请求连接超时时间, 单位秒, 默认10秒
.timeout(long timeoutSeconds) | HTTPS请求超时时间, 单位秒, 默认30分钟
.blockSize(int blockSize) | 分块上传默认块大小, 单位字节, 默认10MB, 最大允许10MB
.retry(int retry) | HTTP请求出现网络异常时的重试次数, 默认不重试

---

## 常见问题

### 引用问题

#### 现象
* 在引用最新版本的项目时，出现本地代码未能更新

#### 解决办法
* 将下载到本地的项目缓存文件删除

<img src=https://repo.gokuai.cn/app/ImageResourceForMD/raw/master/YunkuJavaSDK/delete.jpg alt="Delete" title="Delete" width="50%" height="50%"/>

* 执行clean操作、重新拉取

<img src=https://repo.gokuai.cn/app/ImageResourceForMD/raw/master/YunkuJavaSDK/clean.jpg alt="Clean" title="Clean" width="50%" height="50%"/>

### 打印日志

> 详细可见 Module YunkuJavaSDKDemo 中 PrintWithLogWithYourEngine.class

```
DebugConfig.PRINT_LOG = true;

LogPrint.setLogDetector(new DebugConfig.LogDetector() {
    @Override
    public void getLog(String logtag, String level, String message) {

        //在这里添加你需要的 log

    }
});

LogPrint.info("LogTag", "Your Log");
```


