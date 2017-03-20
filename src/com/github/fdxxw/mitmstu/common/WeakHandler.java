
/**   
 * @Title: WeakHandler.java 
 * @Package: com.github.fdxxw.mitmstu.common 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月20日 上午10:16:47 
 */


package com.github.fdxxw.mitmstu.common;

import java.lang.ref.WeakReference;

import android.os.Handler;

/** 
 * @Description 让Handler持有一个Activity的弱引用（WeakReference）以便不会意外导致context泄露。
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月20日 上午10:16:47 
 */

public class WeakHandler<T> extends Handler {
    private WeakReference<T> mRef;
    
    public WeakHandler(T ref) {
        mRef = new WeakReference<T>(ref);
    }
    
    public WeakReference<T> getRef() {
        return mRef;
    }
}
