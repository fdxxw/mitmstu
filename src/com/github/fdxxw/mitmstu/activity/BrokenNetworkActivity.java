
/**   
 * @Title: BrokenNetworkActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月30日 下午1:15:17 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.service.ArpService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/** 
 * @Description 断网攻击
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月30日 下午1:15:17 
 */

public class BrokenNetworkActivity extends Activity {
    
    private TextView brokenNetInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broken_network);
        Intent intent = new Intent(BrokenNetworkActivity.this, ArpService.class);
        intent.putExtra("arpChratWay", ArpService.ONE_WAY_HOST);
        //startService(intent);
        AppContext.isBrokenNetworkRunning = true;
        brokenNetInfo = (TextView)findViewById(R.id.broken_network_info);
        LanHost target = AppContext.getTarget();
        if(target != null) {
            
            brokenNetInfo.append("被攻击者ip:" + target.getIp() + "\n被攻击者MAC:" + target.getMac());
        }
    }
}
