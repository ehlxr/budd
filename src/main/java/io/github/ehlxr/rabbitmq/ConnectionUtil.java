/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ehlxr
 * @since 2019-01-22.
 */
public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 连接 5672 端口；注意 15672 为工具界面端口；25672 为集群端口
        factory.setPort(5672);
        /*
         * 当我们在创建用户时，会指定用户能访问一个虚拟机，并且该用户只能访问该虚拟机下的队列和交换机，如果没有指定，默认的是”/”;
         * 一个 rabbitmq 服务器上可以运行多个 vhost，以便于适用不同的业务需要，
         * 这样做既可以满足权限配置的要求，也可以避免不同业务之间队列、交换机的命名冲突问题，因为不同 vhost 之间是隔离的。
         */
        factory.setVirtualHost("/tdd");
        factory.setUsername("ehlxr");
        factory.setPassword("123456");

        //获取连接
        return factory.newConnection();
    }
}