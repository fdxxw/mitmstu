package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.HttpPacket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LookHistory extends Activity {
	
	private TextView lookView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_history);
		lookView = (TextView)findViewById(R.id.lookHistory);
		HttpPacket httpPacket = AppContext.getLookHttpPacket();
		lookView.setText("\nTime: " + httpPacket.getTime());
		lookView.append("\n" + httpPacket.getHost());
		if(httpPacket.getCookie() != null) {
			
			lookView.append("\n" + httpPacket.getCookie());
		}
		lookView.append("\n" + httpPacket.getConnection());
		lookView.append("\n" + httpPacket.getAccept_encoding());
		lookView.append("\n" + httpPacket.getAccept_language());
		lookView.append("\n" + httpPacket.getAccept_charset());
		lookView.append("\nPath: " + httpPacket.getPath());
		for(String str : httpPacket.getPaths()) {
			
			lookView.append("\nPath: " + str);
		}
	}
}
