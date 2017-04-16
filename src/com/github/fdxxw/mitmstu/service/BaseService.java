
/**   
 * @Title: BaseService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月9日 下午11:17:44 
 */


package com.github.fdxxw.mitmstu.service;

import com.github.fdxxw.mitmstu.common.AppContext;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/** 
 * 基础服务
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月9日 下午11:17:44 
 */

public class BaseService extends Service {

    
    
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
     
    /** 
     * 停止Arp欺骗服务
     * @author fdxxw ucmxxw@163.com  
     */
      	
    protected void stopArpService() {
        if(!AppContext.isBrokenNetworkRunning && !AppContext.isSniffRunning && !AppContext.isHijackRunning) {
            stopService(new Intent(this,ArpService.class));
        }
    }
    
     
    /** 
     * 启动Arp欺骗服务
     * @author fdxxw ucmxxw@163.com  
     */
      	
    protected void startArpService() {
        if(!AppContext.isBrokenNetworkRunning && !AppContext.isSniffRunning && !AppContext.isHijackRunning) {
            Intent intent = new Intent(this,ArpService.class);
            intent.putExtra("arpCheatWay", ArpService.ONE_WAY_HOST);
            intent.putExtra("ipForward", true);   //开启ip转发
            startService(intent);
        }
    }

}
