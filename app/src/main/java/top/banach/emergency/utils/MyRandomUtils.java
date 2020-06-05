package top.banach.emergency.utils;

import java.util.Random;
import java.util.UUID;

public class MyRandomUtils {
	/** 取得javaId:时间戳+"_"+三位随机数 */
	public static String getJavaId() {
		return System.currentTimeMillis() + "_" + getThreeRandom();
	}
	
	/** 取得随机生成的图片路径:时间 年月日时分秒+"_"+三位随机数  + "_" +指定的后缀.jpg*/
	public static String getRandomImagePath(String suffix) {
		return StringUtil.getCurrentTime() + "_" + getThreeRandom() + "_" + suffix + ".jpg";
	}

	/** 得到三为随机数 */
	public static int getThreeRandom() {
		Random random = new Random();
		return random.nextInt(900) + 100;
	}
	/** 獲取 uuid + 時間戳*/
	public static String getUUIDPlusCurrentTime(){
		return UUID.randomUUID().toString()+ System.currentTimeMillis();
	}
	/**
	 * 获取缓存图片的命名 (.jpg)
	 * @return
	 */
	public static String getImgNameByUUIDPlusCurrentTime(){
		return getUUIDPlusCurrentTime()+".jpg";
	}
}
