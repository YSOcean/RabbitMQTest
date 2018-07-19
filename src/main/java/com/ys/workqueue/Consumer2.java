package com.ys.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.ys.utils.ConnectionUtil;


/**
 * Create by YSOcean
 */
public class Consumer2 {

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception{
        //1、获取连接
        Connection connection = ConnectionUtil.getConnection("192.168.146.251",5672,"/","guest","guest");
        //2、声明通道
        Channel channel = connection.createChannel();
        //3、声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //同一时刻服务器只会发送一条消息给消费者
        //channel.basicQos(1);

        //4、定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //5、监听队列，手动返回完成状态
        channel.basicConsume(QUEUE_NAME,false,queueingConsumer);
        //6、获取消息
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //消费者2接收一条消息后休眠1000毫秒
            Thread.sleep(1000);
            //返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }

}
