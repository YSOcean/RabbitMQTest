package com.ys.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ys.utils.ConnectionUtil;

/**
 * Create by YSOcean
 */
public class Producer {
    private final static String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws Exception {
        //1、获取连接
        Connection connection = ConnectionUtil.getConnection("192.168.146.251", 5672, "/", "guest", "guest");
        //2、声明信道
        Channel channel = connection.createChannel();
        //3、声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //4、创建消息
        String message = "hello rabbitmq";
        //5、发布消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("[x] Sent'" + message + "'");
        //6、关闭通道
        channel.close();
        //7、关闭连接
        connection.close();
    }
}
