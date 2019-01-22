package me.ehlxr.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import me.ehlxr.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic 模式：将路由键和某模式进行匹配，此时队列需要绑定在一个模式上，“#” 匹配一个词或多个词，“*” 只匹配一个词。
 *
 * @author lixiangrong
 * @since 2019-01-22.
 */
public class Sender {
    private final static String EXCHANGE_NAME = "exchange_topic";
    private final static String EXCHANGE_TYPE = "topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

        //消息内容
        String message = "如果真爱有颜色";
        channel.basicPublish(EXCHANGE_NAME, "key.1", null, message.getBytes());
        System.out.println("[x] Sent '" + message + "'");

        //关通道 关连接
        channel.close();
        connection.close();
    }
}
