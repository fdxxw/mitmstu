
/**   
 * @Title: HostsActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月20日 上午10:22:52 
 */


package com.github.fdxxw.mitmstu.activity;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.adapter.HostsAdapter;
import com.github.fdxxw.mitmstu.common.AppContext;
import com.github.fdxxw.mitmstu.common.WeakHandler;
import com.github.fdxxw.mitmstu.entity.LanHost;
import com.github.fdxxw.mitmstu.utils.NetworkUtils;
import com.github.fdxxw.mitmstu.utils.ShellUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月20日 上午10:22:52 
 */

public class HostsActivity extends Activity {
    
    private final static int DATA_CHANGED = 1;     //arp缓存改变
    private final static int DATA_HOST_ALIAS_CHANGED = 2;    //alias改变
    
    private static final Pattern ARP_TABLE_PARSER = Pattern
            .compile("^([\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3})\\s+([0-9-a-fx]+)\\s+([0-9-a-fx]+)\\s+([a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2})\\s+([^\\s]+)\\s+(.+)$",
                    Pattern.CASE_INSENSITIVE);
    private static boolean stop = true;   //停止扫描
    
    private Thread arpReader = null;   //读取arp缓存表
    
    private Thread scanThread = null;   //扫描局域网
    
    private ListView hostListView;
    
    private HostsAdapter hostAdapter;
    
    private List<LanHost> mHosts;
    
    private List<LanHost> mCheckHosts;   //检查重复
    
    private HostHandler mHandler = new HostHandler(this);
    
    private ScheduledThreadPoolExecutor stpe = null;
    
    private String netInterface = null;
    
    /**
     * Description 
     * @param savedInstanceState 
     * @see android.app.Activity#onCreate(android.os.Bundle) 
     */ 
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosts);
        netInterface = AppContext.getNetInterface();
        hostListView = (ListView)findViewById(R.id.host_listview);
        mCheckHosts = new ArrayList<LanHost>();
        mHosts = new ArrayList<LanHost>();
        
        hostAdapter = new HostsAdapter(mHosts, this);
        
        hostListView.setAdapter(hostAdapter);
        startScan();
        hostListView.setOnItemClickListener(new OnItemClickListener() {
            /**
             * Description 
             * @param parent
             * @param view
             * @param position
             * @param id
             * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long) 
             */ 
            	
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LanHost target = (LanHost)parent.getItemAtPosition(position);
                AppContext.setTarget(target);
                startActivity(new Intent(HostsActivity.this, MitmActivity.class));
            }
        });
        
        mHosts.clear();
        LanHost myself = new LanHost(NetworkUtils.intToIp2(AppContext.getInt_ip()), AppContext.getMac());
        mHosts.add(myself);
        
    }
    
    public static class HostHandler extends WeakHandler<HostsActivity> {

        
        /**
         * Creates a new instance of HostHandler.
         * Description 
         * @param ref
         */
        	
        public HostHandler(HostsActivity ref) {
            super(ref);
        }
        
        /**
         * Description 
         * @param msg 
         * @see android.os.Handler#handleMessage(android.os.Message) 
         */ 
        	
        @Override
        public void handleMessage(Message msg) {
            HostsActivity activity = getRef().get();
            if (activity != null) {
                if (msg.what == DATA_CHANGED) {
                    activity.mHosts.add((LanHost) msg.obj);
                    activity.hostAdapter.notifyDataSetChanged();
                    //activity.headerText.setText(String.format(activity.getString(R.string.found_lan_hosts), activity.mHosts.size()));
                } else if (msg.what == DATA_HOST_ALIAS_CHANGED) {
                    int i = msg.arg1;
                    LanHost host = activity.mHosts.get(i);
                    host.setAlias((String) msg.obj);
                    activity.hostAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    
    /** 
     * 读取arp缓存表 每隔三秒一次
     * @author fdxxw ucmxxw@163.com
     * @date 2017年3月20日 下午12:42:53 
     */ 
      	
    class ArpReaderThread extends Thread {
        ExecutorService executor;

        public void run() {
            if (executor != null && !executor.isShutdown()) {
                executor.shutdownNow();
                executor = null;
            }
            executor = Executors.newFixedThreadPool(5);

            RandomAccessFile fileReader = null;
            try {
                fileReader = new RandomAccessFile("/proc/net/arp", "r");
                StringBuilder sb = new StringBuilder();
                int len = -1;
                String line = null;
                Matcher matcher = null;

                synchronized (HostsActivity.class) {
                    while (!stop) {
                        fileReader.seek(0);
                        while (!stop && (len = fileReader.read()) >= 0) {
                            sb.append((char) len);
                            if (len != '\n')
                                continue;
                            line = sb.toString();
                            sb.setLength(0);
    
                            if ((matcher = ARP_TABLE_PARSER.matcher(line)) != null && matcher.find()) {
                                String address = matcher.group(1), flags = matcher.group(3), hwaddr = matcher.group(4), device = matcher.group(6);
                                if (device.equals(netInterface) && !hwaddr.equals("00:00:00:00:00:00") && flags.contains("2")) {
    
    
                                        boolean contains = false;
    
                                        for (LanHost h : mCheckHosts) {
                                            if (h.getMac().equals(hwaddr) || h.getIp().equals(address)) {
                                                contains = true;
                                                break;
                                            }
                                        }
                                        if (!contains) {
                                            byte[] mac_bytes = NetworkUtils.stringMacToByte(hwaddr);
                                            //String vendor = NetworkUtils.vendorFromMac(mac_bytes);
                                            LanHost host = new LanHost(address, hwaddr, "");
                                            mCheckHosts.add(host);
                                            mHandler.obtainMessage(DATA_CHANGED, host).sendToTarget();
                                            //executor.execute(new RecvThread(address));
                                        }
                                    }
                                }
                            }
                        }
                        Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileReader != null)
                        fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (executor != null)
                    executor.shutdownNow();
            }
        }
    }
    
    /** 
     * 多线程按照IP地址递增扫描 ping扫描
     * @author fdxxw ucmxxw@163.com
     * @date 2017年3月20日 下午12:57:15 
     */ 
      	
    class ScanThread extends Thread {
        ExecutorService executor;
        final static String ping = "ping -c 1 -w 0.5";   //ping 命令  -c发送次数  -w等待时间
        @Override
        public void run() {
            if(null != executor && !executor.isShutdown()) {
                executor.shutdown();
                executor = null;
            }
            executor = Executors.newFixedThreadPool(10);
            long next_int_ip = 0;
            try {
                next_int_ip = AppContext.getInt_net_mask() & AppContext.getInt_gateway();
                for(int i=0;i<AppContext.getHostCount();i++) {
                    next_int_ip = NetworkUtils.nextIntIp(next_int_ip);
                    if(next_int_ip != -1) {
                        String ip = NetworkUtils.intToIp2(next_int_ip);
                        //ShellUtils.execCommand(ping + " " + ip, false, false, true);
                        executor.execute(new UDPThread(ip));
                        //Thread.sleep(500);
                    }
                } 
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 发送udp数据包
     * @author fdxxw ucmxxw@163.com
     * 
     */
    class UDPThread extends Thread {
    	String target_ip;

		public UDPThread(String target_ip) {
			this.target_ip = target_ip;
		}

		public void run() {
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket();
				// Log.d(TAG, target_ip);
				InetAddress address = InetAddress.getByName(target_ip);
				DatagramPacket packet = new DatagramPacket("hello".getBytes(), "hello".length(), address, 137);
				socket.setSoTimeout(200);
				socket.send(packet);
				socket.close();
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			} finally {
				if (socket != null)
					socket.close();
			}
		}
    }
    
    private void startScan() {
        stop = false;
        if(null != scanThread && !scanThread.isAlive()) {
            scanThread.interrupt();
            scanThread = null;
        }
        scanThread = new ScanThread();
        scanThread.start();
       /* if(null != stpe && !stpe.isShutdown()) {
            stpe.shutdownNow();
            stpe = null;
        }*/
        if(null != arpReader && !arpReader.isAlive()) {
            arpReader.interrupt();
            arpReader = null;
        }
        arpReader = new ArpReaderThread();
        arpReader.start();
       /* stpe = new ScheduledThreadPoolExecutor(5);
        stpe.scheduleWithFixedDelay(arpReader, 5, 5, TimeUnit.SECONDS);  //每隔五秒执行一次
       */ 
    }
    
    private void stopScan() {
        stop = true;
        if(null != arpReader && !arpReader.isAlive()) {
            arpReader.interrupt();
            arpReader = null;
        }
        if(null != stpe && !stpe.isShutdown()) {
            stpe.shutdownNow();
            stpe = null;
        }
        
        if(null != scanThread && !scanThread.isAlive()) {
            scanThread.interrupt();
            scanThread = null;
        }
        
    }
    
    /**
     * Description  
     * @see android.app.Activity#onBackPressed() 
     */ 
    	
    @Override
    public void onBackPressed() {
        stopScan();
        finish();
        super.onBackPressed();
    }
    
    /**
     * Description  
     * @see android.app.Activity#onResume() 
     */ 
    	
    @Override
    protected void onResume() {
        startScan();
        super.onResume();
    }
    
    /**
     * Description  
     * @see android.app.Activity#onPause() 
     */ 
    	
    @Override
    protected void onPause() {
        stopScan();
        super.onPause();
    }
}
