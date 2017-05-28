package com.github.fdxxw.mitmstu.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.DataFile;

public class FileAdapter extends BaseAdapter{
	
private List<DataFile> mFileList;
	
	private Context mContext;
	
	public FileAdapter(List<DataFile> mFileList,Context ctx) {
		this.mFileList = mFileList;
		this.mContext = ctx;
	}
	@Override
	public int getCount() {
		return mFileList.size();
	}

	@Override
	public Object getItem(int position) {
		return mFileList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup viewGroup) {
		if(contentView == null) {
			contentView = View.inflate(mContext, R.layout.file_list_item, null);
		}
		TextView fileNameText = (TextView)contentView.findViewById(R.id.file_name);
		TextView saveDateText = (TextView)contentView.findViewById(R.id.save_date);
		DataFile DataFile = mFileList.get(position);
		fileNameText.setText("文件名：" + DataFile.getFileName());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		saveDateText.setText("修改日期：" + sf.format(DataFile.getSaveDate()));
		return contentView;
	}

}
