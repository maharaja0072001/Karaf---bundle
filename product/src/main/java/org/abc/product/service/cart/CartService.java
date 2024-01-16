package org.abc.product.service.cart;

import org.abc.product.model.cart.Cart;
import org.abc.authentication.model.User;
import org.abc.product.model.product.Product;

/**
 * <p>
 * Provides service for the Cart.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface CartService {

    /**
     * <p>
     * Gets the cart of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @return {@link Cart} of the user.
     */
    Cart getCart(final User user);

    /**
     * <p>
     * Adds the specific product to the cart.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers the {@link Product} to be added to the cart.
     * @return true if the product is added.
     */
    boolean addItem(final Product product, final User user);

    /**
     * <p>
     * Removes the specific product from the cart.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers the {@link Product} to be removed from the cart.
     */
    void removeItem(final Product product, final User user);
}
