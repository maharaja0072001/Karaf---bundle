package org.abc.product.controller.cart;

import org.abc.product.model.cart.Cart;
import org.abc.product.service.cart.CartService;
import org.abc.product.service.cart.impl2.CartServiceImpl;
import org.abc.authentication.model.User;
import org.abc.product.model.product.Product;

/**
 * <p>
 * Interacts between CartView and CartService for adding , removing from the cart of the user.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartController {

    private static CartController cartController;
    private static final CartService CART_SERVICE = CartServiceImpl.getInstance();

    /**
     * <p>
     * Default constructor of the CartController class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartController() {}

    /**
     * <p>
     * Creates a single object of CartController Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartController Class.
     */
    public static CartController getInstance() {
        return cartController == null ? cartController = new CartController() : cartController;
    }

    /**
     * <p>
     * Adds the product to the cart of the specified user.
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     */
    public boolean addItem(final Product product, final User user) {
        return CART_SERVICE.addItem(product, user);
    }

    /**
     * <p>
     * Removes the product from the cart of the specified user.
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     */
    public void removeItem(final Product product, final User user) {
        CART_SERVICE.removeItem(product, user);
    }

    /**
     * <p>
     * Gets the cart of the specified user id and returns it.
     * </p>
     *
     * @param user Refers the user who owns the cart.
     * @return the cart of the user.
     */
    public Cart getCart(final User user) {
        return CART_SERVICE.getCart(user);
    }
}



