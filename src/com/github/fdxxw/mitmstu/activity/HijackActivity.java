
/**   
 * @Title: HijackActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月14日 下午9:26:19 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.service.ProxyService;
import com.github.fdxxw.mitmstu.service.SniffService;
import com.suke.widget.SwitchButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/** 
 * 数据劫持
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月14日 下午9:26:19 
 */

public class HijackActivity extends Activity {
    
    private SwitchButton hijackSwitchButton;
    
    private TextView hijackInfoView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijack);
        hijackSwitchButton = (SwitchButton)findViewById(R.id.hijack_switch_button);
        hijackInfoView = (TextView)findViewById(R.id.hijack_info);
        
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
                    startService(intent);
                } else {
                    Toast.makeText(HijackActivity.this, 
                            "文件保存在" + AppContext.getmStoragePath() + "/"
                            + ProxyService.hijack_file_name, Toast.LENGTH_LONG).show();
                    stopService(intent);
                }
            }
        });
        
    }
}
