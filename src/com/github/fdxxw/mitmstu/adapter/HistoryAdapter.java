package com.github.fdxxw.mitmstu.adapter;

import java.util.List;

import com.github.fdxxw.mitmstu.common.HttpPacket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.fdxxw.mitmstu.R;
public class HistoryAdapter extends BaseAdapter {

	private List<HttpPacket> mPacketList;
	
	private Context mContext;
	
	public HistoryAdapter(List<HttpPacket> packetList,Context ctx) {
		this.mPacketList = packetList;
		this.mContext = ctx;
	}
	@Override
	public int getCount() {
		return mPacketList.size();
	}

	@Override
	public Object getItem(int position) {
		return mPacketList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup viewGroup) {
		if(contentView == null) {
			contentView = View.inflate(mContext, R.layout.history_list_item, null);
		}
		TextView hostNameText = (TextView)contentView.findViewById(R.id.host_name);
		HttpPacket httpPacket = mPacketList.get(position);
		hostNameText.setText(httpPacket.getHost());
		return contentView;
	}

}
