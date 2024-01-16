package org.abc.product.dao.order.impl;

import org.abc.product.OrderStatus;
import org.abc.product.PaymentMode;
import org.abc.product.ProductCategory;
import org.abc.authentication.exceptions.UpdateActionFailedException;
import org.abc.authentication.exceptions.UserNotFoundException;
import org.abc.authentication.model.User;
import org.abc.product.dao.order.OrderDAO;
import org.abc.dbconnection.connection.DBConnection;
import org.abc.product.exceptions.ItemUpdateFailedException;
import org.abc.product.exceptions.OrderAdditionFailedException;
import org.abc.product.exceptions.OrderRemovalFailedException;
import org.abc.product.exceptions.OrderNotFoundException;
import org.abc.product.model.order.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Stores all the order details in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderDAOImpl implements OrderDAO {

    private static OrderDAOImpl orderDAO;

    /**
     * <p>
     * Default constructor of the OrderDAOImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private OrderDAOImpl() {}

    /**
     * <p>
     * Creates a single object of OrderDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of OrderDAOImpl Class.
     */
    public static OrderDAO getInstance() {
        return orderDAO == null ? orderDAO = new OrderDAOImpl() : orderDAO;
    }

    /**
     * <p>
     * Adds the order of the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @param order Refers the {@link Order} to be added.
     */
    @Override
    public void addOrder(final int userId, final Order order) {
        final int productId = order.getProductId();
        final int quantity = order.getQuantity();
        final float totalAmount = order.getTotalAmount();
        final String address = order.getAddress();
        final int paymentModeId = order.getPaymentMode().getId();
        final int orderStatusId = order.getOrderStatus().getId();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into orders(user_id, product_id, address, payment_mode_id, quantity, total_amount, order_status_id) values (?,?,?,?,?,?,?) ")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, paymentModeId);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setFloat(6, totalAmount);
            preparedStatement.setInt(7, orderStatusId);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
            updateQuantity(productId, quantity);
        } catch (final SQLException exception) {
            throw new OrderAdditionFailedException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Updates the quantity of product when order is placed by the user.
     * </p>
     *
     * @param productId Refers the id of the product.
     * @param quantity Refers the quantity to be updated.
     */
    private void updateQuantity(final int productId, final int quantity) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update product set quantity = quantity - ? where id =?")) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (final SQLException exception) {
            throw new ItemUpdateFailedException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Cancels the order placed by the user.
     * </p>
     *
     * @param order Refers the {@link Order} to be cancelled.
     */
    @Override
    public void cancelOrder(final Order order) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update orders set order_status_id =? where id =?")) {
            preparedStatement.setInt(1, OrderStatus.CANCELLED.getId());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
            updateQuantity(order.getProductId(), -order.getQuantity());
        } catch (final SQLException exception) {
            throw new OrderRemovalFailedException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Gets all the orders placed by the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @return  all the {@link Order} of the user.
     */
    @Override
    public List<Order> getOrders(final int userId) {
        final List<Order> orders = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select o.id,o.product_id, o.payment_mode_id,o.quantity,o.total_amount, o.address,o.order_status_id, p.product_category_id, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand from orders o join product p on o.product_id=p.id  left join electronics_inventory e on o.product_id = e.product_id left join clothes_inventory c on o.product_id=c.product_id where o.user_id = ?")) {
            preparedStatement.setInt(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int orderId = resultSet.getInt(1);
                final int productId = resultSet.getInt(2);
                String productName = null;
                final PaymentMode paymentMode = PaymentMode.valueOf(resultSet.getString(3));
                final int quantity = resultSet.getInt(4);
                final float totalAmount = resultSet.getFloat(5);
                final String address = resultSet.getString(6);
                final OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getInt(7));
                final ProductCategory productCategory = ProductCategory.valueOf(resultSet.getInt(8));

                if (ProductCategory.MOBILE == productCategory || ProductCategory.LAPTOP == productCategory) {
                    final String brand = resultSet.getString(9);
                    final String model = resultSet.getString(10);
                    final float price = resultSet.getFloat(11);
                    productName = String.format("Product name : %s %s - Rs :%.2f", brand, model, price);
                }

                if (ProductCategory.CLOTHES == productCategory) {
                    final float price = resultSet.getFloat(11);
                    final String clothesType = resultSet.getString(12);
                    final String size = resultSet.getString(13);
                    final String gender = resultSet.getString(14);
                    final String brand = resultSet.getString(15);
                    productName = String.format("%s brand :%s size : %s gender: %s - Rs :%.2f ", clothesType, brand, size, gender, price);
                }
                final Order order = new Order.OrderBuilder(userId, productId, paymentMode).setId(orderId).setProductName(productName).setTotalAmount(totalAmount).setQuantity(quantity).setAddress(address).setOrderStatus(orderStatus).buildOrder();

                orders.add(order);
            }

            return orders;
        } catch (final SQLException exception) {
            throw new OrderNotFoundException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Gets all the addresses of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return the list of all the address.
     */
    @Override
    public List<String> getAllAddresses(final User user) {
        final List<String> addresses = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select address from address join users on users.id=address.user_id where user_id =?")) {
            preparedStatement.setInt(1, user.getId());
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final String address = resultSet.getString(1);
                addresses.add(address);
            }
        } catch (final SQLException exception) {
            throw new UserNotFoundException(exception.getMessage());
        }

        return addresses;
    }

    /**
     * <p>
     * Adds the address of the user.
     * </p>
     *
     * @param user Refers the current {@link User} .
     * @param address Refers the address to be added.
     */
    @Override
    public void addAddress(final User user, final String address) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into address(user_id, address) values (?,?)")) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, address);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (final SQLException exception) {
            throw new UpdateActionFailedException(exception.getMessage());
        }
    }
}
