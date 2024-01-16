package org.abc.product.model.order;

import org.abc.product.OrderStatus;
import org.abc.product.PaymentMode;

/**
 * <p>
 * Represents a order placed by the user and contains all the order related information.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class Order {

    private final int id;
    private final int userId;
    private final int productId;
    private final int quantity;
    private final float totalAmount;
    private final String address;
    private final PaymentMode paymentMode;
    private final String productName;
    private OrderStatus orderStatus;

    private Order(final OrderBuilder orderBuilder) {
        this.productId = orderBuilder.productId;
        this.userId = orderBuilder.userId;
        this.address = orderBuilder.address;
        this.quantity = orderBuilder.quantity;
        this.productName = orderBuilder.productName;
        this.totalAmount = orderBuilder.totalAmount;
        this.paymentMode = orderBuilder.paymentMode;
        this.id = orderBuilder.id;
        this.orderStatus = orderBuilder.orderStatus;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public String getProductName() {
        return productName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return String.format("Order id : %d\n%s\nproduct quantity : %d\ntotal amount : %.2f\nPayment mode : %s\nShipping address : %s\nStatus : %s",
                id, productName, quantity, totalAmount, paymentMode, address, orderStatus.toString());
    }

    public static class OrderBuilder {

        private int id;
        private final int userId;
        private final int productId;
        private int quantity;
        private float totalAmount;
        private String address;
        private final PaymentMode paymentMode;
        private String productName;
        private OrderStatus orderStatus;

        public OrderBuilder(final int userId, final int productId, final PaymentMode paymentMode) {
           this.userId = userId;
           this.productId = productId;
           this.paymentMode = paymentMode;
        }

        public OrderBuilder setAddress(final String address) {
            this.address = address;

            return this;
        }

        public OrderBuilder setId(final int id) {
            this.id = id;

            return this;
        }

        public OrderBuilder setTotalAmount(final float totalAmount) {
            this.totalAmount = totalAmount;

            return this;
        }

        public OrderBuilder setQuantity(final int quantity) {
            this.quantity = quantity;

            return this;
        }

        public OrderBuilder setProductName(final String productName) {
            this.productName = productName;

            return this;
        }

        public OrderBuilder setOrderStatus(final OrderStatus orderStatus) {
            this.orderStatus = orderStatus;

            return this;
        }

        public Order buildOrder() {
            return new Order(this) ;
        }
    }
}
