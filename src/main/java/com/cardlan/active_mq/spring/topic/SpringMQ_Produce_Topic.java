package com.cardlan.active_mq.spring.topic;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class SpringMQ_Produce_Topic
{

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        SpringMQ_Produce_Topic springMQ_produce = (SpringMQ_Produce_Topic) applicationContext.getBean("springMQ_Produce_Topic");
        springMQ_produce.jmsTemplate.send((session)-> {
            TextMessage textMessage = session.createTextMessage("spring 和Active整合的 Topic case");
            return textMessage;
        });
        System.out.println("spring 和Active 结束");
    }
}
