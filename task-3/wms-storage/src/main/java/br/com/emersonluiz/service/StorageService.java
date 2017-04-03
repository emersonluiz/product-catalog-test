package br.com.emersonluiz.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.json.simple.parser.ParseException;

import br.com.emersonluiz.model.Product;
import br.com.emersonluiz.repository.StorageRepository;
import br.com.emersonluiz.util.Transformator;

@Named
public class StorageService {
	
	@Inject
	StorageRepository storageRepository;

	public void save(String json) {
		Transformator transformator = new Transformator();
		try {
			List<Product> product = transformator.transformate(json);
			product.forEach(p -> storageRepository.save(p));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
