package br.com.emersonluiz.service;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

@Named
public class MessageService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.queue.destination}")
    String destination;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

}
