
/**   
 * @Title: MainActivity.java 
 * @Package: com.github.fdxxw.mitmstu.activity 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午6:19:29 
 */


package com.github.fdxxw.mitmstu.activity;

import com.github.fdxxw.mitmstu.R;

import android.app.Activity;
import android.os.Bundle;

/** 
 * @Description 主Activity
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午6:19:29 
 */

public class MainActivity extends Activity {
    
    /**
     * Description 
     * @param savedInstanceState 
     * @see android.app.Activity#onCreate(android.os.Bundle) 
     */ 
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
