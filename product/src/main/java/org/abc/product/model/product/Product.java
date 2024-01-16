package org.abc.product.model.product;

import org.abc.product.ProductCategory;

/**
 * <p>
 * Represents the abstract class for the products..
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public abstract class Product {

    private int id;
    private final ProductCategory productCategory;
    private float price;
    private final String brandName;
    private int quantity;

    public Product(final ProductCategory productCategory, final float price, final String brandName, final int quantity) {
        this.productCategory = productCategory;
        this.price = price;
        this.brandName = brandName;
        this.quantity = quantity;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public float getPrice() {
        return price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void changePrice(final float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}