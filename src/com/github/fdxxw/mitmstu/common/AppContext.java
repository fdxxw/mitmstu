
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
import java.net.SocketException;

import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.utils.NetworkUtils;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/** 
 * @Description 全局
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月16日 下午7:54:12 
 */

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月10日 上午11:40:32 
 */ 
  	
public class AppContext extends Application {
    
    private static SharedPreferences preferences = null;
    
    private Context mContext;
    
    public final static String LICENSE = "";
    
    public static String[] TOOLS_FILENAMES = {"arpspoof","tcpdump"};
    public static String[] TOOLS_COMMANDS = {"chmod 755 [ROOT_PATH]/arpspoof","chmod 755 [ROOT_PATH]/tcpdump"}; 
    
    private static InetAddress mInetAddress;
    
    private static long int_ip;
    private static long int_gateway;
    private static long int_net_mask;
    private static String mac;
    private static LanHost target = null;
    private static String gatewayMac;
    
    
    /** 存储路径，sdcard*/
      	
    private static String mStoragePath; 
    
    
    /** 断网攻击服务是否在运行，默认不运行 */
      	
    public static boolean isBrokenNetworkRunning = false;
    
    
    /** 数据嗅探服务是否在运行，默认不运行 */
      	
    public static boolean isSniffRunning = false;
    
    
    /** nat转发是否在运行，默认不运行*/
      	
    public static boolean isHijackRunning = false;
    
    /** 
     * 网卡接口名 ,example:eth0
     */  	
    private static String netInterface;
    
    private static HttpPacket lookHttpPacket;
    
    private static DataFile lookDataFile;
    
    /**
     * Description 程序入口  
     * @see android.app.Application#onCreate() 
     */ 
    	
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        mContext = this;
        mStoragePath = Environment.getExternalStorageDirectory().toString();
        String setPermisson_cmd = "chmod 777 " + mStoragePath;
        ShellUtils.execCommand(setPermisson_cmd, true, false, true);
        initNetInfo();
    }
    
    public void initNetInfo() {
        mInetAddress = NetworkUtils.getInetAddress();
        if(mInetAddress != null) {
        	int_ip = NetworkUtils.ipToint(mInetAddress.getHostAddress());
        	int_net_mask = NetworkUtils.ipToint(NetworkUtils.getMaskFromBit(NetworkUtils.getMaskBitByIp(mInetAddress.getHostAddress())));
        	int_gateway = NetworkUtils.ipToint(NetworkUtils.getGateway());
        	gatewayMac = NetworkUtils.getGatewayMac();
        	try {
        		netInterface = NetworkInterface.getByInetAddress(mInetAddress).getDisplayName();
        		mac = NetworkUtils.byteMacToStr(NetworkInterface.getByInetAddress(mInetAddress).getHardwareAddress());
        	} catch (SocketException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
        
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


    public static long getInt_ip() {
        return int_ip;
    }


    public static void setInt_ip(int int_ip) {
        AppContext.int_ip = int_ip;
    }
    
    public static String getStringIp() {
        return NetworkUtils.intToIp2(int_ip);
    }

    public static long getInt_gateway() {
        return int_gateway;
    }


    public static void setInt_gateway(int int_gateway) {
        AppContext.int_gateway = int_gateway;
    }


    public static long getInt_net_mask() {
        return int_net_mask;
    }


    public static void setInt_net_mask(int int_mac) {
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
    
    public static int getHostCount() {
        return NetworkUtils.getHostCount(NetworkUtils.getMaskBitByIp(mInetAddress.getHostAddress()));
    }

    public static String getNetInterface() {
        return netInterface;
    }

    public static void setNetInterface(String netInterface) {
        AppContext.netInterface = netInterface;
    }

    public static String getMac() {
        return mac;
    }

    public static void setMac(String mac) {
        AppContext.mac = mac;
    }
    
    public static String getGateway() {
        return NetworkUtils.intToIp2(int_gateway);
    }

    public static String getmStoragePath() {
        return mStoragePath;
    }

    public static void setmStoragePath(String mStoragePath) {
        AppContext.mStoragePath = mStoragePath;
    }
    
    public static void putString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public static Boolean getBoolean(String key, Boolean value) {
        return preferences.getBoolean(key, false);
    }

	public static HttpPacket getLookHttpPacket() {
		return lookHttpPacket;
	}

	public static void setLookHttpPacket(HttpPacket lookHttpPacket) {
		AppContext.lookHttpPacket = lookHttpPacket;
	}

	public static DataFile getLookDataFile() {
		return lookDataFile;
	}

	public static void setLookDataFile(DataFile lookDataFile) {
		AppContext.lookDataFile = lookDataFile;
	}
    
}
