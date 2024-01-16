package org.abc.product.dao.cart.impl;

import org.abc.product.dao.cart.CartDAO;
import org.abc.dbconnection.connection.DBConnection;
import org.abc.product.exceptions.ItemAdditionFailedException;
import org.abc.product.exceptions.ItemRemovalFailedException;
import org.abc.product.exceptions.ItemNotFoundException;
import org.abc.product.model.cart.Cart;
import org.abc.authentication.model.User;
import org.abc.product.model.product.Clothes;
import org.abc.product.model.product.Laptop;
import org.abc.product.model.product.Mobile;
import org.abc.product.model.product.Product;
import org.abc.product.ProductCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Stores all the cart details in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartDAOImpl implements CartDAO {

    private static CartDAOImpl cartDAO;

    /**
     * <p>
     * Default constructor of the CartDAOImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartDAOImpl() {}

    /**
     * <p>
     * Creates a single object of CartDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartDAOImpl Class.
     */
    public static CartDAO getInstance() {
        return cartDAO == null ? cartDAO = new CartDAOImpl() : cartDAO;
    }

    /**
     * <p>
     * Adds the product to the cart in the database.
     * </p>
     *
     * @param product Refers the {@link Product} to be added.
     * @param user Refers the current {@link User}.
     * @return true is the product is added to the cart.
     */
    @Override
    public boolean addItem(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into cart (user_id , product_id) values(?,?)")) {
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
     * Removes the product from the cart in the database.
     * </p>
     *
     * @param product Refers the {@link Product} to be removed from the cart.
     * @param user Refers the current {@link User}.
     */
    @Override
    public void removeItem(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from cart where user_id =? and product_id =?")) {
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
     * Gets the cart to the user from the database.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return {@link Cart} of the user.
     */
    @Override
    public Cart getCart(final User user) {
        final Cart cart = new Cart();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select cart.product_id, p.product_category, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand, p.quantity from cart join product p on cart.product_id=p.id  left join electronics_inventory e on cart.product_id = e.product_id left join clothes_inventory c on p.id=c.product_id where cart.user_id = ?")) {
            preparedStatement.setInt(1, user.getId());
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int productId = resultSet.getInt(1);
                final String productType = resultSet.getString(2);

                if (ProductCategory.MOBILE == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Mobile mobile = new Mobile(brand, model, price, quantity);

                    mobile.setId(productId);
                    cart.addItem(mobile);
                }

                if (ProductCategory.LAPTOP == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Laptop laptop = new Laptop(brand, model, price, quantity);

                    laptop.setId(productId);
                    cart.addItem(laptop);
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
                    cart.addItem(clothes);
                }
            }
        } catch (final SQLException exception) {
            throw new ItemNotFoundException(exception.getMessage());
        }

        return cart;
    }
}
