package com.message.study.simple;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MqClient {


    /**
     * 发送消息
     * @param message
     */
    public static void produce(String message){
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println(message);
            printWriter.flush();
            System.out.println("发送消息, message:" + message);
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    public static void consume(){
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //告诉消息处理中心 自己要消费消息
            printWriter.println("consumer");
            printWriter.flush();
            //消费消息
            String message = bufferedReader.readLine();
            System.out.println("成功消费消息, message:" + message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
