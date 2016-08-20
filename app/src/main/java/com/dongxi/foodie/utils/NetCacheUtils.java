package com.dongxi.foodie.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存
 * 
 * @author Kevin
 * 
 */
public class NetCacheUtils {

	private LocalCacheUtils mLocalCacheUtils;
	private MemoryCacheUtils mMemoryCacheUtils;

	public NetCacheUtils(LocalCacheUtils localCacheUtils,
			MemoryCacheUtils memoryCacheUtils) {
		mLocalCacheUtils = localCacheUtils;
		mMemoryCacheUtils = memoryCacheUtils;
	}

	/**
	 * 从网络下载图片
	 * 
	 * @param ivPic
	 * @param url
	 */
	public void getBitmapFromNet(ImageView ivPic, String url) {
		new BitmapTask().execute(ivPic, url);// 启动AsyncTask,
												// 参数会在doInbackground中获取
	}

	/**
	 * Handler和线程池的封装
	 * 
	 * 第一个泛型: 参数类型 第二个泛型: 更新进度的泛型, 第三个泛型是onPostExecute的返回结果
	 * 
	 * @author Kevin
	 * 
	 */
	class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

		private ImageView ivPic;
		private String url;

		/**
		 * 后台耗时方法在此执行, 子线程
		 */
		@Override
		protected Bitmap doInBackground(Object... params) {
			ivPic = (ImageView) params[0];
			url = (String) params[1];
			ivPic.setTag(url);// 将url和imageview绑定

			return downloadBitmap(url);
		}

		/**
		 * 更新进度, 主线程
		 */
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		/**
		 * 耗时方法结束后,执行该方法, 主线程
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				String bindUrl = (String) ivPic.getTag();

				if (url.equals(bindUrl)) {// 确保图片设定给了正确的imageview
					ivPic.setImageBitmap(result);
					mLocalCacheUtils.setBitmapToLocal(url, result);// 将图片保存在本地
					mMemoryCacheUtils.setBitmapToMemory(url, result);// 将图片保存在内存
					System.out.println("从网络缓存读取图片啦...");
				}
			}
		}
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap downloadBitmap(String url) {

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream inputStream = conn.getInputStream();
				//图片压缩处理
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inJustDecodeBounds = false;
				option.inPurgeable = true;
				option.inInputShareable = true;

				//option.inSampleSize = 1;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
				//reqWidth  所需图片压缩尺寸最小宽度
				//reqHeight 所需图片压缩尺寸最小高度
				option.inSampleSize = calculateInSampleSize(option, 400,
						600);
				//option.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式

				Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, option);
				return bitmap;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}

		return null;
	}
/**
 * 图片压缩算法
 */
	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
