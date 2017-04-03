package br.com.emersonluiz.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.emersonluiz.model.Product;

public class Transformator {
	
	public List<Product>transformate(String json) throws ParseException {
		List<Product> products = new ArrayList<>();
		JSONParser parser = new JSONParser();
		
		//Data
		Object objData = parser.parse(json);
		
		if (objData.toString().indexOf("[") == 0) {
			JSONArray array = (JSONArray) objData;
			products = transform(array);
		} else {
			JSONObject object = (JSONObject) objData;
			Product product = transform(object);
			products.add(product);
		}
		
		return products;
	}
	
	private List<Product> transform(JSONArray data) {
		List<Product> products = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = data.iterator();
        while (iterator.hasNext()) {
        	JSONObject ob = iterator.next(); 
        	Product product = transform(ob);
        	products.add(product);
        }
        return products;
	}
	
	private Product transform(JSONObject data) {
		Product product = new Product();
		
		if (data.get("sku").toString() != null) {
			product.setSku(data.get("sku").toString());	
		}
		
		if (data.get("name").toString() != null) {
			product.setName(data.get("name").toString());
		}
		
		if (data.get("brand").toString() != null) {
			product.setBrand(data.get("brand").toString());
		}
		
		if (data.get("description").toString() != null) {
			product.setDescription(data.get("description").toString());
		}
		
		if (data.get("product_image_url").toString() != null) {
			product.setProduct_image_url(data.get("product_image_url").toString());
		}
		
		String price = data.get("price").toString();
		if (price != null) {
    		product.setPrice(Double.parseDouble(price));	
		}
		
		String specialPrice = data.get("special_price").toString();
		if (specialPrice != null) {
    		product.setSpecial_price(Double.parseDouble(specialPrice));	
		}
		
		String categories = data.get("categories").toString();
		if (categories != null) {
			List<String> list = new ArrayList<>();
			if (categories.indexOf("[") == 0) {
				JSONArray array = (JSONArray) data.get("categories");
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = array.iterator();
		        while (iterator.hasNext()) {
		        	String ob = iterator.next(); 
		        	list.add(ob);
		        }
			} else {
				list.add(categories);
			}
			
			product.setCategories(list);
		}
		
		String size = data.get("size").toString();
		if (size != null) {
			List<String> list = new ArrayList<>();
			if (size.indexOf("[") == 0) {
				JSONArray array = (JSONArray) data.get("size");
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = array.iterator();
		        while (iterator.hasNext()) {
		        	String ob = iterator.next(); 
		        	list.add(ob);
		        }
			} else {
				list.add(size);
			}
			
			product.setSize(list);
		}
		
		return product;
	}

}
