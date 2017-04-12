package com.github.fdxxw.mitmstu.ref;

public class PcapHeader {
	private int magic;//�ļ�ʶ��ͷ��Ϊ1a2b3c4d
	private short magor_version;//��Ҫ�汾
	private short minor_version;//��Ҫ�汾
	private int timezone;//���ر�׼ʱ��
	private int sigflags;//ʱ����ľ���
	private int snaplen;//���Ĵ洢����
	private int linktype;//��·����
	public int getMagic(){
		return magic;
	}
	public void setMagic(int magic){
		this.magic=magic;
	}
	
	public short getMagor_version(){
		return magor_version;
	}
	public void setMagor_version(short magor_version){
		this.magor_version=magor_version;
	}
	public short getMinor_version(){
		return minor_version;
	}
	public void setMinor_version(short minor_version){
		this.minor_version=minor_version;
	}
	public int getTimezone(){
		return timezone;
	}
	public void setTimezone(int timezone){
		this.timezone=timezone;
	}
	public int getSigflags(){
		return sigflags;
	}
	public void setSigflags(int sigflags){
		this.sigflags=sigflags;
	}
	
	public int getSnaplen(){
		return snaplen;
	}
	public void setSnaplen(int snaplen){
		this.snaplen=snaplen;
	}
	
	public int getLinktype(){
		return linktype;
	}
	
	public void setLinktype(int linktype){
		this.linktype=linktype;
	}
	public void otString(){
		System.out.println("magic="+"0x"+Integer.toHexString(this.magic));
		System.out.println("\nmagor_version="+this.magor_version);
		System.out.println("\nminor_version="+this.minor_version);
		System.out.println("\ntimezone="+this.timezone);
		System.out.println("\nsigflags="+this.sigflags);
		System.out.println("\nsnaplen="+this.snaplen);
		System.out.println("\nlinktype="+this.linktype);
	}
	public String toString(){
		StringBuilder sbr=new StringBuilder();
		sbr.append("magic=").append("0x"+Integer.toHexString(this.magic));
		return sbr.toString();
	}

}
