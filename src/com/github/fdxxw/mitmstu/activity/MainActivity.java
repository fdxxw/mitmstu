
/**   
 * @Title: MainActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午6:19:29 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/** 
 * @Description 主Activity
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午6:19:29 
 */

public class MainActivity extends Activity {
    
    private Button scanLanBtn;
    
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
    }
}
