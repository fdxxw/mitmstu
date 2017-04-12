
/**   
 * @Title: ProxyService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月11日 下午3:15:17 
 */


package com.github.fdxxw.mitmstu.service;

import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.utils.NetworkUtils;

import android.content.Intent;

/** 
 * 代理服务，路由转发
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月11日 下午3:15:17 
 */

public class ProxyService extends BaseService {
    
    
    /** 端口转发命令 */
      	
    public final static String[] PORT_REDIRECT_CMD = {
            "iptables -t nat -F",
            "iptables -F",
            "iptables -t nat -I POSTROUTING -s 0/0 -j MASQUERADE",
            "iptables -P FORWARD ACCEPT",
            "iptables -t nat -A PREROUTING -j DNAT -p tcp --dport 80 --to "
                    + AppContext.getStringIp() + ":" + 8080};
    
    
    /** 关闭端口转发命令*/
      	
    public final static String[] UN_PORT_REDIRECT_CMD = {
            "iptables -t nat -F",
            "iptables -F",
            "iptables -t nat -I POSTROUTING -s 0/0 -j MASQUERADE",
            "iptables -t nat -D PREROUTING -j DNAT -p tcp --dport 80 --to "
                    + AppContext.getStringIp() + ":" + 8080};
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        return super.onStartCommand(intent, flags, startId);
    }
}
