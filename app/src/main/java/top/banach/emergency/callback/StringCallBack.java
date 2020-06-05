package top.banach.emergency.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * base callback
 * 
 * @author KisonPan
 * 
 */
public class StringCallBack extends com.lzy.okhttputils.callback.StringCallback {

	public interface HttpCallBack {
		public void httpSucc(String result, Object request);

		public void httpfalse(String result);
	}

	private Object request = 0;

	public StringCallBack(HttpCallBack callBack) {
		this.callBack = callBack;
	}

	public StringCallBack(HttpCallBack callBack, Object request) {
		this.callBack = callBack;
		this.request = request;
	}

	protected HttpCallBack callBack;

	@Override
	public void onResponse(boolean arg0, String result, Request request,
			 Response response) {

		if (!response.isSuccessful()) {
			callBack.httpfalse("网络请求失败，请稍候再试");
			Log.e("StringCallback", "HTTP Status " + response.code() + "\n" + result);
		} else {
			try {
				JSONObject resultObj = new JSONObject(result);
				if (resultObj.get("code").equals("1")) {
					Log.i("StringCallback", "result = " + result);
					callBack.httpSucc(result, this.request);
					return;
				} else if (resultObj.get("code").equals("0")) {
					callBack.httpfalse(result);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			callBack.httpfalse("网络请求失败，请稍候再试");
			Log.e("StringCallback", "网络请求失败: HTTP Status " + response.code() + " result: " + result);
		}

	}


	@Override
	public void onError(boolean isFromCache, Call call,
						@Nullable Response response, @Nullable Exception e) {
		super.onError(isFromCache, call, response, e);
		if (response != null) {
			Log.e("StringCallback", "HTTP Status " + response.code() + " \n" + response.body().toString());
		}

		if (e != null) {
			Log.e("StringCallback", "e.getMessage()  " + e.getMessage());
		}
		callBack.httpfalse("网络请求失败，请稍候再试");

	}

}
