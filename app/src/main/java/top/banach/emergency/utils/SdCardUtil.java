package top.banach.emergency.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import java.io.File;

/**
 * <pre>
 * Title: 功能/模块 
 * Description: SdCard工具类
 * 修订历史： 日期          作者        参考         描述
 * </pre>
 *
 * @author Sean.Hayes
 * @email lwh_vanness@139.com
 * @version 1.0
 * @date 2016年7月14日
 */
public class SdCardUtil {

	/**
	 * 判断sd卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSdCardAvailable() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	/**
	 * 获取SdCard根目录
	 * 
	 * @return
	 */
	public static File getExternalStorageDirectory() {
		File sdDir = Environment.getExternalStorageDirectory();
		return sdDir;
	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		File sdDir = null;
		if (isSdCardAvailable()) {
			sdDir = getExternalStorageDirectory();
			return sdDir.getAbsolutePath();// sdDir.toString();
		} else {
			return null;
		}

	}

	/**
	 * 获取数据库文件路径
	 * 
	 * @return
	 */
	public static String getDatabasePath(String name) {
		String dbfile = getSdCardPath() + File.separator + name;
		if (!dbfile.endsWith(".db")) {
			dbfile += ".db";
		}

		File result = new File(dbfile);

		if (!result.getParentFile().exists()) {
			result.getParentFile().mkdirs();
		}
		return result.getAbsolutePath();
	}

	/**
	 * 判断包名所对应的应用是否安装在SD卡上
	 * 
	 * @param context 上下文变量
	 * @param packageName 包名 @return, true if install on sd card
	 */
	public static boolean isInstallOnSdCard(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		ApplicationInfo appInfo;
		try {
			appInfo = pm.getApplicationInfo(packageName, 0);
			if ((appInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
