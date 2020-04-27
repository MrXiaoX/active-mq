package com.cardlan.active_mq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceTopic  {

    public static final String DEFAULT_BROKER_URL="tcp://172.0.15.82:61616";
    public static final String TOPIC_NAME="topic01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接,按照指定的url地址，用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory("admin","admin",DEFAULT_BROKER_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话 session
        //两个参数 createSession(1.事务  2.签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地 queue或topic
        Topic topic = session.createTopic(TOPIC_NAME); //Collection collection=new ArrayList();
        //5.通过使用messageProducer生产消息 发送到MQ队列中
        MessageProducer messageProducer = session.createProducer(topic);
//        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//NON_PERSISTENT 非持久   PERSISTENT 持久
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("topic msg 信息 " + i);
            textMessage.setStringProperty("zl01","vip");
//            textMessage.setJMSMessageID();
//            textMessage.setText("topic msg 信息 " + i); ==  session.createTextMessage("topic msg 信息 " + i);
            //8.通过消息生产者发送到mq
            messageProducer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","mapMessage 值"+i);
            messageProducer.send(mapMessage);

        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****topic 生产者消费发送到MQ完成****");
    }
}
