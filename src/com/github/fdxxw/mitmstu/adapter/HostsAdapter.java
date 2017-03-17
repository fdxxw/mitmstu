
/**   
 * @Title: HostsAdapter.java 
 * @Package: com.github.fdxxw.mitmstu.adapter 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午8:02:54 
 */


package com.github.fdxxw.mitmstu.adapter;

import java.util.List;

import com.github.fdxxw.mitmstu.R;
import com.github.fdxxw.mitmstu.entity.LanHost;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
 * @Description 
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午8:02:54 
 */

public class HostsAdapter extends BaseAdapter {
    
    
    private List<LanHost> mLanHosts;

    private Context mContext;
    
    public HostsAdapter(List<LanHost> lanHosts,Context ctx) {
        this.mLanHosts = lanHosts;
        this.mContext = ctx;
    }
    
    /**
     * Description 
     * @return 
     * @see android.widget.Adapter#getCount() 
     */

    @Override
    public int getCount() {
        return mLanHosts.size();
    }

    /**
     * Description 
     * @param position
     * @return 
     * @see android.widget.Adapter#getItem(int) 
     */

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mLanHosts.get(position);
    }

    /**
     * Description 
     * @param position
     * @return 
     * @see android.widget.Adapter#getItemId(int) 
     */

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * Description 
     * @param position
     * @param convertView
     * @param parent
     * @return 
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup) 
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if(null == convertView) {
            convertView = View.inflate(mContext, R.layout.host_list_item, null);
        }
        TextView ipText = (TextView)convertView.findViewById(R.id.host_ip);
        TextView macText = (TextView)convertView.findViewById(R.id.host_mac);
        TextView vendorText = (TextView)convertView.findViewById(R.id.host_vendor);
        LanHost lanHost = mLanHosts.get(position);
        String alias = lanHost.getAlias();
        if(null != alias && ! alias.isEmpty()) 
           ipText.setText(alias);
        else
            ipText.setText(lanHost.getIp());
        macText.setText(lanHost.getMac());
        vendorText.setText(lanHost.getVendor());
        return convertView;
    }

}
