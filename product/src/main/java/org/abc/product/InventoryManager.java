package org.abc.product;

import org.abc.product.controller.inventory.InventoryController;
import org.abc.dbconnection.exceptions.FileUnavailableException;
import org.abc.product.model.product.Clothes;
import org.abc.product.model.product.Laptop;
import org.abc.product.model.product.Mobile;
import org.abc.product.model.product.Product;
import org.abc.product.view.cart.CartView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * Creates all products and adds them to the inventory.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class InventoryManager {

    private static InventoryManager inventoryManager;
    private static final Logger LOGGER = LogManager.getLogger(CartView.class);

    /**
     * <p>
     * Default constructor of InventoryManager class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private InventoryManager() {}

    /**
     * <p>
     * Creates a single object of InventoryManager class and returns it.
     * </p>
     *
     * @return the single instance of InventoryManager class.
     */
    public static InventoryManager getInstance() {
        return inventoryManager == null ? inventoryManager = new InventoryManager() : inventoryManager;
    }

    /**
     * <p>
     * Creates all the products and add them to the inventory.
     * </p>
     */
    public void addAllItems() {
        final List<Product> allProducts = new ArrayList<>();

        loadProducts(allProducts, String.join("",System.getenv("INVENTORY_PATH"), "/mobiles.properties"), ProductCategory.MOBILE);
        loadProducts(allProducts, String.join("",System.getenv("INVENTORY_PATH"), "/laptops.properties"), ProductCategory.LAPTOP);
        loadProducts(allProducts, String.join("",System.getenv("INVENTORY_PATH"), "/clothes.properties"), ProductCategory.CLOTHES);
        InventoryController.getInstance().addItemToInventory(allProducts);
    }

    /**
     * <p>
     * Loads product data from properties files and add all the products into a list.
     * </p>
     *
     * @param allProducts Refers the collection of product.
     * @param filePath Refers the file path of properties file.
     * @param productCategory Refers the product category.
     */
    private void loadProducts(final List<Product> allProducts, final String filePath, final ProductCategory productCategory) {
        try (final FileReader fileReader = new FileReader(filePath)) {
            final Properties properties = new Properties();

            properties.load(fileReader);

            for (final Object key : properties.keySet()) {
                final String value = properties.getProperty((String) key);

                switch (productCategory) {
                    case MOBILE:
                        allProducts.add(createMobile(value));
                        break;
                    case LAPTOP:
                        allProducts.add(createLaptop(value));
                        break;
                    case CLOTHES:
                        allProducts.add(createClothes(value));
                        break;
                }
            }
        } catch (final IOException exception) {
            LOGGER.error("Failed Loading properties file or File not found");
            throw new FileUnavailableException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Creates a Mobiles object from a string of comma separated values.
     * </p>
     *
     * @param input Refers the string of comma separated values.
     * @return the instance of {@link Mobile}.
     */
    private Mobile createMobile(final String input) {
        final String[] attributes = input.split(",");

        return new Mobile(attributes[0], attributes[1], Float.parseFloat(attributes[2]), Integer.parseInt(attributes[3]));
    }

    /**
     * <p>
     * Creates a Laptop object from a string of comma separated values.
     * </p>
     *
     * @param input Refers the string of comma separated values.
     * @return the instance of {@link Laptop}.
     */
    private Laptop createLaptop(final String input) {
        final String[] attributes = input.split(",");

        return new Laptop(attributes[0], attributes[1], Float.parseFloat(attributes[2]), Integer.parseInt(attributes[3]));
    }

    /**
     * <p>
     * Creates a Clothes object from a string of comma separated values.
     * </p>
     *
     * @param input Refers the string of comma separated values.
     * @return the instance of {@link Clothes}.
     */
    private Clothes createClothes(final String input) {
        final String[] attributes = input.split(",");

        return new Clothes(attributes[0], attributes[1], attributes[2], Float.parseFloat(attributes[3]), attributes[4], Integer.parseInt(attributes[5]));
    }
}
