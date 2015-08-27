package com.lsz.img;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener{

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.iv_img);
		findViewById(R.id.btn_getImage).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		imageView.setImageBitmap(getCompressionBitmap("/mnt/sdcard/lsz.jpg"));
	}
	
	
	/**
	 * 处理大图片，避免 OOM
	 * @param imgPath : 图片路径
	 * @return : 返回缩放后的图片
	 */
	public Bitmap getCompressionBitmap(String imgPath){
//		1. 得到屏幕的宽高信息
		WindowManager windowManager = MainActivity.this.getWindowManager();
			int width  = windowManager.getDefaultDisplay().getWidth();
			int height = windowManager.getDefaultDisplay().getHeight();
			System.out.println("屏幕的宽 = " + width + "   高 = " + height);
			
//		2.得到图片的宽高
		Options options = new BitmapFactory.Options();    // 解析位图的附加条件
			options.inJustDecodeBounds = true;            // 只解析位图的头文件信息(图片的附加信息)
		BitmapFactory.decodeFile(imgPath, options);
		int bitmapWidth  = options.outWidth;	
		int bitmapHeight = options.outHeight;
			System.out.println("图片的宽 = " + bitmapWidth + "   高 = " + bitmapHeight);
			
//		3.计算图片的缩放比例
		int dx = bitmapWidth  / width;
		int dy = bitmapHeight / height;
		
		int scale = 1;
		if(dx > dy && dy > 1){
			System.out.println("按照水平方向绽放，缩放比例 = " + dx);
			scale = dx;
		}
		if(dy > dx && dx > 1){
			System.out.println("按照垂直方法缩放，缩放比例 = " + dy);
			scale = dy;
		}
		
//		4.缩放图片加载到内存
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;   // 解析全部图片
		return BitmapFactory.decodeFile(imgPath, options);
	}
}

























