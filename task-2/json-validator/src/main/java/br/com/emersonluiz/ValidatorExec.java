package br.com.emersonluiz;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.validator.Validator;

public class ValidatorExec {
	
	private static final Logger LOGGER = Logger.getLogger( ValidatorExec.class.getName() );

	public static void main(String[] args) {
		Validator validator = new Validator();
        try {
        	validator.validate(new FileReader("src/main/resources/product-schema.json"), new FileReader("src/main/resources/product.json"));
		} catch (FileNotFoundException fne) {
			LOGGER.log(Level.SEVERE, fne.getMessage());
		} catch (FailureException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
        
        try {
        	validator.validate(new FileReader("src/main/resources/product-schema.json"), new FileReader("src/main/resources/products.json"));
		} catch (FileNotFoundException fne) {
			LOGGER.log(Level.SEVERE, fne.getMessage());
		} catch (FailureException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
    }
}
