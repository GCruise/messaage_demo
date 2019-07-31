package com.message.study.simple;

import org.springframework.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description 消息处理中心，负责接收消息，存储消息，转发消息
 * @Author GCruise
 * @Date 2019/7/30 16:04
 * @Version V1.0
 **/
public class Broker {

    private static final int MAX_SIZE = 3;

    /**
     * 队列，用于存储消息，消息最大个数是3
     */
    private static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(MAX_SIZE);



    /**
     * 接收消息  将接收到的消息放入消息队列中
     * @param message
     */
    public static void produce(String message){
        if(StringUtils.isEmpty(message)){
            return;
        }
        boolean result = arrayBlockingQueue.offer(message);
        if(result){
            System.out.println("成功将消息发送到消息队列中, message:" + message);
        }else {
            System.out.println("消息已满，不能再往消息队列中发送消息, message:" + message);
        }
    }

    /**
     * 消费消息  将消息从消息队列中去除
     * @return
     */
    public static String consumer(){
        String message = arrayBlockingQueue.poll();
        if(StringUtils.isEmpty(message)){
            System.out.println("拉去消息失败，消息队列中没有可供消费的消息");
        }else {
            System.out.println("拉去消息成功，成功消费消息, message:{}" + message);
        }
        return message;
    }

}
