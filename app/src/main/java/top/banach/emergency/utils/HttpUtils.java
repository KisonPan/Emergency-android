package top.banach.emergency.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.content.Context;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.request.PostRequest;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;

public class HttpUtils {

	public static void httpPost(Context context, String url, String content, StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e(url + "  " + content);
		
		OkHttpUtils.post(url)//
        .connTimeOut(C.TIME_OUT)
        .writeTimeOut(C.TIME_OUT)
        .readTimeOut(C.TIME_OUT)
	    .tag(context)//
	    .postJson(content)//
//	    .addCookie("JSESSIONID", UserMgr.getCookies(context))
	    .execute(new StringCallBack(httpCallBack));
	}

	public static void httpPost(Context context, String url, String content, StringCallBack.HttpCallBack httpCallBack, Object request) {
		LogUtils.e(url + "  " + content);
		
		OkHttpUtils.post(url)//
        .connTimeOut(C.TIME_OUT)
        .writeTimeOut(C.TIME_OUT)
        .readTimeOut(C.TIME_OUT)
	    .tag(context)//
	    .postJson(content)//
//	    .addCookie("JSESSIONID", UserMgr.getCookies(context))
	    .execute(new StringCallBack(httpCallBack,request));
	}
	public static void httpPost(Context context, String url, String content, StringCallBack stringCallBack, Object request) {
		LogUtils.e(url + "  " + content);
		
		OkHttpUtils.post(url)//
		.connTimeOut(C.TIME_OUT)
		.writeTimeOut(C.TIME_OUT)
		.readTimeOut(C.TIME_OUT)
		.tag(context)//
		.postJson(content)//
//	    .addCookie("JSESSIONID", UserMgr.getCookies(context))
		.execute(stringCallBack);
	}
	
	public static void httpPost(Context context, String url, HttpHeaders headers,String content, StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e(url + "  " + content +"   "+headers.toJSONString());
		
		OkHttpUtils.post(url)//
        .connTimeOut(C.TIME_OUT)
        .writeTimeOut(C.TIME_OUT)
        .readTimeOut(C.TIME_OUT)
	    .tag(context).headers(headers)
	    .postJson(content)
//	    .addCookie("JSESSIONID", UserMgr.getCookies(context))
	    .execute(new StringCallBack(httpCallBack));
	}

	public static void downloadFile(Context context,String url,FileCallback fileCallBack) {
		OkHttpUtils.get(url)//
				.tag(context)//
				.execute(fileCallBack);
	}
	
	
	public static void httpGet(Context context, String url, StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e("get-" + url);
		
		OkHttpUtils.get(url)//
        .connTimeOut(C.TIME_OUT)
        .writeTimeOut(C.TIME_OUT)
        .readTimeOut(C.TIME_OUT)
	    .execute(new StringCallBack(httpCallBack));
	}
	
	public static void httpPost(Context context, String url, Map<String,String> paramsMap, StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e("post-" + url);
		
		OkHttpUtils.get(url)//
        .connTimeOut(C.TIME_OUT)
        .writeTimeOut(C.TIME_OUT)
        .readTimeOut(C.TIME_OUT)
        .params(paramsMap)
	    .execute(new StringCallBack(httpCallBack));
	}
	
	public static void uploadFile(Context context, String url , Map<String,String> paramsMap, String filePath,  StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e("uploadFile-" + url);
		OkHttpUtils.post(url)
		.tag(context)
		.params(paramsMap)
		.params(FileUtil.getFileName(filePath), new File(filePath))
		.connTimeOut(60*1000)
		.readTimeOut(60*1000)
		.writeTimeOut(60*1000)
//		.params("enctype", "multipart/form-data")
		.execute(new StringCallBack(httpCallBack));
	}
	
	public static void uploadMutiFiles(Context context, String url, List<String> filePathList,  StringCallBack.HttpCallBack httpCallBack) {
		LogUtils.e("uploadFile-" + url);
		PostRequest postRequest = OkHttpUtils.post(url)
									.tag(context)
									.connTimeOut(60*1000)
									.readTimeOut(60*1000)
									.writeTimeOut(60*1000);
		int size = filePathList.size();
		for (int i = 0; i < size; i++) {
			String filePath = filePathList.get(i);
			postRequest = postRequest.params(FileUtil.getFileName(filePath), new File(filePath));
		}
		
		postRequest.execute(new StringCallBack(httpCallBack));
	}
}
