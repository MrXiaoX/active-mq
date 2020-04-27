package com.cardlan.active_mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

//    public static final String DEFAULT_BROKER_URL="tcp://172.0.15.82:61616";
    public static final String DEFAULT_BROKER_URL="tcp://127.0.0.1:61616";
    public static final String QUEUE="queue01";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("消费者1 ***");

        //1.创建连接,按照指定的url地址，用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory("admin","admin",DEFAULT_BROKER_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话 session
        //两个参数 1.事务  2.签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地 queue或topic
        Queue queue = session.createQueue(QUEUE); //Collection collection=new ArrayList();
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        // 一、receive()方法
        while (true)
        {
            //receive() 一直等待  receive(4000L)4秒 过后结束消费
            TextMessage textMessage= (TextMessage) messageConsumer.receive(4000L);
            if(textMessage!=null)
            {
                System.out.println("*****生产者消费接受到MQ完成****"+textMessage.getText());
            }else{
                break;
            }
        }

        /**
         * 异步非阻塞方式（监听器onMessage()）
         * 订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
         * 当消息到达之后，系统会自动调用监听器MessageListener的onMessage(Message message)方法。
         */
//        messageConsumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                if(null!=message&& message instanceof TextMessage){
//                    TextMessage textMessage= (TextMessage) message;
//                    try {
//                        System.out.println(" MessageListener 消费者接受消息 "+textMessage.getText());
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        System.in.read();

        messageConsumer.close();
        session.close();
        connection.close();

        /**
         * 1. 先生产 只启动 1号消费者  问题：1号消费者能消费吗? yes
         *
         * 2. 先生产 先启动 1号消费者，再启动2好消费者  问题：2号消费者能消费吗? 1号 yes  2号 no
         *
         * 3.先启动 1号消费者和2号消费者，再生产6条消息  问题：消费者能消费吗?
         * 一、 2个消费者都有6条
         * 二、 先到先得
         * 三、 一人一半  yes
         *
         *
         *
         */
    }
}
