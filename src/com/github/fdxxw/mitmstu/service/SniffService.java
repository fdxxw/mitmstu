
/**   
 * @Title: SniffService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月9日 下午11:11:44 
 */


package com.github.fdxxw.mitmstu.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

import android.content.Intent;
import android.os.IBinder;

/** 
 * 数据嗅探服务
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月9日 下午11:11:44 
 */

public class SniffService extends BaseService {
    
	/** 开启ip转发 */
  	
    public static String[] START_IP_FORWARD = {
            "iptables -t nat -F",
            "iptables -F",
            "iptables -X",
            "iptables -P FORWARD ACCEPT"};
    
    /** 数据嗅探命令*/
      	
    private String tcpdump_cmd = null;
    
    
    /** 执行嗅探线程 */
      	
    private Thread tcpdump;
    
    
    /** 保存嗅探数据的文件名*/
      	
    public static String sniff_file_name = null;
    
    	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        
        SimpleDateFormat df = new SimpleDateFormat("ddHHmm");
        String date = df.format(new Date());
        
        sniff_file_name = "tcpdump_" + date + ".pcap";
        
        tcpdump_cmd = "tcpdump -w '" + AppContext.getmStoragePath() + "/" + sniff_file_name + "'" 
                + " host " + AppContext.getTarget().getIp();
        startSniff();
        
        return super.onStartCommand(intent, flags, startId);
    }

    	
    @Override
    public IBinder onBind(Intent arg0) {
        
        return null;
    }
    
    
    private void startSniff() {
        startArpService();

        tcpdump = new Thread() {
            @Override
            public void run() {
            	ShellUtils.execCommand(START_IP_FORWARD, true, false, true);
                ShellUtils.execCommand(tcpdump_cmd, true, false, false);
            }
        };
        tcpdump.start();

        AppContext.isSniffRunning = true;
    }
    
    
    private void stopSniff() {
        if(tcpdump != null) {
            tcpdump.interrupt();
            tcpdump = null;
        }
        new Thread() {
            @Override
            public void run() {
                ShellUtils.execCommand("killall tcpdump", true, false, true);
            }
        }.start();
        AppContext.isSniffRunning = false;
        stopArpService();
    }
    
    	
    @Override
    public void onDestroy() {
        stopSniff();
        super.onDestroy();
    }
}
