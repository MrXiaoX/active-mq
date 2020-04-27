package com.cardlan.active_mq.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService=new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }
}
