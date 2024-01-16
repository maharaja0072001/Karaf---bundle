package org.abc.product;

import org.abc.product.exceptions.ConstantNotFoundException;

/**
 * <p>
 * Provides the category of products available.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public enum ProductCategory {

    MOBILE(1), LAPTOP(2), CLOTHES(3);

    final int id;

    /**
     * <p>
     * Constructor of the enum.
     * </p>
     *
     * @param id Refers the id of the enum values
     */
    ProductCategory(final int id) {
       this.id = id;
    }

    /**
     * <p>
     * Gets the id of the enum value of returns it.
     * </p>
     *
     * @return the id of the enum value.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>
     * Gets the enum value based on id and returns it.
     * </p>
     *
     * @param id Refers the id of the enum value.
     * @return the enum value.
     */
    public static ProductCategory valueOf(int id) {
        for (final ProductCategory productCategory : values()) {
            if (productCategory.id == id) {
                return productCategory;
            }
        }
        throw new ConstantNotFoundException(String.format("Constant not found for the id: %d", id));
    }
}
