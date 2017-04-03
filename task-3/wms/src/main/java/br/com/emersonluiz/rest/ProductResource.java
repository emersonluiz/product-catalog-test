package br.com.emersonluiz.rest;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.exception.FailureReason;
import br.com.emersonluiz.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {
	
	private ProductService productService;
	
	@Inject
	public ProductResource(ProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Serializable> create(@RequestBody String json) {
		try {
			productService.create(json);
			return ResponseEntity.noContent().build();
		} catch(FailureException fe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailureReason(fe.getMessage()));		
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro on server");
		}
	}


}
