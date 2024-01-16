package org.abc.product.model.product;

import org.abc.product.ProductCategory;

/**
 * <p>
 * Represents the model for clothes.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class Clothes extends Product {

    private final String gender;
    private final String size;
    private final String clothesType;

    public Clothes(final String clothesType, final String gender,
                   final String size, final float price, final String brandName, final int quantity) {
        super(ProductCategory.CLOTHES, price, brandName, quantity);
        this.gender = gender;
        this.size = size;
        this.clothesType = clothesType;
    }

    public String getClothesType() {
        return clothesType;
    }

    public String getGender() {
        return gender;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("%s - %s : %s : %s : %.2f", clothesType, super.getBrandName(), gender, size, super.getPrice());
    }
}
