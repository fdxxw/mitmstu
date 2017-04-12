
/**   
 * @Title: CommonUtils.java 
 * @Package: com.github.fdxxw.mitmstu.utils 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月11日 上午9:39:04 
 */


package com.github.fdxxw.mitmstu.utils;

/** 
 * @通用工具
 * @author fdxxw ucmxxw@163.com
 * @date 2017年4月11日 上午9:39:04 
 */

public class CommonUtils {
    
    
    /**  
     * bytes转换成十六进制字符串  
     * @param byte[] b byte数组  
     * @return String 每个Byte值之间空格分隔  
     */    
    public static String byte2HexStr(byte[] b)    
    {    
        String stmp="";    
        StringBuilder sb = new StringBuilder("");    
        for (int n=0;n<b.length;n++)    
        {    
            stmp = Integer.toHexString(b[n] & 0xFF);    
            sb.append((stmp.length()==1)? "0"+stmp : stmp);    
            sb.append(" ");    
        }    
        return sb.toString().toUpperCase().trim();    
    }    
    
    
    /**   
     * 十六进制转换字符串  
     * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
     * @return String 对应的字符串  
     */      
    public static String hexStr2Str(String hexStr)    
    {      
        String str = "0123456789ABCDEF";      
        char[] hexs = hexStr.toCharArray();      
        byte[] bytes = new byte[hexStr.length() / 2];      
        int n;      
      
        for (int i = 0; i < bytes.length; i++)    
        {      
            n = str.indexOf(hexs[2 * i]) * 16;      
            n += str.indexOf(hexs[2 * i + 1]);      
            bytes[i] = (byte) (n & 0xff);      
        }      
        return new String(bytes);      
    }    
}
