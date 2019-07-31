package com.message.study.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 通过BrokerServer与外界建立发送、消费消息的联系
 * @Author GCruise
 * @Date 2019/7/30 16:04
 * @Version V1.0
 **/
public class BrokerServer implements Runnable{

    private static final int SERVER_PORT = 9999;

    private Socket socket;

    public BrokerServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true){
                String msg = bufferedReader.readLine();
                if(msg == null){
                    continue;
                }
                System.out.println("收到消息, msg:" + msg);
                if(msg.equals("consumer")){
                    String message = Broker.consumer();
                    printWriter.println(message);
                    printWriter.flush();
                }else{
                    Broker.produce(msg);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while (true){
            BrokerServer brokerServer = new BrokerServer(serverSocket.accept());
            new Thread(brokerServer).start();
        }
    }
}
