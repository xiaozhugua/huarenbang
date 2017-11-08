/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.abcs.huaqiaobang.ytbt.storage;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;

import com.abcs.huaqiaobang.ytbt.common.utils.DemoUtils;
import com.abcs.huaqiaobang.ytbt.common.utils.FileAccessor;
import com.abcs.huaqiaobang.ytbt.common.utils.LogUtil;

import java.util.HashMap;


/**
 * 图片保存
 * @author Jorstin Chan@容联•云通讯
 * @date 2015-1-4
 * @version 4.0
 */
public class ImgInfoSqlManager extends AbstractSQLManager {

	public HashMap<String, Bitmap> imgThumbCache = new HashMap<String, Bitmap>(20);
	private static int column_index = 1;
	
	public static ImgInfoSqlManager mInstance;
	public static ImgInfoSqlManager getInstance() {
		if(mInstance == null) {
			mInstance = new ImgInfoSqlManager();
		}
		return mInstance;
	}
	
	static final String TABLES_NAME_IMGINFO = "imginfo";


//	public List<ViewImageInfo> getViewImageInfos(List<String> msgids) {
//		StringBuilder where = new StringBuilder();
//		if(msgids != null && !msgids.isEmpty()) {
//			where.append(" where " + ImgInfoColumn.MSG_LOCAL_ID  + " IN (");
//			for(int i = 0; i < msgids.size() ; i++) {
//				if(msgids.get(i) == null) {
//					continue ;
//				}
//				String id = msgids.get(i);
//				where.append("'" + id + "'");
//				if(i != msgids.size() - 1) {
//					where.append(",");
//				}
//			}
//			where.append(") ");
//		}
//		String sql = "select id , msglocalid ,bigImgPath , thumbImgPath from " + TABLES_NAME_IMGINFO + where.toString() + " ORDER BY id ,msglocalid ASC";
//		Cursor cursor = sqliteDB().rawQuery(sql , null);
//		List<ViewImageInfo> urls = null;
//		if(cursor !=null && cursor.getCount() > 0) {
//			urls = new ArrayList<ViewImageInfo>();
//			while(cursor.moveToNext()) {
//				urls.add(new ViewImageInfo(cursor));
//			}
//		}
//		return urls;
//	}

	public String getAllmsgid() {
		return null;
	}

	public class ImgInfoColumn extends BaseColumn{
		
		public static final String MSGSVR_ID = "msgSvrId";
		public static final String OFFSET = "offset";
		public static final String TOTALLEN ="totalLen";
		public static final String BIG_IMGPATH = "bigImgPath";
		public static final String THUMBIMG_PATH = "thumbImgPath";
		public static final String CREATE_TIME = "createtime";
		public static final String STATUS = "status";
		public static final String MSG_LOCAL_ID = "msglocalid";
		public static final String NET_TIMES = "nettimes";

	}
	
	private ImgInfoSqlManager() {
		Cursor cursor = sqliteDB().query(TABLES_NAME_IMGINFO, null, null, null, null, null, ImgInfoColumn.ID + " ASC ");
		if ((cursor.getCount() > 0) && (cursor.moveToLast())) {
			column_index = 1 + cursor.getInt(cursor.getColumnIndex(ImgInfoColumn.ID));
		}
		cursor.close();
		LogUtil.d(LogUtil.getLogUtilsTag(getClass()), "loading new img id:" + column_index);
	}
	

    public String getThumbUrlAndDel(String fileName) {
        if(TextUtils.isEmpty(fileName)) {
            return null;
        }
        if(fileName.trim().startsWith("THUMBNAIL://")) {
            String fileId = fileName.substring("THUMBNAIL://".length());
            String imgName = "tt";
            if(imgName == null) {
                return null;
            }
            String fileUrlByFileName = FileAccessor.getImagePathName() + "/" + imgName;
            delImgInfo(fileId);
            return fileUrlByFileName;
        }
        return null;
    }
	
	public Bitmap getThumbBitmap(String fileName , float scale) {
		if(TextUtils.isEmpty(fileName)) {
			return null;
		}
		if(fileName.trim().startsWith("THUMBNAIL://")) {
			String fileId = fileName.substring("THUMBNAIL://".length());
			String imgName = "tt";
			if(imgName == null) {
				return null;
			}
			String fileUrlByFileName = FileAccessor.getImagePathName() + "/" + imgName;
			//String fileUrlByFileName = FileAccessor.getFileUrlByFileName(imgName);
			Bitmap bitmap = imgThumbCache.get(fileUrlByFileName);
			if(bitmap == null || bitmap.isRecycled()) {
				Options options = new Options();
			    float density = 160.0F * scale;
			    options.inDensity = (int)density;
			    bitmap = BitmapFactory.decodeFile(fileUrlByFileName, options);
			    if (bitmap != null){
			    	bitmap.setDensity((int)density);
			    	bitmap = Bitmap.createScaledBitmap(bitmap, (int)(scale * bitmap.getWidth()), (int)(scale * bitmap.getHeight()), true);
			    	imgThumbCache.put(fileUrlByFileName, bitmap);
			    	LogUtil.d(TAG, "cached file " + fileName);
			    }
			}
			
			if(bitmap != null) {
				return DemoUtils.processBitmap(bitmap, /*bitmap.getWidth() / 15*/0);
			}
			
		}
		return null;
	}



    public long delImgInfo(String msgId) {
        String where = ImgInfoColumn.MSG_LOCAL_ID + "='" + msgId + "'";
       return getInstance().sqliteDB().delete(TABLES_NAME_IMGINFO ,where, null);
    }
	
	public static void reset() {
		getInstance().release();
	}
	
	@Override
	protected void release() {
		super.release();
		mInstance = null;
	}
}
