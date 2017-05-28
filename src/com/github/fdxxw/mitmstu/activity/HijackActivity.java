
/**   
 * @Title: HijackActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月14日 下午9:26:19 
 */


package com.github.fdxxw.mitmstu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.service.ProxyService;
import com.suke.widget.SwitchButton;

/** 
 * 数据劫持
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月14日 下午9:26:19 
 */

public class HijackActivity extends ActionBarActivity {
    
    private SwitchButton hijackSwitchButton;
    
    private TextView hijackInfoView;
    
    private TextView hijackContentView;
    private MsgReceiver msgReceiver; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_hijack);
        setBarTitle(Html.fromHtml("<b>" + getString(R.string.hijack_name) + "</b>"));
        
      //动态注册广播接收器  
        msgReceiver = new MsgReceiver();  
        final IntentFilter intentFilter = new IntentFilter();  
        intentFilter.addAction("com.github.fdxxw.mitmstu.RECEIVER");  
        registerReceiver(msgReceiver, intentFilter); 
        
        hijackSwitchButton = (SwitchButton)findViewById(R.id.hijack_switch_button);
        hijackInfoView = (TextView)findViewById(R.id.hijack_info);
        hijackContentView = (TextView)findViewById(R.id.hijack_content);
        hijackContentView.setMovementMethod(ScrollingMovementMethod.getInstance()); 
        if(AppContext.isHijackRunning) {
            hijackSwitchButton.setChecked(true);
        } else {
            hijackSwitchButton.setChecked(false);
        }
        final LanHost target = AppContext.getTarget();
        if(target != null) {
            hijackInfoView.append("被劫持主机IP:" + target.getIp() + "\n被劫持主机MAC:" + target.getMac());
        } else {
            hijackInfoView.append("没有选中要攻击的主机");
        }
        final Intent intent = new Intent(HijackActivity.this,ProxyService.class);
        intent.putExtra("targetIp", target.getIp());
        intent.putExtra("attackerIp", AppContext.getStringIp());
        
        hijackSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked) {
                	registerReceiver(msgReceiver, intentFilter);
                    startService(intent);
                } else {
                    Toast.makeText(HijackActivity.this, 
                            "文件保存在" + AppContext.getmStoragePath() + "/"
                            + ProxyService.hijack_file_name, Toast.LENGTH_LONG).show();
                    stopService(intent);
                    //注销广播  
                    unregisterReceiver(msgReceiver);
                }
            }
        });
        
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
    
    /** 
     * 广播接收器 
     * 
     */  
    public class MsgReceiver extends BroadcastReceiver{  
  
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String message = intent.getStringExtra("message");
			hijackContentView.append("\n" + message);
		}  
          
    }  
}
