package org.abc.product;

import org.abc.product.exceptions.ConstantNotFoundException;

/**
 * <p>
 * Provides the payment modes.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public enum PaymentMode {

    CASH_ON_DELIVERY(1), CREDIT_OR_DEBIT_CARD(2), NET_BANKING(3), UPI(4);

    final int id;

    /**
     * <p>
     * Constructor of the enum.
     * </p>
     *
     * @param id Refers the id of the enum values
     */
    PaymentMode(final int id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the id of the enum value of returns it.
     * </p>
     *
     * @return the id of the enum value.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>
     * Gets the enum value based on id and returns it.
     * </p>
     *
     * @param id Refers the id of the enum value.
     * @return the enum value.
     */
    public static PaymentMode valueOf(int id) {
        for (final PaymentMode paymentMode : values()) {
            if (paymentMode.id == id) {
                return paymentMode;
            }
        }
        throw new ConstantNotFoundException(String.format("Constant not found for the id: %d", id));
    }
}
