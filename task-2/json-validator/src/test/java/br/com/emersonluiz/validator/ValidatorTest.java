package br.com.emersonluiz.validator;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.validator.Validator;

public class ValidatorTest {
	
	private Validator validator;
	private FileReader schema;
	private FileReader json;
	private FileReader jsonError;
	private FileReader jsonList;
	private FileReader jsonListError;
	
	@Before
	public void start() throws FileNotFoundException {
		validator = new Validator();
		schema = new FileReader("src/main/resources/product-schema.json");
		json = new FileReader("src/main/resources/product.json");
		jsonError = new FileReader("src/main/resources/product-with-error.json");
		jsonListError = new FileReader("src/main/resources/products-with-error.json");
		jsonList = new FileReader("src/main/resources/products.json");
	}
	
	@Test
	public void validateData() throws FailureException {
		boolean rtn = validator.validate(schema, json);
		assertTrue(rtn);
	}
	
	@Test(expected=FailureException.class)
	public void validateDataWithError() throws FileNotFoundException, FailureException {
		validator.validate(schema, jsonError);
	}
	
	@Test
	public void validateDataList() throws FailureException {
		validator.validate(schema, jsonList);
	}
    
	@Test(expected=FailureException.class)
	public void validateDataListError() throws FailureException {
		validator.validate(schema, jsonListError);
	}
}
