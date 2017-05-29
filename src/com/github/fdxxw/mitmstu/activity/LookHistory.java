package com.github.fdxxw.mitmstu.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.HttpPacket;

public class LookHistory extends ActionBarActivity {
	
	private TextView lookView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_look_history);
		setBarTitle(Html.fromHtml("<b>" + getString(R.string.data_content) + "</b>"));
		lookView = (TextView)findViewById(R.id.lookHistory);
		HttpPacket httpPacket = AppContext.getLookHttpPacket();
		lookView.setText("\n\nTime: " + httpPacket.getTime());
		lookView.append("\n\n" + httpPacket.getHost());
		if(httpPacket.getCookie() != null) {
			
			lookView.append("\n\n" + httpPacket.getCookie());
		}
		lookView.append("\n\n" + httpPacket.getConnection());
		lookView.append("\n\n" + httpPacket.getAccept_encoding());
		lookView.append("\n\n" + httpPacket.getAccept_language());
		lookView.append("\n\n" + httpPacket.getAccept_charset());
		lookView.append("\n\nPath: " + httpPacket.getPath());
		for(String str : httpPacket.getPaths()) {
			
			lookView.append("\n\nPath: " + str);
		}
	}
}
