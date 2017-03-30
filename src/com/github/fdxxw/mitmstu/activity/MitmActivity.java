
/**   
 * @Title: MitmActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月22日 下午4:56:34 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.service.ArpService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月22日 下午4:56:34 
 */

public class MitmActivity extends Activity implements OnClickListener{
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitm);
        findViewById(R.id.mitm_broken_network).setOnClickListener(this);
    }
    
    	
    /**
     * Description 
     * @param v 
     * @see android.view.View.OnClickListener#onClick(android.view.View) 
     */ 
    	
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.mitm_broken_network : {
            if(AppContext.isBrokenNetworkRunning) {
                stopService(new Intent(MitmActivity.this, ArpService.class));
            }
            
            startActivity(new Intent(MitmActivity.this,BrokenNetworkActivity.class));
            break;
        }
        default : break;
        }
    }
}
