package me.ehlxr.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import me.ehlxr.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式：一个生产者，一个消费者
 *
 * @author ehlxr
 * @since 2019-01-22.
 */

public class Sender {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();


        /*
         * 声明队列
         *  1. 队列名
         *  2. 是否持久化
         *  3. 是否排外（即只允许该channel访问该队列   一般等于true的话用于一个队列只能有一个消费者来消费的场景）
         *  4. 是否自动删除（消费完删除）
         *  6. 其他属性
         *
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /*
         * 消息内容
         *  1. 交换机
         *  2. 队列名
         *  3. 其他属性（路由）
         *  4. 消息body
         */
        String message = "错的不是我，是这个世界~";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x]Sent '" + message + "'");

        //最后关闭通关和连接
        channel.close();
        connection.close();
    }

}
