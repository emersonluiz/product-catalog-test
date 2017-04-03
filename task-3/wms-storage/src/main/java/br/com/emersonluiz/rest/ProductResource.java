package br.com.emersonluiz.rest;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.service.StorageService;

@RestController
@RequestMapping("/storage")
public class ProductResource {
	
	@Inject
	StorageService storageService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Serializable> create(@RequestBody String json) {
		storageService.save(json);
		return ResponseEntity.noContent().build();
	}


}
