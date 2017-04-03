package br.com.emersonluiz.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.util.Validator;

@Service
public class ProductService {
	
	@Inject
    MessageService messageService;
	@Inject
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "createReliable")
	public void create(String json) throws FailureException {
		Validator validator = new Validator();
		try {
			validator.validate(new FileReader("src/main/resources/product-schema.json"), json);
			URI uri = URI.create("http://localhost:8090/storage");
			this.restTemplate.postForObject(uri, json, String.class);
		} catch (FileNotFoundException e) {
			throw new FailureException("File Product Schema was not found or have a problem!");
		}
	}
	
	public void createReliable(String json) throws FailureException {
		Validator validator = new Validator();
		try {
			validator.validate(new FileReader("src/main/resources/product-schema.json"), json);
			messageService.sendMessage(json);
		} catch (FileNotFoundException e) {
			throw new FailureException("File Product Schema was not found or have a problem!");
		}
	}

}
