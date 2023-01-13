package com.ws.util;
 
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
 
 
public class UdpDemo {
 
	public static void main(String[] args) throws Exception {
		//输出本机的主机名和IP地址
		System.out.println(InetAddress.getLocalHost());

	}
 
}
 
class UdpSend{
	public static void main(String[] args) throws Exception{
		//通过DatagramSocket对象创建UDP服务
		DatagramSocket ds= new DatagramSocket();
		//确定数据，并封装成数据包
		byte[] buf="hello world".getBytes();
		DatagramPacket dp = new DatagramPacket(buf,0,buf.length,InetAddress.getByName("192.168.86.1"),10000);
		//通过socket服务，将已有的数据包发送出去
		ds.send(dp);
		//关闭资源
		ds.close();
	}
}
 
class UdpReceive{
	public static void main(String[] args) throws Exception{
		//通过DatagramSocket对象创建UDP服务
		DatagramSocket ds= new DatagramSocket(10000);
		//定义数据包用于存储数据
		byte[] buf= new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf,0,buf.length);
		//通过服务的receive方法将收到的数据存入数据包中
		ds.receive(dp);
		//通过数据包中的方法获取其中的数据
		int port=dp.getPort();
		String receiveData=new String(dp.getData(),0,dp.getLength());
		System.out.println("port:"+port+"   data:"+receiveData);
		//关闭资源
		ds.close();
	}
}