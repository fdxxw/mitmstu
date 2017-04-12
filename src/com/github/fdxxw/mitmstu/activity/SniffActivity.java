
/**   
 * @Title: SniffActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月6日 下午7:22:41 
 */


package com.github.fdxxw.mitmstu.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

import com.alibaba.fastjson.JSON;
import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.ref.Pcap;
import com.github.fdxxw.mitmstu.ref.PcapData;
import com.github.fdxxw.mitmstu.ref.PcapParser;
import com.github.fdxxw.mitmstu.service.SniffService;
import com.github.fdxxw.mitmstu.utils.CommonUtils;
import com.suke.widget.SwitchButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月6日 下午7:22:41 
 */

public class SniffActivity extends Activity {
    
    private SwitchButton sniffSwitchButton;
    
    private TextView sniffInfoView;
    
    /**
     * @param savedInstanceState 
     * @see android.app.Activity#onCreate(android.os.Bundle) 
     */ 
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sniff);
        sniffInfoView = (TextView)findViewById(R.id.sniff_info); 
        sniffSwitchButton = (SwitchButton)findViewById(R.id.sniff_switch_button);
        
        if(AppContext.isBrokenNetworkRunning) {
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
