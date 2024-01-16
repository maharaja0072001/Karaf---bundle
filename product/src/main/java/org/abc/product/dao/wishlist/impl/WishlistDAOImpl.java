package org.abc.product.dao.wishlist.impl;

import org.abc.product.dao.wishlist.WishlistDAO;
import org.abc.dbconnection.connection.DBConnection;
import org.abc.product.ProductCategory;
import org.abc.authentication.model.User;
import org.abc.product.exceptions.ItemAdditionFailedException;
import org.abc.product.exceptions.ItemNotFoundException;
import org.abc.product.exceptions.ItemRemovalFailedException;
import org.abc.product.model.wishlist.Wishlist;
import org.abc.product.model.product.Clothes;
import org.abc.product.model.product.Laptop;
import org.abc.product.model.product.Mobile;
import org.abc.product.model.product.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Provides DAO for wishlist.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistDAOImpl implements WishlistDAO {

    private static WishlistDAOImpl wishlistDAO;

    /**
     * <p>
     * Default constructor of the WishlistDAOImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private WishlistDAOImpl() {}

    /**
     * <p>
     * Creates a single object of WishlistDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of WishlistDAOImpl Class.
     */
    public static WishlistDAO getInstance() {
        return wishlistDAO == null ? wishlistDAO = new WishlistDAOImpl() : wishlistDAO;
    }

    /**
     * <p>
     * Adds the product to the wishlist of the specified user.
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     * @return the wishlist of the user.
     */
    @Override
    public boolean addItem(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into wishlist (user_id , product_id) values(?,?)")) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, product.getId());
            final int updatedRows = preparedStatement.executeUpdate();

            DBConnection.getConnection().commit();

            return  updatedRows > 0;
        } catch (final SQLException exception) {
            throw new ItemAdditionFailedException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Removes the product from the wishlist of the specified user.
     * * </p>
     *
     * @param product Refers the product to be removed.
     * @param user Refers the user
     */
    @Override
    public void removeItem(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from wishlist where user_id =? and product_id =?")) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (final SQLException exception) {
            throw new ItemRemovalFailedException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Gets the wishlist of the specified user id and returns it.
     * </p>
     *
     * @param user Refers the user who owns the cart.
     * @return the wishlist of the user.
     */
    @Override
    public Wishlist getWishlist(final User user) {
        final Wishlist wishlist = new Wishlist();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select w.product_id, p.product_category, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand ,p.quantity from wishlist w join product p on w.product_id=p.id left join electronics_inventory e on w.product_id = e.product_id left join clothes_inventory c on p.id=c.product_id where w.user_id = ?")) {
            preparedStatement.setInt(1, user.getId());
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int productId = resultSet.getInt(1);
                final String productType = resultSet.getString(2);

                if (ProductCategory.MOBILE.name().equals(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Mobile mobile = new Mobile(brand, model, price, quantity);

                    mobile.setId(productId);
                    wishlist.addItem(mobile);
                }

                if (ProductCategory.LAPTOP == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Laptop laptop = new Laptop(brand, model, price, quantity);

                    laptop.setId(productId);
                    wishlist.addItem(laptop);
                }

                if (ProductCategory.CLOTHES == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(9);
                    final String clothesType = resultSet.getString(6);
                    final String size = resultSet.getString(7);
                    final String gender = resultSet.getString(8);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Clothes clothes = new Clothes(clothesType, gender, size, price, brand, quantity);

                    clothes.setId(productId);
                    wishlist.addItem(clothes);
                }
            }

            return wishlist;
        } catch (final SQLException exception) {
            throw new ItemNotFoundException(exception.getMessage());
        }
    }
}
