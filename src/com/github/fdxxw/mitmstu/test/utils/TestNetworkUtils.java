
/**   
 * @Title: TestNetworkUtils.java 
 * @Package: com.github.fdxxw.mitmstu.test.utils 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月17日 下午2:06:07 
 */


package com.github.fdxxw.mitmstu.test.utils;

import java.net.InetAddress;

import org.junit.Test;

import com.github.fdxxw.mitmstu.utils.NetworkUtils;

import android.test.AndroidTestCase;
import android.util.Log;

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
        InetAddress inetAddress = NetworkUtils.getInetAddress();
        Log.i("mask", NetworkUtils.ipToint(inetAddress.getHostAddress())+ "");
        Log.i("mask", NetworkUtils.intToIp2(NetworkUtils.nextIntIp(NetworkUtils.ipToint(inetAddress.getHostAddress())))+ "");
        //fail("Not yet implemented");
    }

}
