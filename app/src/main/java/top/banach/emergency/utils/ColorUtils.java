package top.banach.emergency.utils;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class ColorUtils {

	/**
	 * 改变String字符串的部分字体颜色
	 * 
	 * @param mContext环境变量
	 * @param value
	 *            传入的字符串
	 * @return
	 */
	public static SpannableString changeTextColor(Context context, int xmlCororId, String value) {
		// TODO Auto-generated method stub
		SpannableString sp = new SpannableString(value);
		sp.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						xmlCororId)), 0, value.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return sp;
	}

	/**
	 * 改变String字符串的部分字体颜色
	 * 
	 * @param mContext环境变量
	 * @param value
	 *            传入的字符串
	 * @param start
	 *            起始位置
	 * @return
	 */
	public static SpannableString changeTextColor(Context context, int xmlCororId,
                                                  String value, int start) {
		// TODO Auto-generated method stub
		SpannableString sp = new SpannableString(value);
		sp.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						xmlCororId)), start, value.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return sp;
	}
	
	/**
	 * 改变String字符串的部分字体颜色
	 * 
	 * @param mContext环境变量
	 * @param value
	 *            传入的字符串
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static SpannableString changeTextColorByEnd(Context context, int xmlCororId,
                                                       String value, int end) {
		// TODO Auto-generated method stub
		SpannableString sp = new SpannableString(value);
		sp.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						xmlCororId)), 0, end,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return sp;
	}

	/**
	 * 改变String字符串的部分字体颜色
	 * 
	 * @param mContext环境变量
	 * @param value
	 *            传入的字符串
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static SpannableString changeTextColor(Context context, int xmlCororId,
                                                  String value, int start, int end) {
		// TODO Auto-generated method stub
		SpannableString sp = new SpannableString(value);
		sp.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						xmlCororId)), start, end,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return sp;
	}
	
	public static int getResColor(Context context, int resid) {
		int color = context.getResources().getColor(
				resid);
		return color;
	}

}
