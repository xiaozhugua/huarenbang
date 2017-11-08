package com.abcs.huaqiaobang.model;

import android.graphics.Bitmap;

import com.abcs.sociax.android.R;
import com.abcs.huaqiaobang.view.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class Options {
	/** 新闻列表中用到的图片加载配置 */
	public static DisplayImageOptions getListOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
//				 .showImageOnLoading(R.drawable.bg_weibodigg_count)
//				 设置图片Uri为空或是错误的时候显示的图片
//				 .showImageForEmptyUri(R.drawable.bg_weibodigg_count)
//				 设置图片加载/解码过程中错误时候显示的图片
//				 .showImageOnFail(R.drawable.bg_weibodigg_count)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)

				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入

				.build();
		return options;
	}
	public static DisplayImageOptions getListOptions2() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
//				 .showImageOnLoading(R.drawable.bg_weibodigg_count)
//				 设置图片Uri为空或是错误的时候显示的图片
//				 .showImageForEmptyUri(R.drawable.bg_weibodigg_count)
//				 设置图片加载/解码过程中错误时候显示的图片
//				 .showImageOnFail(R.drawable.bg_weibodigg_count)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)

				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new SimpleBitmapDisplayer())// 淡入

				.build();
		return options;
	}

	public static DisplayImageOptions options;

	/** 新闻列表中用到的图片加载配置圆角 */
	public static DisplayImageOptions getCircleListOptions() {
		if (options != null) {
			return options;
		}
		options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
//				.showImageOnLoading(R.drawable.img_avatar)
				// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageForEmptyUri(R.drawable.img_avatar)
				// 设置图片加载/解码过程中错误时候显示的图片
//				.showImageOnFail(R.drawable.img_avatar)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.displayer(new CircleBitmapDisplayer()).build();
		return options;
	}

	public static DisplayImageOptions noMemOptions;

	/** 新闻列表中用到的图片加载配置圆角 */
	public static DisplayImageOptions getCornerListOptions() {
		if (noMemOptions != null) {
			return noMemOptions;
		}
		noMemOptions = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
//				.showImageOnLoading(R.drawable.img_morentupian)
				// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageForEmptyUri(R.drawable.img_morentupian)
				// 设置图片加载/解码过程中错误时候显示的图片
//				.showImageOnFail(R.drawable.img_morentupian)
				.cacheInMemory(false)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(false)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				 .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(100))// 淡入

				.build();
		return noMemOptions;
	}
	public static DisplayImageOptions getHDOptions() {

		DisplayImageOptions Options = new DisplayImageOptions.Builder()
				/*// // 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.img_huaqiao_default)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.img_huaqiao_default)
				// 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.img_huaqiao_default)*/
				.cacheInMemory(true)
						// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
						// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.NONE)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
						// .decodingOptions(android.graphics.BitmapFactory.Options
						// decodingOptions)//设置图片的解码配置
						// 设置图片下载前的延迟
						// .delayBeforeLoading(int delayInMillis)//int
						// delayInMillis为你设置的延迟时间
						// 设置图片加入缓存前，对bitmap进行设置
						// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
						// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		return Options;
	}

	public static DisplayImageOptions getUserHeadOptions() {

		DisplayImageOptions Options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.img_huaqiao_default)
						// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.img_hwg_head_default)
						// 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.img_hwg_head_default)
				.cacheInMemory(true)
						// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
						// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
						// .decodingOptions(android.graphics.BitmapFactory.Options
						// decodingOptions)//设置图片的解码配置
						// 设置图片下载前的延迟
						// .delayBeforeLoading(int delayInMillis)//int
						// delayInMillis为你设置的延迟时间
						// 设置图片加入缓存前，对bitmap进行设置
						// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
						// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入

				.build();
		return Options;
	}
}
