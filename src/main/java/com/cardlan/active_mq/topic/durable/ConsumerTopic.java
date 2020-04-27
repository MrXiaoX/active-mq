package com.cardlan.active_mq.topic.durable;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class ConsumerTopic {

    public static final String DEFAULT_BROKER_URL="tcp://172.0.15.82:61616";
    public static final String TOPIC_NAME="durable-topic";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("持久化 durable topic 消费者 ***");

        //1.创建连接,按照指定的url地址，用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory("admin","admin",DEFAULT_BROKER_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("zl1");
        //3.创建会话 session
        //两个参数 1.事务  2.签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地 queue或topic
        Topic topic = session.createTopic(TOPIC_NAME); //Collection collection=new ArrayList();
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark");
        connection.start();
        //5.创建消费者
        Message message = topicSubscriber.receive();
        while (null!=message )
        {
            TextMessage textMessage= (TextMessage) message;
            System.out.println(" 持久化 topic 消费者接受消息 "+textMessage.getText());
            message= topicSubscriber.receive(5000L);
        }
        session.close();
        connection.close();

        /**
         *
         * 1.一定要先运行一次消费者，等于向MQ注册，订阅生产者
         * 2. 然后再运行生产者发送信息，
         * 3.此时无论消费者是否在线，都会接受到消息，不在线，就会等待下次连接，把没有接受的消息接收下来
         *
         */

    }
}
