package org.abc.product.service.inventory.impl2;

import org.abc.product.ProductCategory;
import org.abc.product.dao.inventory.impl.InventoryDAOImpl;
import org.abc.product.dao.inventory.InventoryDAO;
import org.abc.product.model.product.Product;
import org.abc.product.service.inventory.InventoryService;

import java.util.List;

/**
 * <p>
 * Provides the service for the Inventory. Responsible for storing all the products.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class InventoryServiceImpl implements InventoryService {

    private static InventoryService inventoryService;
    private static final InventoryDAO INVENTORY_DAO = InventoryDAOImpl.getInstance();

    /**
     * <p>
     * Default constructor of InventoryServiceImpl class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private InventoryServiceImpl() {}

    /**
     * <p>
     * Creates a single object of InventoryServiceImpl class and returns it.
     * </p>
     *
     * @return the single instance of InventoryController class.
     */
    public static InventoryService getInstance() {
        return inventoryService == null ? inventoryService = new InventoryServiceImpl() : inventoryService;
    }

    /**
     * <p>
     * Adds the given products to the inventory.
     * </p>
     *
     * @param products Refers the {@link Product} to be added.
     */
    @Override
    public void addItem(final List<Product> products) {
        INVENTORY_DAO.addItem(products);
    }

    /**
     * <p>
     * Removes the given item from the inventory.
     * </p>
     *
     * @param product Refers the {@link Product} to be removed.
     */
    @Override
    public void removeItem(final Product product) {
        INVENTORY_DAO.removeItem(product);
    }

    /**
     * <p>
     * Gets all the products from the inventory based on the category and returns it.
     * </p>
     *
     * @return all the {@link Product} from the inventory.
     */
    @Override
    public List<Product> getItemsByCategory(final ProductCategory productCategory) {
        return INVENTORY_DAO.getItemsByCategory(productCategory);
    }
}