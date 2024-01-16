package org.abc.product.service.cart.impl;

import org.abc.product.model.cart.Cart;
import org.abc.authentication.model.User;
import org.abc.product.model.product.Product;
import org.abc.product.service.cart.CartService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Provides the service for the Cart of the user.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartServiceImpl implements CartService {

    private static CartService cartService;
    private static final Map<Integer, Cart> CARTS = new HashMap<>();

    /**
     * <p>
     * Default constructor of the CartServiceImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartServiceImpl() {}

    /**
     * <p>
     * Creates a single object of CartServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartServiceImpl Class.
     */
    public static CartService getInstance() {
        return cartService == null ? cartService = new CartServiceImpl() : cartService;
    }

    /**
     * <p>
     * Adds the product to the cart of the user.
     * </p>
     *
     * @param product Refers the {@link Product} to be added to the cart.
     * @param user Refers the current {@link User}.
     */
    @Override
    public boolean addItem(final Product product, final User user) {
        if (!CARTS.containsKey(user.getId())) {
             CARTS.put(user.getId(), new Cart());
        }
        final Cart cart = CARTS.get(user.getId());

        return cart.addItem(product);
    }

    /**
     * <p>
     * Removes the specific product from the cart.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers the {@link Product} to be removed from the cart.
     */
    @Override
    public void removeItem(final Product product, final User user) {
        final Cart cart = CARTS.get(user.getId());

        cart.removeItem(product);
    }

    /**
     * <p>
     * Gets the cart of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @return the {@link Cart} of the user.
     */
    @Override
    public Cart getCart(final User user) {
        return CARTS.get(user.getId());
    }
}
