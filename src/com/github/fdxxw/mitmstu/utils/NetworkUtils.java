
/**   
 * @Title: NetworkUtils.java 
 * @Package: com.github.fdxxw.mitmstu.utils 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月17日 下午12:23:03 
 */


package com.github.fdxxw.mitmstu.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import com.github.fdxxw.mitmstu.activity.HostsActivity;
import com.github.fdxxw.mitmstu.utils.ShellUtils.CommandResult;

import android.util.Log;

/** 
 * @Description 网络方面工具
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月17日 下午12:23:03 
 */

public class NetworkUtils {
    
    
      
    /** 
     * 获取网关MAC
     * @author fdxxw ucmxxw@163.com
     * @return  
     */
      	
    public static String getGatewayMac() {
         String gatewayMac = null;
         //ShellUtils.execCommand("ping -c 1 -w 0.5 " + getGateway() , true, false, true);
         new UDPThread(getGateway()).start();
         CommandResult result = ShellUtils.execCommand("arp -a " + getGateway(), false, true, true);
         String[] msgs = result.successMsg.split("\\s");
         for(String msg : msgs) {
             if(msg.trim().matches("([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}")) {
                 gatewayMac = msg;
                 break;
             }
         }
         return gatewayMac;
     }
      
    /** 
     * 获取网关地址
     * @author fdxxw ucmxxw@163.com
     * @return  
     */
      	
    public static String getGateway() {
         String gateway = null;
         try {
            String netInterface = NetworkInterface.getByInetAddress(getInetAddress()).getDisplayName();
            CommandResult result = ShellUtils.execCommand("getprop dhcp." + netInterface + ".gateway", false, true, true);
            gateway = result.successMsg;
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return gateway;
     }
     
    /** 
     * @Description ip地址转int
     * @author fdxxw ucmxxw@163.com
     * @param ipAddress
     * @return  int
     */
      	
    public static long ipToint(String ipAddress) { 
        
        long result = 0;  
          
        String[] ipAddressInArray = ipAddress.split("\\.");  
          
        for (int i = 3; i >= 0; i--) {  
          
            long ip = Long.parseLong(ipAddressInArray[3 - i]);  
          
            //left shifting 24,16,8,0 and bitwise OR  
          
            //1. 192 << 24  
            //1. 168 << 16  
            //1. 1   << 8  
            //1. 2   << 0  
            result |= ip << (i * 8);  
          
        }  
        return result;  
    }
    
     
    /** 
     * @Description int ip 转字符串
     * @author fdxxw ucmxxw@163.com
     * @param ip
     * @return  String
     */
      	
    public static String intToIp2(long ip) {  
        
        return ((ip >> 24) & 0xFF) + "."   
            + ((ip >> 16) & 0xFF) + "."   
            + ((ip >> 8) & 0xFF) + "."   
            + (ip & 0xFF);  
     } 
    
    
    /** 
     * @Description 根据掩码位数获取主机数
     * @author fdxxw ucmxxw@163.com
     * @param maskBit
     * @return  int
     */
      	
    public static int getHostCount(int maskBit) {
        return (int)(Math.pow(2, 32-maskBit)-2);
    }
    
    /** 
     * @Description   根据ip获取掩码位数 
     * @author fdxxw ucmxxw@163.com
     * @param ip
     * @return  int
     */
      	
    public static int getMaskBitByIp(String ip) {
        int maskBit = 0;
        CommandResult result = ShellUtils.execCommand("ip a", false, true, true);
        String successMsg = result.successMsg;
        int start = successMsg.lastIndexOf(ip) + ip.length() + 1;
        maskBit = Integer.parseInt(successMsg.substring(start, start + 2));
        /*Pattern pattern = Pattern.compile("(.+)" + ip + "(\\d{2})(.+)");
        Matcher matcher = pattern.matcher(successMsg);
        if(matcher.find()) {
            int_net_mask = Integer.parseInt(matcher.group(2));
        }*/
        return maskBit;
    }
    
     
    /** 
     * @Description 根据掩码位数获取掩码地址
     * @author fdxxw ucmxxw@163.com
     * @param maskBit
     * @return  String
     */
      	
    public static String getMaskFromBit(int maskBit) {
        if(maskBit > 32) 
            return null;
        
        int[] tempMask = {0,0,0,0};
        int times = maskBit/8;    //多少个8位
        int i = 0;
        for(;i<times;i++) {
            tempMask[i] = 255;
        }
        for(int j=1;j<=8;j++) {
            if(j <= maskBit-times*8) {
                tempMask[i] += (int) Math.pow(2, 8-j);
            } else {
                
                break;
            }
        }
        return Integer.toString(tempMask[0]) + "." + Integer.toString(tempMask[1]) + "." +Integer.toString(tempMask[2]) + "." + Integer.toString(tempMask[3]);
    }
    
    /**
     * byte数组Ip转成Int类型Ip
     * 
     * @param ip_byte
     * @return int
     */
    public static int byteIpToInt(byte[] ip_byte) {
        return ((ip_byte[0] & 0xff) << 24) + ((ip_byte[1] & 0xff) << 16)
                + ((ip_byte[2] & 0xff) << 8) + (ip_byte[3] & 0xff);
    }
    
    /**
     * 
     * 查找下一个IP
     * 
     * @param int_ip
     * @return
     */
    public static long nextIntIp(long int_ip) {
        int next_ip = -1;
        byte[] ip_byte = intIpToByte(int_ip);
        int i = ip_byte.length - 1;

        while (i >= 0 && ip_byte[i] == (byte) 0xff) {
            ip_byte[i] = 0;
            i--;
        }
        if (i >= 0)
            ip_byte[i]++;
        else
            return next_ip;

        next_ip = byteIpToInt(ip_byte);

        return next_ip;
    }

    /**
     * Int类型IP转成byte数组
     * 
     * @param int_ip
     * @return
     */
    public static byte[] intIpToByte(long int_ip) {
        byte[] ip_byte = new byte[4];

        ip_byte[3] = (byte) (int_ip & 0xff);
        ip_byte[2] = (byte) (0xff & int_ip >> 8);
        ip_byte[1] = (byte) (0xff & int_ip >> 16);
        ip_byte[0] = (byte) (0xff & int_ip >> 24);

        return ip_byte;
    }

     
    /** 
     * @Description 获取本机InetAddress
     * @author fdxxw ucmxxw@163.com
     * @return  InetAddress
     */
      	
    public static InetAddress getInetAddress() {
        InetAddress inetAddress = null;
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            //遍历所有网络接口
            while(en.hasMoreElements()) {
                NetworkInterface networks = en.nextElement();
             // 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> address = networks.getInetAddresses();
             // 遍历每一个接口绑定的所有ip
                while(address.hasMoreElements()) {
                    InetAddress ip = address.nextElement();
                    if(!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
                        inetAddress = ip;
                        
                    }
                }
            }
        }catch (SocketException e) {
            // TODO: handle exception
            Log.e("", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return inetAddress;
    }
    
    /**
     * String mac转byte[]
     * 
     * @param mac_string
     * @return
     */
    public static byte[] stringMacToByte(String mac_string) {
        byte[] mac_byte = new byte[6];
        if (mac_string == null)
            return mac_byte;
        String[] mac_strs = mac_string.split(":");
        if (mac_strs.length != 6) {
            mac_strs = mac_string.split("-");
        }
        for (int i = 0; i < 6; i++) {
            mac_byte[i] = Integer.valueOf(mac_strs[i], 16).byteValue();
        }
        return mac_byte;
    }
    
     
    /** 
     * @Description byte的Mac转String
     * @author fdxxw ucmxxw@163.com
     * @param byteMac
     * @return  
     */
      	
    public static String byteMacToStr(byte[] macBytes) {
        StringBuilder value = new StringBuilder();
        for(int i = 0;i < macBytes.length; i++){
            String sTemp = "";
            if((0xFF &  macBytes[i]) < 0x0F) {
                
                sTemp = "0" + Integer.toHexString(0xFF &  macBytes[i]);
            } else {
                
                sTemp = Integer.toHexString(0xFF &  macBytes[i]);
            }
         value.append(sTemp+":");
        }
          
        value.substring(0,value.lastIndexOf(":"));
        return value.toString();
    } 

    /*public static String vendorFromMac(byte[] mac) {
        if (mVendors == null) {
            try {
                mVendors = new HashMap<String, String>();
                InputStream is = AppContext.getContext().getAssets()
                        .open("nmap-mac-prefixes");
                DataInputStream in = new DataInputStream(is);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        String[] tokens = line.split(" ", 2);
                        if (tokens.length == 2)
                            mVendors.put(tokens[0], tokens[1]);
                    }
                }
                in.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mac != null && mac.length >= 3)
            return mVendors.get(String.format("%02X%02X%02X", mac[0], mac[1],
                    mac[2]));
        else
            return null;
    }*/
}
