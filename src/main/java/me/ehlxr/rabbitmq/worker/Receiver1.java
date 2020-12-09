package me.ehlxr.rabbitmq.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import me.ehlxr.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work 模式：一个生产者，多个消费者，每个消费者获取到的消息唯一
 *
 * @author ehlxr
 * @since 2019-01-22.
 */
public class Receiver1 {
    private final static String QUEUE_NAME = "queue_work";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /*
         * 同一时刻服务器只会发送一条消息给消费者
         *
         * channel.basicQos: 是指通道 channel 每次能够接收的消费者最大值  https://www.rabbitmq.com/consumer-prefetch.html
         * 若将该行代码注释，则 channel 无限制，消息将很快发送完毕，只不过消息阻塞在队列中
         *
         */
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[x] Received1 '" + message + "'");

            Thread.sleep(10);
            //返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
