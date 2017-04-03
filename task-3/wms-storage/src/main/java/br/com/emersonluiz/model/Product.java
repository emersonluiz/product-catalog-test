package br.com.emersonluiz.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Product {

	@Id
    private String sku;
	
	private Double price;
	
	private String name;
	
	private String description;
	
	private String brand;
	
	private String product_image_url;
	
	private Double special_price;
	
	private List<String> categories;
	
	private List<String> size;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProduct_image_url() {
		return product_image_url;
	}

	public void setProduct_image_url(String product_image_url) {
		this.product_image_url = product_image_url;
	}

	public Double getSpecial_price() {
		return special_price;
	}

	public void setSpecial_price(Double special_price) {
		this.special_price = special_price;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}
}
