package com.github.fdxxw.mitmstu.ref;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PcapParser {
	
	
	private static char[] byteArrayTochar(byte[] b,int offset){
		char value;
		
		 Charset cs = Charset.forName ("UTF-8");
	      ByteBuffer bb = ByteBuffer.allocate (b.length);
	      bb.put (b);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
		
	}
	private static String byteArrayToString(byte[] b,int offset){
		String s = b.toString();
		return s;
	}
	
	private static void reverseByteArray(byte[] arr){
		byte temp;
		int n=arr.length;
		for(int i=0;i<n/2;i++){
			temp=arr[i];
			arr[i]=arr[n-1-i];
			arr[n-1-i]=temp;
		}
	}
	
	private static int byteArrayToInt(byte[] b,int offset){
		int value=0;
		for(int i=0;i<4;i++){
			int shift=(4-1-i)*8;
			value+=(b[i+offset]&0x000000FF)<<shift;
		}
		return value;
	}
	private static short byteArrayToShort(byte[]b,int offset){
		short value=0;
		for(int i=0;i<2;i++){
			int shift=(2-1-i)*8;
			value+=(b[i+offset]&0x000000FF)<<shift;
		}

		return value;
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	    FileInputStream fis=new FileInputStream("src/heheda.pcap");
	//    byte[] buffer_14=new byte[14];
		byte[] buffer_4=new byte[4];
		byte[] buffer_2=new byte[2];
//		byte[] buffer_1=new byte[1];
		PcapHeader header=new PcapHeader();
		Pcap pcap=new Pcap();
		int m=fis.read(buffer_4);
		if(m!=4){
			return;
		}
		reverseByteArray(buffer_4);
		header.setMagic(byteArrayToInt(buffer_4, 0));
		m=fis.read(buffer_2);
		reverseByteArray(buffer_2);
		header.setMagor_version(byteArrayToShort(buffer_2,0));
		
		m=fis.read(buffer_2);
		reverseByteArray(buffer_2);
		header.setMinor_version(byteArrayToShort(buffer_2,0));
		
		m=fis.read(buffer_4);
    	reverseByteArray(buffer_4);
		header.setTimezone(byteArrayToInt(buffer_4,0));
		
		m=fis.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setSigflags(byteArrayToInt(buffer_4,0));
		m=fis.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setSnaplen(byteArrayToInt(buffer_4,0));
		m=fis.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setLinktype(byteArrayToInt(buffer_4,0));
		
		header.otString();
		pcap.setHeader(header);
		List<PcapData> dataList=new ArrayList<PcapData>();
		while (m>0){
			PcapData data=new PcapData();
			m=fis.read(buffer_4);
			if(m<0){
				break;
			}
			reverseByteArray(buffer_4);
			data.setTime_s(byteArrayToInt(buffer_4,0));
			
			m=fis.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setTime_ms(byteArrayToInt(buffer_4,0));
			
			m=fis.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setpLength(byteArrayToInt(buffer_4,0));
			
			m=fis.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setLength(byteArrayToInt(buffer_4,0));			
			
	
			byte[] content=new byte[data.getpLength()];
		    m=fis.read(content);
			data.setContent(content);
			
			byte[] ti=new byte[14];
			for(int i=0;i<14;i++){
			ti[i]=content[i];}
			data.setiTai(ti.toString());
			
			byte[] ver_ihla=new byte[1];
//			 StringBuffer sbb = new StringBuffer();
			for(int i=0;i<1;i++){
			int b=i+14;
			ver_ihla[0]=content[b];
//			sbb.append((int)(content[b]&0xff));
//            sbb.append(".");
			}
//			  sbb.deleteCharAt(sbb.length() - 1);
//				data.setVersion_ihl(sbb.toString());
			data.setVersion_ihl((short)ver_ihla[0] + "");
			
			byte[] tosa=new byte[1];
			for(int i=0;i<1;i++){
			int b=i+15;
			tosa[i]=content[b];
			}
			data.setTos(String.valueOf(tosa));
			
			byte[] totall=new byte[2];
			for(int i=0;i<2;i++){
				int b=i+16;
				totall[i]=content[b];
			}
			reverseByteArray(totall);
			data.setTotalLen((byteArrayToShort(totall,0)));
			
			byte[] ident=new byte[2];
			for(int i=0;i<2;i++){
				int b=i+18;
				ident[i]=content[b];
			}
			data.setIdentification(byteArrayToShort(ident,0));
			
			byte[] ff=new byte[2];
			for(int i=0;i<2;i++){
				int b=i+20;
				ff[i]=content[b];
			}
			data.setFlags_fOffset(byteArrayToShort(ff,0));
			
			byte[] tt=new byte[1];
			for(int i=0;i<1;i++){
				int b=i+22;
				tt[i]=content[b];
			}
			data.setTimeToLive(String.valueOf(tt));
			
			byte[] pro=new byte[1];
			for(int i=0;i<1;i++){
				int b=i+23;
				pro[i]=content[b];
			}
			
			data.setProtocol((short)pro[0] + "");
			
			byte[] hc=new byte[2];
			for(int i=0;i<2;i++){
				int b=i+24;
				hc[i]=content[b];
			}
			data.setTimeToLive(String.valueOf(tt));
			
			
			byte[] sou=new byte[4];
            StringBuffer sbr = new StringBuffer();
			for(int i=0;i<4;i++){
				int b = i + 26;
				sbr.append((int)(content[b]&0xff));
                sbr.append(".");
			}
            sbr.deleteCharAt(sbr.length() - 1);
			data.setSource_address(sbr.toString());
			
			byte[] des=new byte[4];
			StringBuffer sba=new StringBuffer();
			for(int i=0;i<4;i++){
				int b=i+30;
				sba.append((int)(content[b]&0xff));
                sba.append(".");
			}
			  sba.deleteCharAt(sba.length() - 1);
			data.setDes_adress(sba.toString());
			
			if((short)ver_ihla[0]==69){
				
				System.out.println("�ǲ���ɵ�ϣ�˭֪����");
				
				
				
				if((short)pro[0]==6){
					//tcp Э�����
					byte[] soure_portt=new byte[2];
					StringBuffer sbd=new StringBuffer();
					for(int i=0;i<2;i++){
						int b=i+34;
						sbd.append((int)(content[b]&0xff));		
					}
					  sbd.deleteCharAt(sbd.length() - 1);
						data.setSource_port(sbd.toString());
						
						byte[] des_portt=new byte[2];
						StringBuffer sbe=new StringBuffer();
						for(int i=0;i<2;i++){
							int b=i+36;
							sbe.append((int)(content[b]&0xff));		
						}
						  sbe.deleteCharAt(sbe.length() - 1);
							data.setDes_port(sbe.toString());
						
							byte[] seq_numbert=new byte[4];
							StringBuffer sbf=new StringBuffer();
							for(int i=0;i<4;i++){
								int b=i+38;
								sbf.append((int)(content[b]&0xff));		
							}
							  sbf.deleteCharAt(sbf.length() - 1);
								data.setSeq_number(sbf.toString());
								
								byte[] ack_numbert=new byte[4];
								StringBuffer sbg=new StringBuffer();
								for(int i=0;i<4;i++){
									int b=i+42;
									sbg.append((int)(content[b]&0xff));		
								}
								  sbg.deleteCharAt(sbg.length() - 1);
									data.setAck_number(sbg.toString());
									
									byte[] info_ctrlt=new byte[2];
									StringBuffer sbh=new StringBuffer();
									for(int i=0;i<2;i++){
										int b=i+46;
										sbh.append((int)(content[b]&0xff));		
									}
									  sbh.deleteCharAt(sbh.length() - 1);
										data.setInfo_ctrl(sbh.toString());
										
										byte[] windowt=new byte[2];
										StringBuffer sbi=new StringBuffer();
										for(int i=0;i<2;i++){
											int b=i+48;
											sbi.append((int)(content[b]&0xff));		
										}
										  sbi.deleteCharAt(sbi.length() - 1);
											data.setWindow(sbi.toString());
										
											byte[] checksumt=new byte[2];
											StringBuffer sbj=new StringBuffer();
											for(int i=0;i<2;i++){
												int b=i+50;
												sbj.append((int)(content[b]&0xff));		
											}
											  sbj.deleteCharAt(sbj.length() - 1);
												data.setChecksum(sbj.toString());
											
												byte[] urgent_pointert=new byte[2];
												StringBuffer sbk=new StringBuffer();
												for(int i=0;i<2;i++){
													int b=i+52;
													sbk.append((int)(content[b]&0xff));		
												}
												  sbk.deleteCharAt(sbk.length() - 1);
													data.setUrgent_pointer(sbk.toString());
													//��Ԫ�� ԭip��ַ Դ�˿� Ŀ��ip��ַ Ŀ�Ķ˿� �����Э��	
													//String pcapname=soure_port".pcap"
													System.out.println("���ǲ��Ǵ廻��");
//						int pcapdlength=16+content.length;							
//						byte[] pcapdataall=new byte[pcapdlength];
//						String pcapdataname=soure_adress+soure_port+protocol+des_adress+des_port;
//						
													creatFiles();					
													
								
				}
				else if((short)pro[0]==7){
					//udpЭ�����
					System.out.println("���ǲ��Ƕ��û�");

					byte[] usour_portu=new byte[2];
					StringBuffer sbl=new StringBuffer();
					for(int i=0;i<2;i++){
						int b=i+34;
						sbl.append((int)(content[b]&0xff));		
					}
					  sbl.deleteCharAt(sbl.length() - 1);
						data.setUsour_port(sbl.toString());
						
						byte[] udes_portu=new byte[2];
						StringBuffer sbm=new StringBuffer();
						for(int i=0;i<2;i++){
							int b=i+36;
							sbm.append((int)(content[b]&0xff));		
						}
						  sbm.deleteCharAt(sbm.length() - 1);
							data.setUdes_port(sbm.toString());
							
							byte[] ulengthu=new byte[2];
							StringBuffer sbn=new StringBuffer();
							for(int i=0;i<2;i++){
								int b=i+38;
								sbn.append((int)(content[b]&0xff));		
							}
							  sbn.deleteCharAt(sbn.length() - 1);
								data.setUlength(sbn.toString());
						
								byte[] ucrcu=new byte[2];
								StringBuffer sbo=new StringBuffer();
								for(int i=0;i<2;i++){
									int b=i+38;
									sbo.append((int)(content[b]&0xff));		
								}
								  sbo.deleteCharAt(sbo.length() - 1);
									data.setUcrc(sbo.toString());

					
				}
			}
			if((short)ver_ihla[0]!=69){
				byte[] opd=new byte[4];
				StringBuffer sbc=new StringBuffer();
				for(int i=0;i<4;i++){
					int b=i+34;
					sbc.append((int)(content[b]&0xff));
	                sbc.append(".");
				}
				  sbc.deleteCharAt(sbc.length() - 1);
				data.setOptions_padding(sbc.toString());
				if((short)pro[0]==6){
					
				}
				else if((short)pro[0]==7){
					
				}
			}
			
		
			
			FileOutputStream fos=new FileOutputStream("first.pacp");
			for(int i=0;i<34;i++){
			fos.write(content[i]);}
			fos.close();
			
			dataList.add(data);
			data.ttString();
			}
		pcap.setData(dataList);
		pcap.ottString();
		System.out.println("�㻹��ɵ��");
		fis.close();
		System.out.println("�㲻��ɵ�ϣ�");
	}
	private static void creatFiles() throws IOException {
		// TODO Auto-generated method stub
		String source_address=PcapData.source_address;
		String source_port=PcapData.source_port;
		String protocol=PcapData.protocol;
		String des_address=PcapData.des_address;
		String des_port=PcapData.des_port;
		PcapData data=new PcapData();

		String pcapname=source_address+source_port+protocol+des_address+des_port+".pcap";
		
		int pcapdatalength=data.length;
		char[] pcapcontent=new char[pcapdatalength];
	
		PrintWriter out = new PrintWriter(new BufferedWriter(  new FileWriter(pcapname)));
		
		out.write(pcapcontent,0,pcapdatalength);
        out.close();
	}
	
}