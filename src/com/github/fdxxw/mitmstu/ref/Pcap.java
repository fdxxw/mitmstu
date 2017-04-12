package com.github.fdxxw.mitmstu.ref;

import java.util.List;

public class Pcap {
	private PcapHeader header;
	private List<PcapData> data;
	public PcapHeader getHeader(){
		return header;
	}
	public void setHeader(PcapHeader header){
		this.header=header;
	}
	public List<PcapData> getData(){
		return data;
	}
	public void setData(List<PcapData> data){
		this.data=data;
	}
	
	public void ottString(){
		System.out.println("data part count="+data.size());
	}

}
