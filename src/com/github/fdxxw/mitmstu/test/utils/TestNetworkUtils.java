
/**   
 * @Title: TestNetworkUtils.java 
 * @Package: com.github.fdxxw.mitmstu.test.utils 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月17日 下午2:06:07 
 */


package com.github.fdxxw.mitmstu.test.utils;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.github.fdxxw.mitmstu.common.HttpPacket;
import com.github.fdxxw.mitmstu.service.ProxyService;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月17日 下午2:06:07 
 */

public class TestNetworkUtils extends AndroidTestCase {

    /**
     * Test method for {@link com.github.fdxxw.mitmstu.utils.NetworkUtils#getNetMaskByIp(java.lang.String)}.
     */
    @Test
    public void testGetNetMaskByIp() {
       /* InetAddress inetAddress = NetworkUtils.getInetAddress();
        int bit = NetworkUtils.getMaskBitByIp(inetAddress.getHostAddress());
        String mask = NetworkUtils.getMaskFromBit(bit);
        long int_mask = NetworkUtils.ipToint(mask);
        String maskStr = NetworkUtils.intToIp2(int_mask);
        Log.i("interface", NetworkUtils.getGateway());
        Log.i("interface", AppContext.getInt_ip() + "");
        Log.i("interface", int_mask + "");*/
    	
    	ProxyService.processLine(new HttpPacket(),"Cookie: pac_uid=0_59072071cad24; pgv_info=ssid=s1076865612; pgv_pvid=4227304432; 3g_guest_id=-8890830560016932864; g_ut=3");
        
    }

}
