
/**   
 * @Title: AppContext.java 
 * @Package: com.github.fdxxw.mitmstu.common 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月16日 下午7:54:12 
 */


package com.github.fdxxw.mitmstu.common;

import java.net.InetAddress;
import java.net.NetworkInterface;

import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.utils.NetworkUtils;

import android.app.Application;
import android.content.Context;

/** 
 * @Description 全局
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月16日 下午7:54:12 
 */

public class AppContext extends Application {
    
    private Context mContext;
    
    public final static String LICENSE = "";
    
    public static String[] TOOLS_FILENAMES = {"arpspoof","tcpdump"};
    public static String[] TOOLS_COMMANDS = {"chmod 755 [ROOT_PATH]/arpspoof","chmod 755 [ROOT_PATH]/tcpdump"}; 
    
    private static InetAddress mInetAddress;
    
    private static int int_ip;
    private static int int_gateway;
    private static int int_net_mask;
    private static LanHost target = null;
    private static String gatewayMac;
    
    
    /**
     * Description 程序入口  
     * @see android.app.Application#onCreate() 
     */ 
    	
    @Override
    public void onCreate() {
        super.onCreate();
        
        mContext = this;
        
    }
    
    public void initNetInfo() {
        
        mInetAddress = NetworkUtils.getInetAddress();
        
        int_ip = NetworkUtils.byteIpToInt(mInetAddress.getAddress());
    }


    public Context getmContext() {
        return mContext;
    }


    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public static InetAddress getmInetAddress() {
        return mInetAddress;
    }


    public static void setmInetAddress(InetAddress mInetAddress) {
        AppContext.mInetAddress = mInetAddress;
    }


    public static int getInt_ip() {
        return int_ip;
    }


    public static void setInt_ip(int int_ip) {
        AppContext.int_ip = int_ip;
    }


    public static int getInt_gateway() {
        return int_gateway;
    }


    public static void setInt_gateway(int int_gateway) {
        AppContext.int_gateway = int_gateway;
    }


    public static int getInt_mac() {
        return int_net_mask;
    }


    public static void setInt_mac(int int_mac) {
        AppContext.int_net_mask = int_mac;
    }


    public static LanHost getTarget() {
        return target;
    }


    public static void setTarget(LanHost target) {
        AppContext.target = target;
    }


    public static String getGatewayMac() {
        return gatewayMac;
    }


    public static void setGatewayMac(String gatewayMac) {
        AppContext.gatewayMac = gatewayMac;
    }
    
    
    
}
