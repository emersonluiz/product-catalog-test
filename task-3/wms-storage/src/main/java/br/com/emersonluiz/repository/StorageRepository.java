package br.com.emersonluiz.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.emersonluiz.model.Product;

@Repository
public interface StorageRepository extends MongoRepository<Product, String> {

}
