package top.banach.emergency.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * bitmap压缩专用方法
 * 
 * @author wenjian
 * 
 */
public class BitmapUtil {

	/**
	 * 指定大小压缩 自适应水平 竖直图片
	 * 
	 * @param bx
	 *            想要的x
	 * @param by
	 *            想要的y
	 * @return
	 */
	public static Bitmap bitmapResizes(Bitmap bm, int bx, int by) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		int newWidth = 0;
		int newHeight = 0;
		// 考虑图片是横还是竖的
		if (width >= height) {
			// 横设置想要的大小
			newWidth = bx > by ? bx : by;
			newHeight = bx > by ? by : bx;
		} else {
			// 竖设置想要的大小
			newWidth = bx > by ? by : bx;
			newHeight = bx > by ? bx : by;
		}
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片 createBitmap(//原始位图,//剪切的其实x,//剪切的起始y,//想要的宽度,//想要的高度,//)
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * bitmap转file保存 
	 * @param bmp
	 * @param filename 要保存的路径
	 * @return
	 */
	public static String saveBitmap2file(Bitmap bmp, String filename) {
		String trsultString="";
		CompressFormat format = CompressFormat.JPEG;
		int quality = 100;
//		int quality = 30;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filename);
			bmp.compress(format, quality, stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			trsultString="照片处理错误!"+e.getMessage();
		}

		return trsultString;
	}
	
    /** 
     * Bitmap to Drawable 
     * @param bitmap 
     * @param mcontext 
     * @return 
     */  
    public static Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext){
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;  
    } 
    
    public static Bitmap getBitmap(String photoPath) {
    	Bitmap bm = BitmapFactory.decodeFile(photoPath);
    	return bm;
    }
    
    public static void compressBitmap(String photoPath) {
		//图片压缩替换原图
		Options options = new Options();
		options.inSampleSize = 3;//变为原图的4分之1
		options.inPurgeable=true;//如果 inPurgeable 设为True的话表示使用BitmapFactory创建的Bitmap   
		                         //* 用于存储Pixel的内存空间在系统内存不足时可以被回收，   可以回收部分Bitmap占据的内存空间，这时一般不会出现OutOfMemory 错误。
		options.inInputShareable=true;
		options.inJustDecodeBounds = false;//true可以不把图片读到内存中,但依然可以计算出图片的大小 ,false读到内存中 如果需要展示 必须读到内存
		Bitmap bm = BitmapFactory.decodeFile(photoPath,options);
		if (bm != null) {
			//指定大小压缩
			Bitmap newbm=BitmapUtil.bitmapResizes(bm, 300, 400);
			//开始进行替换
			String tagb=BitmapUtil.saveBitmap2file(newbm, photoPath);
			//替换操作的结果
//			if(tagb.length()<=0){
//			}else{
//				showToast(tagb);
//				return;
//			}
		}
    }

	/**
	 * 将字符串转换成Bitmap类型（Base64字符串转换成图片）
	 * @param imgBase64
	 * @return
	 */
	public static Bitmap base64toBitmap(String imgBase64){
		Bitmap bitmap=null;
		try {
			byte[]bitmapArray;
			bitmapArray= Base64.decode(imgBase64, Base64.DEFAULT);
			bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将图片转成base64
	 * @param imgPath
	 * @return
	 */
	public static String bitmapToBase64(String imgPath) {
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		byte[] bytes = os.toByteArray();
		byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
		String encodeStr = new String(encode);
		return encodeStr;
	}

//	public Bitmap Base64ToBitMap(String base64) {
//		byte[] decode = Base64.decode(base64, Base64.DEFAULT);
//		Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//		return bitmap;
//	}

	/**
	 * 二进制流转换为Bitmap图片
	 * @param temp
	 * @return
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * Bitmap转换为二进制流
	 * @param bitmap
	 * @return
	 */
	private static byte[] bitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] imgBytes = baos.toByteArray();
		return imgBytes;
	}

	/**
	 * 照片转byte二进制
	 * String路径图片转二进制
	 * @param imagepath 需要转byte的照片路径
	 * @return 已经转成的byte
	 * @throws Exception
	 */
	public static byte[] readStream(String imagepath) throws Exception {
		FileInputStream fs = new FileInputStream(imagepath);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while (-1 != (len = fs.read(buffer))) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		fs.close();
		return outStream.toByteArray();
	}
}
