package top.banach.emergency.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于管理Activity的类。在程序�?��的时候清除所有的Activity
 * 
 * @author Kison Pan
 * 
 */
public class AppActivityManager {

	private List<Activity> activities = null;
	private static AppActivityManager instance;

	private AppActivityManager() {
		activities = new ArrayList<Activity>();
	}

	public static AppActivityManager getInstance() {
		if (instance == null) {
			instance = new AppActivityManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (activities != null && activities.size() > 0) {
			if (!activities.contains(activity)) {
				activities.add(activity);
			}
		} else {
			activities.add(activity);
		}
	}

	/**
	 * 移除Activity到容器中
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
		activity.finish();
	}

//	/**
//	 * 返回主界面
//	 */
//	public void backToMain() {
//		if (activities != null && activities.size() > 0) {
//			for (int i = 0; i < activities.size(); i++) {
//				if (activities.get(i) instanceof MainActivity) {
//					continue;
//				}
//				activities.get(i).finish();
//			}
//		}
//	}



	/**
	 * 退出应用
	 */
	public void exit(Context mContext) {
		try {
			for (Activity activity : activities) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * 后台运行，不退出
	 * 
	 * @param context
	 */
	public void moveTaskToBack(Context context) {
		try {
			for (Activity activity : activities) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((Activity) context).moveTaskToBack(false);
		}
	}

	public static void openActivity(Activity activity, Class<?> cls) {
		Intent intent = new Intent(activity, cls);
		activity.startActivity(intent);
		activity.finish();
	}

}
