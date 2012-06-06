package com.ad.cow.library;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ad.cow.R;

public class RatingAdapter extends ArrayAdapter {

	private LayoutInflater mInflater;
	private ArrayList items;
	private Context context;

	public RatingAdapter(Context context, int textViewResourceId, ArrayList items) {
		super(context, textViewResourceId, items);
		mInflater = LayoutInflater.from(context);
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row, parent, false);
			holder = new ViewHolder();
			//holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtLevel = (TextView) convertView.findViewById(R.id.level);
			holder.txtExp = (TextView) convertView.findViewById(R.id.exp);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Fill in the actual story info
		Rating s = (Rating) items.get(position);
		if (s.getName().length() > 35)
			holder.txtName.setText(s.getName().substring(0, 32) + "...");
		else
			holder.txtName.setText(s.getName());
		
		holder.txtLevel.setText(Integer.toString(s.getLevel()));
		holder.txtExp.setText(Float.toString(s.getExp()));

  		return convertView;
 	}

  	static class ViewHolder {
 		public TextView txtLevel;
		public TextView txtExp;
		public TextView txtName;
		ImageView image;
 		TextView title;
 		TextView detail;
 	}
 }
