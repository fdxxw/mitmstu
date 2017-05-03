
/**   
 * @Title: MainActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午6:19:29 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.utils.ShellUtils;
import com.suke.widget.SwitchButton;
import com.suke.widget.SwitchButton.OnCheckedChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/** 
 * @Description 主Activity
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午6:19:29 
 */

public class MainActivity extends Activity {
    
    private Button scanLanBtn;
    
    private SwitchButton protectSwitchButton;
    
    private View historyView;
    
    private String protect_cmds = String.format("arp -s %s %s", AppContext.getGateway(), AppContext.getGatewayMac());
    
    private String close_protect_cmds = String.format("arp -d %s", AppContext.getGateway());
    
    private boolean isProtected;
    
    /**
     * Description 
     * @param savedInstanceState 
     * @see android.app.Activity#onCreate(android.os.Bundle) 
     */ 
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanLanBtn = (Button)findViewById(R.id.scan_lan_btn);
        protectSwitchButton = (SwitchButton)findViewById(R.id.protect_switch_button);
        
        historyView = findViewById(R.id.hijack_history);
        isProtected = AppContext.getBoolean("is_protected", false);
        protectSwitchButton.setChecked(isProtected);
        
        if(isProtected) {
            ShellUtils.execCommand(protect_cmds, true, false, true);
        }
        
        protectSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                AppContext.putBoolean("is_protected", isChecked);
                if (isChecked) {
                    ShellUtils.execCommand(new String[] { close_protect_cmds, protect_cmds }, true, false, true);
                } else {
                    ShellUtils.execCommand(close_protect_cmds, true, false, true);
                }
            }
        });
        scanLanBtn.setOnClickListener(new OnClickListener() {
            /**
             * Description 进入扫描局域网ui
             * @param arg0 
             * @see android.view.View.OnClickListener#onClick(android.view.View) 
             */ 
            	
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, HostsActivity.class));
            }
        });
        
        historyView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, HijackHistory.class));
			}
        	
        });
    }
}
