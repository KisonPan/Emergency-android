package top.banach.emergency.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	public static final String CACHE_ROOT_DIRECTORY = "hdMoble"; // 缓存根目录
	public static final String CACHE_FILE_DIRECTORY = "fileCache"; // 文件缓存

	/**
	 * 根据图片文件路径名，取得文件后缀
	 * 
	 * @param filepath
	 */
	public static String getFileSuffix(String filepath) {
		return filepath.substring(filepath.lastIndexOf(".") + 1);
	}

	/**
	 * 根据图片文件路径名，取得文件名（不包含后缀）
	 * 
	 * @param filepath
	 */
	public static String getFileName(String filepath) {
		return filepath.substring(filepath.lastIndexOf("/") + 1, filepath.lastIndexOf("."));
	}

	/**
	 * 
	 * 功能:压缩多个文件成一个zip文件
	 * <p>
	 * zhaomx , 2013 17:04:40 pm
	 * 
	 * @param srcfile
	 *            ：源文件列表
	 * @param zipfile
	 *            ：压缩后的文件
	 */
	public static boolean zipFiles(File[] srcfile, File zipfile) {
		boolean flag = false;
		byte[] buf = new byte[1024];
		try {
			// ZipOutputStream类：完成文件或文件夹的压缩
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
			System.out.println("压缩完成.");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否存在SD卡
	 * 
	 * @return
	 */
	public static final boolean hasSDCard() {

		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static final String getCachePath(Context context) {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().toString() + File.separator + CACHE_ROOT_DIRECTORY;
		} else {
			// return context.getFilesDir()+File.separator+CACHE_ROOT_DIRECTORY;
			return context.getCacheDir() + File.separator + CACHE_ROOT_DIRECTORY;
		}
	}

	/**
	 * 获取缓存文件路径
	 * 
	 * @param context
	 * @return
	 */
	public static final String getFileCachePath(Context context) {
		String path = getCachePath(context) + File.separator + CACHE_FILE_DIRECTORY;

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	public static final byte[] getFileBytes(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				int length = fin.available();
				byte[] buffer = new byte[length];
				fin.read(buffer);
				fin.close();
				fin = null;
				return buffer;
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 拷贝文件
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}
}
