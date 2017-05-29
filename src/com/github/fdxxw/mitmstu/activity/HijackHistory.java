package com.github.fdxxw.mitmstu.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.adapter.HistoryAdapter;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.DataFile;
import com.github.fdxxw.mitmstu.common.HttpPacket;
import com.github.fdxxw.mitmstu.utils.FileUtils;
public class HijackHistory extends ActionBarActivity{
	
	ListView historyListView;
	
	List<HttpPacket> packetList;
	
	HistoryAdapter historyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_hijack_history);
		setBarTitle(Html.fromHtml("<b>" + getString(R.string.data_item) + "</b>"));
		DataFile dataFile = AppContext.getLookDataFile();
		if(dataFile != null) {
			packetList = readAndParseData(dataFile.getFileName());
		}
		historyAdapter = new HistoryAdapter(packetList, this);
		
		historyListView = (ListView)findViewById(R.id.hijack_history_listview);
		historyListView.setAdapter(historyAdapter);
		
		historyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AppContext.setLookHttpPacket((HttpPacket)arg0.getItemAtPosition(arg2));
				startActivity(new Intent(HijackHistory.this,LookHistory.class));
			}
			
		});
	}
	
	private static List<HttpPacket> readAndParseData(String fileName) {
		List<HttpPacket> packetList = new ArrayList<HttpPacket>();
		try { 
			File file = new File(AppContext.getmStoragePath() + "/" + fileName);
				if(!file.isDirectory() && file.getName().contains(".json")) {
					String jsonStr = FileUtils.readData(file);
					JSONObject jsonObject = JSON.parseObject(jsonStr);
					packetList.addAll(toList(jsonObject));
				}
			//String jsonStr  = FileUtils.
		} catch (Exception e) {
			
		}
		return packetList;
	}
	
	private static List<HttpPacket> toList(JSONObject jsonObject) {  
	       HashMap<String, String> data = new HashMap<String, String>();
	       List<HttpPacket> packetList = new ArrayList<HttpPacket>();
	       // 将json字符串转换成jsonObject  
	       Iterator it = jsonObject.keySet().iterator();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext()) {
	           String key = String.valueOf(it.next());  
	           String value = (String) jsonObject.getString(key);  
	           data.put(key, value);  
	       }
	       it = data.keySet().iterator();
	       while(it.hasNext()) {
	    	   String key = (String) it.next();
	    	   String json = data.get(key);
	    	   packetList.add(toBean(json));
	       }
	       return packetList;  
	}  
	
	private static HttpPacket toBean(String json) {
		HttpPacket httpPacket = new HttpPacket();
		JSONObject jsonObj = JSON.parseObject(json);
		httpPacket.setHost(jsonObj.getString("host"));
		httpPacket.setConnection(jsonObj.getString("connection"));
		httpPacket.setAccept_encoding(jsonObj.getString("accept_encoding"));
		httpPacket.setAccept_language(jsonObj.getString("accept_language"));
		httpPacket.setAccept_charset(jsonObj.getString("accept_charset"));
		httpPacket.setCookie(jsonObj.getString("cookie"));
		httpPacket.setUser_agent(jsonObj.getString("user_agent"));
		httpPacket.setTime(jsonObj.getString("time"));
		httpPacket.setPath(jsonObj.getString("path"));
		String jsonPaths = jsonObj.getString("paths");
		
		List<Object> array = JSON.parseArray(jsonPaths);
		Set<String> paths = new HashSet<String>(); 
		for(Object o : array) {
			paths.add(o.toString());
		}
		httpPacket.setPaths(paths);
		return httpPacket;
	}
}
