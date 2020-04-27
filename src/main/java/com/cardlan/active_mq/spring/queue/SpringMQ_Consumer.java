package com.cardlan.active_mq.spring.queue;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class SpringMQ_Consumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        SpringMQ_Consumer consumer = (SpringMQ_Consumer) applicationContext.getBean("springMQ_Consumer");
        String receive = (String) consumer.jmsTemplate.receiveAndConvert();
        System.out.println("spring Consumer 接收消息"+receive);
    }
}
