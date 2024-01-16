package org.abc.product.dao.wishlist;

import org.abc.authentication.model.User;
import org.abc.product.model.product.Product;
import org.abc.product.model.wishlist.Wishlist;

/**
 * <p>
 * Provides service for the WishlistDAO.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface WishlistDAO {

    /**
     * <p>
     * Adds the specific product to the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} to be added to the wishlist.
     * @return true if the product is added.
     */
    boolean addItem(final Product product, final User user);

    /**
     * <p>
     * Removes the specific product from the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} the product to be removed.
     */
    void removeItem(final Product product, final User user);

    /**
     * <p>
     * Gets the wishlist of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @return the {@link Wishlist} of the user.
     */
    Wishlist getWishlist(final User user);
}