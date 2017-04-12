package com.github.fdxxw.mitmstu.ref;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PcapData {
	public static String source_adrress;
	//���ݰ�ͷ�ļ�
	public static int time_s;//ʱ������룩
	public static int time_ms;//ʱ�����΢�룩
	public  static int pLength;//ץ������
	public  static int length;//ʵ�ʳ���
	public static byte[] content;//����
	
	public String tai;//��̫��֡ 14�ֽ�  ������ֱ�Ӷ�ȡʮ�ĸ��ֽ�
	//ip��
	public String version_ihl;//ip���еİ汾�żҰ�ͷ����  1�ֽ�
	public String tos;//��������  8�ֽ�
	public Short totalLen;//�ܳ��� 2�ֽ�
	public Short identification;//������״���ݰ��ı�ʶ�� 2�ֽ�
	public Short flags_fOffset;//���Ʊ�ʾ+����ƫ�� 2�ֽ�
	public String timeToLive;//�������� 1�ֽ�
	public static String protocol;//Э������ 1�ֽ�
	private short headerChc;//ͷУ���� 2�ֽ�
	public static String source_address;//ԭ��ַ 4�ֽ�
	public static String des_address;//Ŀ�ĵ�ַ  4�ֽ�
	private String options_padding;// 4�ֽڡ�/����version_ihl�еĵ�λ����ȷ��ip��λ��
	//tcp�ṹ
	public static String source_port;//Դ�˿� 2�ֽ�
	public static String des_port;//Ŀ�Ķ˿� 2�ֽ�
	private String seq_number;// ���  ��С��ԭ�򣬸ߵ�λ4��8bit�Ĵ��˳���Ƿ��ģ�intelʹ��С��ģʽ 4�ֽ�
	private String ack_number;//ȷ�Ϻţ���С��ԭ�򣬸ߵ�λ4��8bit�Ĵ��˳���Ƿ��ģ�intelʹ��С��ģʽ 4�ֽ�
	private String info_ctrl;//Data Offset (4 bits), Reserved (6 bits), Control bits (6 bits)��intelʹ��С��ģʽ 2�ֽ�
	private String window;//���� 2�ֽ�
	private String checksum;// У��� 2�ֽ�
	private String urgent_pointer;// ����ָ�� 2�ֽ�
	//udp�ṹ
	public String usour_port;//Դ�˿� 2�ֽ�
	public String udes_port;//Ŀ�Ķ˿� 2�ֽ�
	private String ulength;//udp���� 2 �ֽ�
	private String ucrc;//udpУ���� 2�ֽ�
	
	
	public int getTime_s(){
		return time_s;
	}
	public void setTime_s(int time_s){
		this.time_s=time_s;		
	}
	public int getTime_ms(){
		return time_ms;
	}
	public void setTime_ms(int time_ms){
		this.time_ms=time_ms;
	}
	
	public int getpLength(){
		return pLength;
	}
	public void setpLength(int pLength){
		this.pLength=pLength;
	}
	public  int getLength(){
		return length;
	}
	public void setLength(int length){
		this.length=length;
	}
	public byte[] getContent(){
		return content;
	}
	
	public void setContent(byte[] content){
		this.content=content;
	}
	
	
	
	
	public String getiTai(){
		return tai;
	}
	
	public void setiTai(String tai ){
		this.tai=tai;
	}
	
	
	public String getVersion_ihl(){
		return version_ihl;
	}
	public void setVersion_ihl(String version_ihl){
		this.version_ihl=version_ihl;
	}
	public String getTos(){
		return tos;
	}
	public void setTos(String tos){
		this.tos=tos;
	}
	public short getTotalLen(){
		return totalLen;
	}
	public void setTotalLen(short totalLen){
		this.totalLen=totalLen;
	}
	public short getIdentification(){
		return identification;
	}
	public void setIdentification(short identification){
		this.identification=identification;
	}
	public short getFlags_fOffset(){
		return flags_fOffset;
	}
	public void setFlags_fOffset(short flags_fOffset){
		this.flags_fOffset=flags_fOffset;
	}
	public String getTimeToLive(){
		return timeToLive;
	}
	public void setTimeToLive(String timeToLive){
		this.timeToLive=timeToLive;
	}
	public String getProtocol(){
		return protocol;
	}
	public void setProtocol(String protocol){
		this.protocol=protocol;
	}
	public short getHeaderChc(){
		return headerChc;
	}
	public void setHeaderChc(short headerChc){
		this.headerChc=headerChc;
	}
	public String getSource_address(){
		return source_address;
	}
	public void setSource_address(String source_address){
		this.source_address=source_address;
	}
	public String getDes_adress(){
		return des_address;
	}
	public void setDes_adress(String des_address){
		this.des_address=des_address;
	}
	public String getOptions_padding(){
		return options_padding;
	}
	public void setOptions_padding(String options_padding){
		this.options_padding=options_padding;
	}

	public String getSource_port(){
		return source_port;
	}
	public void setSource_port(String source_port){
		this.source_port=source_port;
	}
	public String getDes_port(){
		return des_port;
	}
	public void  setDes_port(String des_port){
		this.des_port=des_port;
	}
	public String getSeq_number(){
		return seq_number;
	}
	public void setSeq_number(String seq_number){
		this.seq_number=seq_number;
	}
	public String getAck_number(){
		return ack_number;
	}
	public void setAck_number(String ack_number){
		this.ack_number=ack_number;
	}
	public String getInfo_ctrl(){
		return info_ctrl;
	}
	public void setInfo_ctrl(String info_ctrl){
		this.info_ctrl=info_ctrl;
	}
	public String getWindow(){
		return window;
	}
	public void setWindow(String window){
		this.window=window;
	}
	public String getChecksum(){
		return checksum;
	}
	public void setChecksum(String checksum){
		this.checksum=checksum;
	}
	public String getUrgent_pointer(){
		return urgent_pointer;
	}
	public void setUrgent_pointer(String urgent_pointer){
		this.urgent_pointer=urgent_pointer;
	}

	
	public String getUsour_port(){
		return usour_port;
	}
	public void setUsour_port(String usour_port){
		this.usour_port=usour_port;
	}
	public String getUdes_port(){
		return usour_port;
	}
	public void setUdes_port(String udes_port){
		this.udes_port=udes_port;
	}
	public String getUlength(){
		return ulength;
	}
	public void setUlength(String ulength){
		this.ulength=ulength;
	}
	public String getUcrc(){
		return ucrc;
	}
	public void setUcrc(String ucrc){
		this.ucrc=ucrc;
	}

	
	
	
	private String content2Str(byte[] content){
		char[] chars = new char[content.length];
		for(int i = 0;i < content.length; i++){
			chars[i] = (char) content[i];
		}
		return String.valueOf(chars);
	}
	
	public void ttString() throws IOException{

		 
		System.out.println("time_s="+this.time_s);
		System.out.println("\ntime_ms="+this.time_ms);
		System.out.println("\npLength="+this.pLength);
		System.out.println("\nlength="+this.length);
		System.out.println("\ncontent="+content2Str(this.content));
		System.out.println("\niTai="+this.tai);
		System.out.println("\nversion_ihl="+this.version_ihl);
		System.out.println("\ntos="+this.tos);
		System.out.println("\ntotal_length="+this.totalLen);
		System.out.println("\nidentification="+this.identification);
		System.out.println("\nflags_offset="+this.flags_fOffset);
		System.out.println("\ntimeToLive="+this.timeToLive);
		System.out.println("\nprptocol="+this.protocol);
		System.out.println("\nHeaderChecksum="+this.headerChc);
		System.out.println("\nsource_address="+this.source_address);
		System.out.println("\nDes_address="+this.des_address);
		System.out.println("\noption_padding="+this.options_padding);
		System.out.println("\nsour_port="+this.source_port);
		System.out.println("\ndes_port="+this.des_port);
		
		System.out.println("\nseq_numer="+this.seq_number);
		System.out.println("\nack_number="+this.ack_number);
		System.out.println("\ninfo_ctrl="+this.info_ctrl);
		System.out.println("\nwindow="+this.window);
		System.out.println("\nchecksum="+this.checksum);
		System.out.println("\nurgent_pointer="+this.urgent_pointer);
		
		
		System.out.println("\n##########################");
		
	}
//	private static void creatFilea() throws IOException {
//		// TODO Auto-generated method stub
////		String source_address=PcapData.source_address;
////		String source_port=PcapData.source_port;
////		String protocol=PcapData.protocol;
////		String des_address=PcapData.des_address;
////		String des_port=PcapData.des_port;
//////	
////		public static int time_ms;//ʱ�����΢�룩
////		public  static int pLength;//ץ������
////		public  static int length;//ʵ�ʳ���
////		public static byte[] content;//����
//		byte[] content=PcapData.content;
////		Int time_s=PcapData.time_s;
//		String pcapname=source_address+source_port+protocol+des_address+des_port+".pcap";
//		
//		int pcapdatalength=content.length+16;
//		char[] pcapcontent=new char[pcapdatalength];
//	
//		PrintWriter out = new PrintWriter(new BufferedWriter(  new FileWriter(pcapname)));
//		
//		out.write(pcapcontent,0,pcapdatalength);
//        out.close();
//	}

}
