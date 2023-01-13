/*
package Tcp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.wsdl.Input;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPSendAndReceive {
    public static void main(String[] args) {
        // 先开启接收端的线程
        new Thread(new Recieve()).start();
        // 再开启发送端的线程
        new Thread(new Send()).start();
    }
}
// 发送端代码
class Send implements Runnable {

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost",8999);
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = br.readLine())!=null) {
                bw.write(line);
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 接收端代码
class Recieve implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8999);
            Socket s = ss.accept();
            InputStream is = s.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            byte[] bytes = new byte[1024];
            int line = 0;
            while ((line=is.read(bytes))!=-1) {
                String string = new String(bytes,0,line);

                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
