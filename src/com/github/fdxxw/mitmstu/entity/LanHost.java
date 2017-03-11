
/**   
 * @Title: LanHosts.java 
 * @Package: com.github.fdxxw.mitmstu.entity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午7:52:21 
 */


package com.github.fdxxw.mitmstu.entity;

/** 
 * @Description 局域网主机
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午7:52:21 
 */

public class LanHost {
    
    private String  ip;
    
    private String mac;
    
    private String alias;
    
    private String vendor;
    
    public LanHost() {}
    
    public LanHost(String ip,String mac) {
        this.ip = ip;
        this.mac = mac;
    }
    
    public LanHost(String ip,String mac,String vendor) {
        this.ip = ip;
        this.mac = mac;
        this.vendor = vendor;
    }
    
    public LanHost(String ip,String mac,String vendor,String alias) {
        this.ip = ip;
        this.mac = mac;
        this.vendor = vendor;
        this.alias = alias;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    
    
}
