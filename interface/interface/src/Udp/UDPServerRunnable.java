/*
package Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerRunnable implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket packet = null;

    public UDPServerRunnable(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //创建数据报
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data,data.length);
            System.out.println("我是服务器，等待客户端连接");
            socket.receive(packet);//把数据存到packet中
            if (socket == null) {
                wait(10);
            }
            String info = new String(data, 0, packet.getLength());
            String[] strings = info.split(" ");

            */
/**
             * 对客户端进行响应
             *//*

            //要发送的信息
            //定义客户端的地址，端口
            byte[] fasong = strings[2].getBytes();
            InetAddress address = InetAddress.getByName(strings[0]);
            int port2 = Integer.parseInt(strings[1]);
            byte[] data2 = "你好，我是服务器，连接成功".getBytes();
            System.out.println("客户端端口为："+ port2);
            DatagramPacket packet2 = new DatagramPacket(fasong,fasong.length,address,port2);
            socket.send(packet2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
*/
