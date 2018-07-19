package com.ys.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ys.utils.ConnectionUtil;

/**
 * Create by YSOcean
 */
public class Producer {
    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception{
        //1、获取连接
        Connection connection = ConnectionUtil.getConnection("192.168.146.251",5672,"/","guest","guest");
        //2、声明信道
        Channel channel = connection.createChannel();
        //3、声明(创建)队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //4、定义消息内容(发布多条消息)
        for(int i = 0 ; i < 10 ; i++){
            String message = "hello rabbitmq "+i;
            //5、发布消息
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("[x] Sent'"+message+"'");
            //模拟发送消息延时，便于演示多个消费者竞争接受消息
            Thread.sleep(i*10);
        }
        //6、关闭通道
        channel.close();
        //7、关闭连接
        connection.close();
    }
}
