package com.abcs.huaqiaobang.ytbt.emotion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.abcs.sociax.android.R;

import java.util.List;

public class EmotionGvAdapter extends BaseAdapter {

	private Context context;
	private List<String> emotionNames;
	private int itemWidth;
	
	public EmotionGvAdapter(Context context, List<String> emotionNames, int itemWidth) {
		this.context = context;
		this.emotionNames = emotionNames;
		this.itemWidth = itemWidth;
	}
	
	@Override
	public int getCount() {
		return emotionNames.size() + 1;
	}

	@Override
	public String getItem(int position) {
		return emotionNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = new ImageView(context);
		iv.setPadding(itemWidth/8, itemWidth/8, itemWidth/8, itemWidth/8);
		LayoutParams params = new LayoutParams(itemWidth, itemWidth);
		iv.setLayoutParams(params);
		
		if(position == getCount() - 1) {
			iv.setImageResource(R.drawable.compose_emotion_delete);
		} else {
			String emotionName = emotionNames.get(position);
			iv.setImageResource(EmotionUtils.getImgByName(emotionName));
		}
		
		return iv;
	}

}
