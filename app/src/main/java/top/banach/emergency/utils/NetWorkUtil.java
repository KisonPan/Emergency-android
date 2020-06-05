package top.banach.emergency.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetWorkUtil {

	/** Unknown network class. */
	public static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	public static final int NETWORK_CLASS_2_G = 1;
	/** Class of broadly defined "3G" networks. */
	public static final int NETWORK_CLASS_3_G = 2;
	/** Class of broadly defined "4G" networks. */
	public static final int NETWORK_CLASS_4_G = 3;
	/** wifi网络 */
	public static final int NETWORK_CLASS__WIFI = 4;
	/** 获取运营商失败 */
	public static final int OPERATOR_CLASS_FAILURE = 0;
	/** 移动 */
	public static final int OPERATOR_CLASS_CMCC = 1;
	/** 联通 */
	public static final int OPERATOR_CLASS_CUCC = 2;
	/** 电信 */
	public static final int OPERATOR_CLASS_CTCC = 3;
	/** 其他 */
	public static final int OPERATOR_CLASS_OTHER = 4;

	/**
	 * 检测网络连接状态
	 * 
	 * @return true-网络可用；false-网络不可用
	 */
	public static boolean checkConnectState(Context mContext) {
		boolean isConnect = false;
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				// connected to wifi
				isConnect = true;
			}
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				// connected to the mobile provider's data plan
				isConnect = true;
			}
		}
		return isConnect;
	}

	/**
	 * 检测wifi连接状态
	 * 
	 * @return true-wifi可用；false-wifi不可用
	 */
	public static boolean checkWifiConnectState(Context mContext) {
		boolean isWifiConnect = false;
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
					isWifiConnect = true;
				}
			}
		}
		return isWifiConnect;
	}
	
	/**
	 * 返回网络类型，如未知网络、 2G、3G、4G对应0、1、2、3
	 */
	public static int getNetworkClass(int networkType) {
		switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case TelephonyManager.NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}

	
	/**
	 * 获取网络状态：未知网络,2g,3g,4g,wifi 对应值0，1，2，3，4
	 */
	public static int getNetWorkType(Context context) {
		int mNetWorkType = NETWORK_CLASS_UNKNOWN;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();
			Log.i(NetWorkUtil.class.getSimpleName(),
					"\n getDetailedState=" + networkInfo.getDetailedState() + 
					"\n getReason=" + networkInfo.getReason() + 
					"\n getSubtype=" + networkInfo.getSubtype() + 
					"\n getSubtypeName=" + networkInfo.getSubtypeName() + 
					"\n getExtraInfo=" + networkInfo.getExtraInfo() + 
					"\n getTypeName=" + networkInfo.getTypeName() + 
					"\n getType=" + networkInfo.getType());
			if (type == ConnectivityManager.TYPE_WIFI) {
				mNetWorkType = NETWORK_CLASS__WIFI;
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				int subtype = networkInfo.getSubtype();
				mNetWorkType = getNetworkClass(subtype);
			}
		} else {
			mNetWorkType = NETWORK_CLASS_UNKNOWN;
		}

		return mNetWorkType;
	}

	/**
	 * 获取网络运营商：未知,移动,联通,电信,其他, 对应值0，1，2，3，4
	 */
	public static int getOperator(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
				// 中国移动
				return OPERATOR_CLASS_CMCC;
			} else if (operator.equals("46001")) {
				// 中国联通
				return OPERATOR_CLASS_CUCC;
			} else if (operator.equals("46003")) {
				// 中国电信
				return OPERATOR_CLASS_CTCC;
			} else {
				// 其他
				return OPERATOR_CLASS_OTHER;
			}
		} else {
			return OPERATOR_CLASS_FAILURE;
		}

	}
}
