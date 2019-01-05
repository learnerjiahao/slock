package com.hungry.slock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewBaseAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<HashMap<String, String>> arr;
	
	public ListViewBaseAdapter() {
		super();
	}
	
	public ListViewBaseAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arr.get(position);
	}

	public void freshCal(ArrayList<HashMap<String, String>> addMap){
		notifyDataSetChanged();
		this.arr = addMap;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	//在外面先定义，ViewHolder静态类
	static class ViewHolder
	{
	    public TextView tv_starttime;
	    public TextView tv_endtime;
	    public TextView tv_title;
	}
	//然后重写getView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if(convertView == null)
	    {
	        holder = new ViewHolder();
	        convertView = mInflater.inflate(R.layout.item, null);
	        holder.tv_starttime = (TextView)convertView.findViewById(R.id.tv_starttime);
	        holder.tv_endtime = (TextView)convertView.findViewById(R.id.tv_endtime);
	        holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	        convertView.setTag(holder);
	    }else{
	        holder = (ViewHolder)convertView.getTag();
	    } 
	    holder.tv_starttime.setText(arr.get(position).get("starttime"));
	    holder.tv_endtime.setText(arr.get(position).get("endtime"));
	    holder.tv_title.setText(arr.get(position).get("title"));
	    return convertView;
	}

}
