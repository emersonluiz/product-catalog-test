package br.com.emersonluiz.receiver;

import javax.inject.Inject;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;

import br.com.emersonluiz.service.StorageService;

@Controller
public class Receiver {
	
	@Inject
	StorageService storageService;

    @JmsListener(destination="mailbox")
    public void receiveMessage(String data) {
        storageService.save(data);
    }
}