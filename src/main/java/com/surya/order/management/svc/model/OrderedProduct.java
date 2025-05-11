package com.surya.order.management.svc.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderedProduct {
	
	private Long productId;
	private String name;
	private String brand;
	private Double priceAtPurchase;
	private Integer quantity;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPriceAtPurchase() {
		return priceAtPurchase;
	}
	public void setPriceAtPurchase(Double priceAtPurchase) {
		this.priceAtPurchase = priceAtPurchase;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "OrderedProduct [productId=" + productId + ", name=" + name + ", brand=" + brand + ", priceAtPurchase="
				+ priceAtPurchase + ", quantity=" + quantity + "]";
	}
	
}
