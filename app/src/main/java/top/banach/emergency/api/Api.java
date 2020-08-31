package top.banach.emergency.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.utils.LogUtils;
import top.banach.emergency.utils.SPUtils;
import top.banach.emergency.utils.UUIDGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Api {

    private static String cid;
    private static final String TAG = "Banach";

//    //get方法
//    public static void okhttp_get(Context context, String httpUrl, Callback callback) {
//        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
//        if (cid==null || cid.isEmpty()) {
//            cid = UUIDGenerator.generateUUID();
//            SPUtils.putString(context.getApplicationContext(),"cid", cid);
//        }
//
////        Long timestamp = new Date().getTime();
//        Long timestamp = System.currentTimeMillis();
//        Log.i(TAG, "[get] httpUrl= " + httpUrl);
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(httpUrl)
//                .addHeader("timestamp", timestamp.toString())
//                .addHeader("cid", cid)
//                .build();
//
//        client.newCall(request).enqueue(callback);
//
//    }

    //get方法
    public static void httpGet(Context context, String httpUrl, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[get] httpUrl= " + httpUrl);

        OkHttpUtils.get(httpUrl)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .execute(new StringCallBack(httpCallBack));
    }

    //get方法
    public static void httpGet(Context context, String httpUrl, Map<String, String> paramsMap, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[get] httpUrl= " + httpUrl);

        Set<String> keySet = paramsMap.keySet();
        for (String key:keySet) {
            String value = paramsMap.get(key);
            Log.i(TAG, "[get params data] " + key + "=" + value);
        }

        OkHttpUtils.get(httpUrl)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .params(paramsMap)
                .execute(new StringCallBack(httpCallBack));
    }


    //post方法
    public static void httpPost(Context context, String httpUrl, Map<String, String> paramsMap, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[post] httpUrl= " + httpUrl);

        Set<String> keySet = paramsMap.keySet();
        for (String key:keySet) {
            String value = paramsMap.get(key);
            Log.i(TAG, "[post data] " + key + "=" + value);
        }

        OkHttpUtils.post(httpUrl)
            .connTimeOut(C.TIME_OUT)
            .writeTimeOut(C.TIME_OUT)
            .readTimeOut(C.TIME_OUT)
            .headers("timestamp", timestamp.toString())
            .headers("cid", cid)
            .params(paramsMap)
            .execute(new StringCallBack(httpCallBack));
    }

    //post方法
    public static void httpPost(Context context, String httpUrl, String jsonInBody, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[post] httpUrl= " + httpUrl);

//        Set<String> keySet = paramsMap.keySet();
//        for (String key:keySet) {
//            String value = paramsMap.get(key);
//            Log.i(TAG, "[post data] " + key + "=" + value);
//        }

        LogUtils.i(context.getClass().getName(), "[post data] " + "=" + jsonInBody);

        Gson gson = new Gson();
        //  创建  RequestBody
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonInBody);
        OkHttpUtils.post(httpUrl)
                .connTimeOut(C.TIME_OUT)
                .writeTimeOut(C.TIME_OUT)
                .readTimeOut(C.TIME_OUT)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .requestBody(requestBody)
                .execute(new StringCallBack(httpCallBack));
    }

    //post方法
    public static void httpPostString(Context context, String httpUrl, String imageStr, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[post] httpUrl= " + httpUrl);

        LogUtils.i(context.getClass().getName(), "[post data] " + "=" + imageStr);

        Gson gson = new Gson();
        //  创建  RequestBody
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), imageStr);
        OkHttpUtils.post(httpUrl)
                .connTimeOut(C.TIME_OUT)
                .writeTimeOut(C.TIME_OUT)
                .readTimeOut(C.TIME_OUT)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .requestBody(requestBody)
                .execute(new StringCallBack(httpCallBack));
    }

    //delete方法
    public static void httpDelete(Context context, String httpUrl, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        LogUtils.i(context.getClass().getName(), "[delete] httpUrl= " + httpUrl);

        OkHttpUtils.delete(httpUrl)
                .connTimeOut(C.TIME_OUT)
                .writeTimeOut(C.TIME_OUT)
                .readTimeOut(C.TIME_OUT)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .execute(new StringCallBack(httpCallBack));
    }


    //put方法
    public static void httpPut(Context context, String httpUrl, Map<String, String> paramsMap, StringCallBack.HttpCallBack httpCallBack) {
        cid = SPUtils.getString(context.getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(context.getApplicationContext(),"cid", cid);
        }

        Long timestamp = System.currentTimeMillis();
        Log.i(TAG, "[post] httpUrl= " + httpUrl);

        Set<String> keySet = paramsMap.keySet();
        for (String key:keySet) {
            String value = paramsMap.get(key);
            Log.i(TAG, "[post data] " + key + "=" + value);
        }

        OkHttpUtils.put(httpUrl)
                .connTimeOut(C.TIME_OUT)
                .writeTimeOut(C.TIME_OUT)
                .readTimeOut(C.TIME_OUT)
                .headers("timestamp", timestamp.toString())
                .headers("cid", cid)
                .params(paramsMap)
                .execute(new StringCallBack(httpCallBack));
    }






    //---以下是具体的API接口----

    /**
     * 获取登陆验证码
     * @param context
     * @param mobile
     * @param callback
     */
    public static void getLoginCode(Context context, String mobile, StringCallBack.HttpCallBack callback){
//        okhttp_get(context, Urls.loginCode + mobile, callback);
        httpGet(context, Urls.LOGIN_CODE + mobile, callback);
    }

    public static void getRegisterCode(Context context, String mobile, String imgCode, StringCallBack.HttpCallBack callback){
//        okhttp_get(context, Urls.loginCode + mobile, callback);
        String strParam =  mobile + "\\/" + imgCode;
        httpGet(context, Urls.REGISTER_CODE + strParam, callback);
    }


    /**
     * 登陆
     * @param context
     * @param mobile
     * @param loginCode
     * @param callback
     */
    public static void login(Context context, String mobile, String loginCode, StringCallBack.HttpCallBack callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("loginCode", loginCode);
//        okhttp_post(context, Urls.login, params, callback);

        httpPost(context, Urls.LOGIN, params, callback);
    }

    public static void register(Context context, String mobile, String registerCode, StringCallBack.HttpCallBack callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", mobile);
        params.put("mobile", mobile);
        params.put("registerCode", registerCode);
//        okhttp_post(context, Urls.login, params, callback);

        httpPost(context, Urls.REGISTER, params, callback);
    }

    public static void getAds(Context context, String type, StringCallBack.HttpCallBack callback) {
//        okhttp_get(context, Urls.ads + type, callback);
        httpGet(context, Urls.ADS + type, callback);
    }

    public static void getCheckUpdate(Context context, String version, StringCallBack.HttpCallBack callback) {
        httpGet(context, Urls.CHECK_VERSION + "?version=" + version, callback);
    }

    public static void sos(Context context, String type, float lat, float lng, StringCallBack.HttpCallBack callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("lng", String.valueOf(lng));
        params.put("lat", String.valueOf(lat));
//        okhttp_post(context, Urls.sos, params, callback);
        httpPost(context, Urls.SOS, params, callback);
    }

    public static void stopSos(Context context, String id, StringCallBack.HttpCallBack callback) {
        httpDelete(context, Urls.SOS + "/" + id, callback);
    }

    //获取我的紧急联系人
    public static void getContacts(Context context, StringCallBack.HttpCallBack callback) {
        httpGet(context, Urls.CONTACTS, callback);
    }

    //保存紧急联系人
    public static void saveContact(Context context, String username, String mobile, StringCallBack.HttpCallBack callBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", username);
        params.put("mobile", mobile);
        httpPost(context, Urls.CONTACT, params, callBack);
    }

    //保存紧急联系人
    public static void saveContacts(Context context, HashMap<String, String> params, StringCallBack.HttpCallBack callBack) {
        httpPost(context, Urls.CONTACTS, params, callBack);
    }

    //保存紧急联系人
    public static void saveContacts(Context context, String contactJson, StringCallBack.HttpCallBack callBack) {
        httpPost(context, Urls.CONTACTS, contactJson, callBack);
    }

    public static void deleteContact(Context context, String id, StringCallBack.HttpCallBack callback) {
        httpDelete(context, Urls.CONTACT + "/" + id, callback);
    }

    public static void getUserInfo(Context context, StringCallBack.HttpCallBack callback) {
        httpGet(context, Urls.USER, callback);
    }

    //保存用户信息
    public static void saveUserInfo(Context context, HashMap<String, String> params, StringCallBack.HttpCallBack callBack) {
        httpPut(context, Urls.USER, params, callBack);
    }

    //上传头像
    public static void uploadPortrait(Context context, String strBase64, StringCallBack.HttpCallBack callBack) {
        httpPostString(context, Urls.PORTRAIT, strBase64, callBack);
    }
}
