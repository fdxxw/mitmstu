
/**   
 * @Title: SniffActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月6日 下午7:22:41 
 */


package com.github.fdxxw.mitmstu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.service.SniffService;
import com.suke.widget.SwitchButton;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月6日 下午7:22:41 
 */

public class SniffActivity extends ActionBarActivity {
    
    private SwitchButton sniffSwitchButton;
    
    private TextView sniffInfoView;
    
    /**
     * @param savedInstanceState 
     * @see android.app.Activity#onCreate(android.os.Bundle) 
     */ 
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_sniff);
        setBarTitle(Html.fromHtml("<b>" + getString(R.string.sniff_name) + "</b>"));
        sniffInfoView = (TextView)findViewById(R.id.sniff_info); 
        sniffSwitchButton = (SwitchButton)findViewById(R.id.sniff_switch_button);
        
        if(AppContext.isSniffRunning) {
            sniffSwitchButton.setChecked(true);
        } else {
            sniffSwitchButton.setChecked(false);
        }
        
        LanHost target = AppContext.getTarget();
        
        if(target != null) {
            sniffInfoView.append("被嗅探者IP:" + target.getIp() + "\n被嗅探者MAC:" + target.getMac());
        }
        
        sniffSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked) {
                    startService(new Intent(SniffActivity.this,SniffService.class));
                } else {
                    Toast.makeText(SniffActivity.this, 
                            "文件保存在" + AppContext.getmStoragePath() + "/"
                            + SniffService.sniff_file_name, Toast.LENGTH_LONG).show();
                    stopService(new Intent(SniffActivity.this,SniffService.class));
                    
                    printContent();
                }
            }
        });
    }
    
    public void printContent() {
        
        try {
            
            /*File file = new File(AppContext.getmStoragePath() + "/" + SniffService.sniff_file_name);
            InputStream is = new FileInputStream(file);
            final Pcap pcap = PcapParser.unpack(is);
            String content = null;
            for(PcapData data : pcap.getData()) {
                content = CommonUtils.hexStr2Str(CommonUtils.byte2HexStr(data.getContent()));
                Log.d("pcap", content);
            }*/
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
