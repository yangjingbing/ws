//package Tcp;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class TcpRecieve {
//
//    private static final int port = 19865;
//
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//        Socket socket = null;
//
//        try {
//            // 建立服务器的socket并设置第一个监听端口
//            serverSocket = new ServerSocket(port);
//            while (true) {
//                try {
//                    // 建立与客户端的连接
//                    socket = serverSocket.accept();
//                } catch (IOException e) {
//                    System.out.println("与客户端的连接出现异常");
//                    e.printStackTrace();
//                }
//                ServerThread thread = new ServerThread(socket);
//                thread.start();
//            }
//
//        } catch (IOException e) {
//            System.out.println("端口被占用了");
//            e.printStackTrace();
//        } finally {
//            serverSocket.close();
//        }
//    }
//    // 服务器端线程
//    static class ServerThread extends Thread {
//        private Socket socket;
//        InputStream inputStream;
//        OutputStream outputStream;
//
//        public ServerThread(Socket socket) {
//            this.socket = socket;
//        }
//        public void run() {
//
//                try {
//                    while (true) {
//                        // 接收客户端的消息并打印
//                        System.out.println(socket);
//                        inputStream = socket.getInputStream();
//                        byte[] bytes = new byte[1024];
//                        inputStream.read(bytes);
//                        String string = new String(bytes);
//                        System.out.println(string);
//
//                        // 向客户端发送消息
//                        outputStream = socket.getOutputStream();
//                        outputStream.write("ok".getBytes());
//                        System.out.println("ok");
//                    }
//                } catch (IOException e) {
//                    System.out.println("客户端连接断开了");
////                    e.printStackTrace();
//                }
//                // 操作结束，关闭socket
//            try {
//                socket.close();
//            } catch (IOException e) {
//                System.out.println("关闭连接出现异常");
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
