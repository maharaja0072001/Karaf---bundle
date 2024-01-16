package org.abc.product.service.order.impl2;

import org.abc.authentication.model.User;
import org.abc.product.dao.order.impl.OrderDAOImpl;
import org.abc.product.dao.order.OrderDAO;
import org.abc.product.model.order.Order;
import org.abc.product.service.order.OrderService;

import java.util.List;

/**
 * <p>
 * Provides the service for the Order.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {

    private static OrderService orderService;
    private static final OrderDAO ORDER_DAO = OrderDAOImpl.getInstance();

    /**
     * <p>
     * Default constructor of the OrderServiceImpl class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private OrderServiceImpl() {}

    /**
     * <p>
     * Creates a single object of OrderServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of OrderServiceImpl Class.
     */
    public static OrderService getInstance() {
        return orderService == null ? orderService = new OrderServiceImpl() : orderService;
    }

    /**
     * <p>
     * Adds the order placed by the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @param order Refers the {@link Order} to be added.
     */
    @Override
    public void addOrder(final int userId, final Order order) {
        ORDER_DAO.addOrder(userId, order);
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
        return ORDER_DAO.getOrders(userId);
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
        ORDER_DAO.cancelOrder(order);
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
        ORDER_DAO.addAddress(user, address);
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
        return ORDER_DAO.getAllAddresses(user);
    }
}
