package com.ws.until;

import java.io.IOException;
import java.net.*;

/**
 * udp接收和发送数据工具类
 */
public class UdpUtil {
    private static final int localport     = 8001;//自己本地的端口
    private static final int targetPort    = 8005;//目标指定的接收端口
    private static final String targetAddr = "192.168.3.24";//目标IP地址
    private static final int byteSize      = 1024;//byte数组大小

    /**
     * 使用Upd进行发送消息
     * @param data 要发送的数据
     */
    public static void UdpSend(byte[] data){

        try {
            DatagramSocket socket = new DatagramSocket(localport);//若需要制定本地端口发送数据，则在此填入端口号
            DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName(targetAddr),targetPort);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Udp接收数据
     * @return
     */
    public static String  UdpReceive(){
        String receiveStr = null;
        try {
            DatagramSocket socket = new DatagramSocket(localport);//如果有指定的接收数据的本地端口，则填入本地端口号；没有则不用
            byte[] buf = new byte[byteSize];
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            socket.receive(packet);
            receiveStr = new String(buf,0,packet.getLength());//获取接收的数据
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  receiveStr;
    }
}
