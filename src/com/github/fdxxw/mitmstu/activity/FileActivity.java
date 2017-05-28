package com.github.fdxxw.mitmstu.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.adapter.FileAdapter;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.ConfirmDialog;
import com.github.fdxxw.mitmstu.common.ConfirmDialog.ClickListenerInterface;
import com.github.fdxxw.mitmstu.common.DataFile;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

public class FileActivity extends ActionBarActivity {
	
	private ListView fileListView;
	
	private FileAdapter fileAdapter;
	
	private List<DataFile> dataFileList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_file);
		setBarTitle(Html.fromHtml("<b>" + getString(R.string.data_file) + "</b>"));
		fileListView = (ListView)findViewById(R.id.file_listview);
		dataFileList = getDataFiles();
		fileAdapter = new FileAdapter(dataFileList, this);
		fileListView.setAdapter(fileAdapter);
		
		fileListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AppContext.setLookDataFile((DataFile)arg0.getItemAtPosition(arg2));
				startActivity(new Intent(FileActivity.this, HijackHistory.class));
			}
			
		});
		
		fileListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				final String fileName = ((DataFile)arg0.getItemAtPosition(arg2)).getFileName();
				String content = "确定要删除文件" + fileName + "吗？";
				final ConfirmDialog confirm = new ConfirmDialog(FileActivity.this, "系统提示", "确定", content, "取消");
				confirm.show();
				confirm.setClickListener(new ClickListenerInterface() {
					
					@Override
					public void doConfirm() {
						confirm.dismiss();
						ShellUtils.execCommand("rm -f " + AppContext.getmStoragePath() + "/" + fileName, true, false, true);
						dataFileList.remove(position);
						fileAdapter.notifyDataSetChanged();
					}
					
					@Override
					public void doCancel() {
						confirm.dismiss();
					}
				});
				return true;
			}
		});
	}
	
	private List<DataFile> getDataFiles() {
		List<DataFile> fileList = new ArrayList<DataFile>();
		try {
			File folder = new File(AppContext.getmStoragePath());
			File[] files = folder.listFiles();
			for(File file : files) {
				if(!file.isDirectory() && file.getName().contains(".json")) {
					DataFile dataFile = new DataFile();
					dataFile.setFileName(file.getName());
					dataFile.setSaveDate(new Date(file.lastModified()));
					fileList.add(dataFile);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}
}
