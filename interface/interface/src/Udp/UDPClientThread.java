//package Udp;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//
//public class UDPClientThread extends Thread {
//    DatagramSocket socket = null;
//    public UDPClientThread(DatagramSocket socket){
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        /**
//         * 接收服务器响应
//         */
//        try {
//        byte[] data2 = new byte[1024];
//        DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
//        socket.receive(packet2);
//        String info = new String(data2,0,packet2.getLength());
//        System.out.println(info);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
