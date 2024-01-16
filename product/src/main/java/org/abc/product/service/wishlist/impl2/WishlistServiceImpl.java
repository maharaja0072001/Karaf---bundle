package org.abc.product.service.wishlist.impl2;

import org.abc.product.dao.wishlist.WishlistDAO;
import org.abc.product.dao.wishlist.impl.WishlistDAOImpl;
import org.abc.authentication.model.User;
import org.abc.product.model.wishlist.Wishlist;
import org.abc.product.model.product.Product;
import org.abc.product.service.wishlist.WishlistService;

/**
 * <p>
 * Provides the service for the Wishlist.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistServiceImpl implements WishlistService {

    private static WishlistServiceImpl wishlistService;
    private static final WishlistDAO WISHLIST_DAO = WishlistDAOImpl.getInstance();

    /**
     * <p>
     * Default constructor of the WishlistServiceImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private WishlistServiceImpl() {}

    /**
     * <p>
     * Creates a single object of WishlistServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of WishlistServiceImpl Class.
     */
    public static WishlistService getInstance() {
        return wishlistService == null ? wishlistService = new WishlistServiceImpl() : wishlistService;
    }

    /**
     * <p>
     * Adds the specific product to the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} to be added to the wishlist.
     * @return true if the product is added.
     */
    @Override
    public boolean addItem(final Product product, final User user) {
        return WISHLIST_DAO.addItem(product, user);
    }

    /**
     * <p>
     * Removes the specific product from the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} the product to be removed.
     */
    @Override
    public void removeItem(final Product product, final User user) {
         WISHLIST_DAO.removeItem(product, user);
    }

    /**
     * <p>
     * Gets the wishlist of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return the {@link Wishlist} of the user.
     */
    @Override
    public Wishlist getWishlist(final User user) {
        return WISHLIST_DAO.getWishlist(user);
    }
}
