//package Udp;
//
//import java.net.DatagramSocket;
//import java.net.SocketException;
//
//public class UDPService {
//    public static void main(String[] args) {
//        /**
//         * 接收客户端消息
//         */
//        try {
//            while (true) {
//                DatagramSocket socket = new DatagramSocket(10086);
//                UDPServerRunnable udpThread = new UDPServerRunnable(socket);
//                udpThread.run();
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//    }
//}
