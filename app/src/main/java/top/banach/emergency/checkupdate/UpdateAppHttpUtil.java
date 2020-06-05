package top.banach.emergency.checkupdate;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lzy.okhttputils.request.BaseRequest;
import com.vector.update_app.HttpManager;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.utils.HttpUtils;
import top.banach.emergency.utils.StringUtil;

public class UpdateAppHttpUtil implements HttpManager {

    private Context context;
    public UpdateAppHttpUtil(Context context) {
        this.context = context;
    }
    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {


        String strResult = "{\n" +
                "  \"update\": \"Yes\",\n" +
                "  \"new_version\": \"0.8.3\",\n" +
                "  \"apk_file_url\": \"https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk\",\n" +
                "  \"update_log\": \"1，添加删除信用卡接口。\\r\\n2，添加vip认证。\\r\\n3，区分自定义消费，一个小时不限制。\\r\\n4，添加放弃任务接口，小时内不生成。\\r\\n5，消费任务手动生成。\",\n" +
                "  \"target_size\": \"5M\",\n" +
                "  \"new_md5\":\"b97bea014531123f94c3ba7b7afbaad2\",\n" +
                "  \"constraint\": false\n" +
                "}";
        String checkResult = StringUtil.replace(strResult);
        callBack.onResponse(checkResult);

        Log.i("Kison", "----asyncGet----");
//        Api.httpGet(context, url, params, new StringCallBack.HttpCallBack() {
//            @Override
//            public void httpSucc(String result, Object request) {
//                Log.i("Kison", "----asyncGet-httpSucc---");
//                callBack.onResponse(result);
//            }
//
//            @Override
//            public void httpfalse(String result) {
//                Log.i("Kison", "----asyncGet-httpfalse---" + result);
//                callBack.onError(result);
//            }
//        });

    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        Log.i("Kison", "----httpSucc----");
        Api.httpPost(context, url, params, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                Log.i("Kison", "----httpSucc-httpfalse---");
                callBack.onResponse(result);
            }

            @Override
            public void httpfalse(String result) {
                Log.i("Kison", "----asyncPost-httpfalse---");
                callBack.onError(result);
            }
        });

    }

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final FileCallback callback) {
//        OkHttpUtils.get(url)
//                .execute(new FileCallback(path, fileName) {
//                    @Override
//                    public void onProgress(float progress, long total) {
//                        callback.onProgress(progress, total);
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        callback.onError(error);
//                    }
//
//                    @Override
//                    public void onResponse(File file) {
//                        callback.onResponse(file);
//                    }
//
//                    @Override
//                    public void onBefore() {
//                        callback.onBefore();
//                    }

//                    @Override
//                    public void inProgress(float progress, long total, int id) {
//                        super.inProgress(progress, total, id);
//                        callback.onProgress(progress, total);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e, int id) {
//                        callback.onError(validateError(e, response));
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        callback.onResponse(response);
//
//                    }
//
//                    @Override
//                    public void onBefore(Request request, int id) {
//                        super.onBefore(request, id);
//                        callback.onBefore();
//                    }
//                });

        Log.i("Kison", "----httpSucc----");

        HttpUtils.downloadFile(context, url, new com.lzy.okhttputils.callback.FileCallback(path, fileName) {
            @Override
            public void onResponse(boolean b, File file, Request request, Response response) {

                Log.i("Kison", "----onResponse----");
                callback.onResponse(file);
            }

            @Override
            public void onError(boolean isFromCache, Call call, Response response, Exception e) {
                super.onError(isFromCache, call, response, e);
                callback.onError(e.getMessage());
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(progress, totalSize);
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                callback.onBefore();
            }
        });
    }
}
