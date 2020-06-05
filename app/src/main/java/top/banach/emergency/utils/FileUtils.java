package top.banach.emergency.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;

/**
 * 用于文件操作
 * 
 * @author MJ
 * 
 */
public class FileUtils {

	// public static boolean isHasSDcard(){
	// boolean has = false;
	//
	// if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
	//
	// }
	// }

	public static String getDir(String path) {

		return path.substring(0, path.lastIndexOf(File.separator));
	}

	public static String getFileName(String path) {

		return path.substring(path.lastIndexOf(File.separator) + 1, path.length());
	}

	/**
	 * 
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 * 
	 *            源文件
	 * 
	 * @param target
	 * 
	 *            复制到的新文件
	 */

	public static void copyFileChannel(File s, File target) {

		FileInputStream fi = null;

		FileOutputStream fo = null;

		FileChannel in = null;

		FileChannel out = null;

		try {

			// if (isFileExist(target.getAbsolutePath()))
			// deleteFile(target);

			createFileInSDCard(target.getAbsolutePath());

			fi = new FileInputStream(s);

			fo = new FileOutputStream(target);

			in = fi.getChannel();// 得到对应的文件通道

			out = fo.getChannel();// 得到对应的文件通道

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				fi.close();

				in.close();

				fo.close();

				out.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * URL编码
	 * 
	 * @param path
	 * @return
	 */
	public static String encode(String path) {
		try {
			path = URLEncoder.encode(path.replaceAll(" ", "%20"), "UTF-8");
			// path = URLEncoder.encode(path,"UTF-8").replaceAll("%3A",
			// ":").replaceAll("%2F", "/").replaceAll("\\+",
			// "%20").replaceAll("+", "%20");

		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 创建新的文件
	 * 
	 * @param dirPath
	 *            文件目录
	 * @param name
	 *            文件名
	 */
	public static File createFileInSDCard(String dirPath, String name) throws Exception {

		// File file = new File(dirPath, StrUtils.encodedUrl(name));
		File file = new File(dirPath, name);
		file.createNewFile();
		return file;
	}

	public static File createFileInSDCard(String name) throws Exception {
		int index = name.lastIndexOf(File.separator);
		String dir = name.substring(0, index);
		creatSDDir(dir);
		return createFileInSDCard(dir, name.substring(index + 1));
	}

	/**
	 * 创建新目录
	 */
	public static File creatSDDir(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists())
			dirFile.mkdirs();
		return dirFile;
	}

	/**
	 * 判断文件存不存在
	 */
	public static boolean isFileExist(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		return new File(name).exists();
	}

	/**
	 * 判断文件存不存在且长度不为0
	 */
	public static boolean isFileExistLenghNotZero(String name) {
		File file = new File(name);
		return file.exists() && file.length() > 0;
	}

	/**
	 * 将流转化成文件，写进内存卡
	 */
	public static File write2SDFromInput(String name, InputStream input) {
		if (input == null)
			return null;// 如果流是空的，就返回空文件
		File file = null;
		try {
			file = createFileInSDCard(name);
			FileOutputStream output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp = -1;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void deleteFile(String path) {
		if (path == null || "".equals(path))
			return;
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file == null || !file.exists())
			return;
		file.delete();
	}

	/**
	 * 删除文件夹里的全部文件
	 * 
	 * @param root
	 */
	public static void deleteAllFiles(File root) {
		// 得到root的所有文件或者文件夹
		File[] files = root.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteAllFiles(file);
				} else {
					deleteFile(file);
				}
			}
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getSize(String filePath) {
		return new File(filePath).length();

	}

	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static String readTxtFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		try {

			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					sb.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param path
	 *            (/storage/emulated/0/roomacceptance/temp/diagramImages/
	 *            1472361132977.jpg)
	 * @return 1472361132977.jpg
	 */
	public String getSpitPath(String imgPath) {

		// 剪切
		int index = imgPath.lastIndexOf(File.separator);
		String path = imgPath.substring(index + 1);

		int end = path.lastIndexOf(".");
		if (end == -1) {
			end = path.length();
		}
		return path.substring(0, index);
	}

	// public static String getTempJpegPath() {
	// return Config.CAMERA_PATH + "/" + System.currentTimeMillis() + ".jpeg";
	// }
}
