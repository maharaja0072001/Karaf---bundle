package org.abc.product.dao.inventory.impl;

import org.abc.product.ProductCategory;
import org.abc.product.dao.inventory.InventoryDAO;
import org.abc.dbconnection.connection.DBConnection;
import org.abc.product.exceptions.ItemAdditionFailedException;
import org.abc.product.exceptions.ItemNotFoundException;
import org.abc.product.exceptions.ItemRemovalFailedException;
import org.abc.product.model.product.Product;
import org.abc.product.model.product.Clothes;
import org.abc.product.model.product.Laptop;
import org.abc.product.model.product.Mobile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Stores all the products in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class InventoryDAOImpl implements InventoryDAO {

    private static InventoryDAOImpl inventoryDAO;

    /**
     * <p>
     * Default constructor of the InventoryController class. Kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private InventoryDAOImpl() {}

    /**
     * <p>
     * Creates a single object of InventoryController Class and returns it.
     * </p>
     *
     * @return returns the single instance of InventoryController Class.
     */
    public static InventoryDAO getInstance() {
        return inventoryDAO == null ? inventoryDAO = new InventoryDAOImpl() : inventoryDAO;
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
        int productId;

        for (final Product product : products) {
            try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into product (product_category_id, price, quantity) values(?,?,?) returning id")) {
                preparedStatement.setInt(1, product.getProductCategory().getId());
                preparedStatement.setFloat(2, product.getPrice());
                preparedStatement.setFloat(3, product.getQuantity());

                final ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                productId = resultSet.getInt(1) ;
            } catch (SQLException exception) {
                throw new ItemAdditionFailedException(exception.getMessage());
            }
            String query = null ;

            switch (product.getProductCategory()) {
                case MOBILE:
                case LAPTOP:
                    query = "insert into electronics_inventory(product_id, brand, model) values(?, ?, ?)" ;
                    break;
                case CLOTHES:
                    query = "insert into clothes_inventory(product_id, brand, clothes_type, gender, size) values(?, ?, ?, ?, ?)";
                    break;
            }

            try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, productId);
                preparedStatement.setString(2, product.getBrandName());

                switch (product.getProductCategory()) {
                    case MOBILE:
                        preparedStatement.setString(3, ((Mobile) product).getModel());
                        break;
                    case LAPTOP:
                        preparedStatement.setString(3, ((Laptop) product).getModel());
                        break;
                    case CLOTHES:
                        preparedStatement.setString(3, ((Clothes) product).getClothesType());
                        preparedStatement.setString(4, ((Clothes) product).getGender());
                        preparedStatement.setString(5, ((Clothes) product).getSize());
                }
                preparedStatement.executeUpdate();
                DBConnection.getConnection().commit();
            } catch (final SQLException exception) {
                try {
                    DBConnection.getConnection().rollback();
                } catch (final SQLException e) {
                    throw new ItemAdditionFailedException(e.getMessage());
                }
            }
        }
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
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from product where id = ?")) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            throw new ItemRemovalFailedException(exception.getMessage());
        }
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
        switch (productCategory) {
            case MOBILE:
                return getMobileItems();
            case LAPTOP:
                return getLaptopItems();
            case CLOTHES:
                return getClothesItems();
        }

        return null;
    }

    /**
     * <p>
     * Gets all the products from the mobile inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the mobile inventory.
     */
    private List<Product> getMobileItems() {
        final List<Product> mobileCollection = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select p.id, e.brand, e.model, p.price,p.quantity from electronics_inventory e join product p on p.id = e.product_id where p.product_category='MOBILE'")) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int productId = resultSet.getInt(1);
                final String brand = resultSet.getString(2);
                final String model = resultSet.getString(3);
                final float price = resultSet.getFloat(4);
                final int quantity = resultSet.getInt(5);
                final Mobile mobile = new Mobile(brand, model, price, quantity);

                mobile.setId(productId);
                mobileCollection.add(mobile);
            }
        } catch (final SQLException exception) {
            throw new ItemNotFoundException(exception.getMessage());
        }

        return mobileCollection;
    }

    /**
     * <p>
     * Gets all the products from the laptop inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the laptop inventory.
     */
    private List<Product> getLaptopItems() {
        final List<Product> laptopCollection = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select p.id, e.brand, e.model, p.price,p.quantity  from electronics_inventory e join product p on p.id = e.product_id where p.product_category='LAPTOP'")) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int product_id = resultSet.getInt(1);
                final String brand = resultSet.getString(2);
                final String model = resultSet.getString(3);
                final float price = resultSet.getFloat(4);
                final int quantity = resultSet.getInt(5);
                final Laptop laptop = new Laptop(brand, model, price, quantity);

                laptop.setId(product_id);
                laptopCollection.add(laptop);
            }
        } catch (final SQLException exception) {
            throw new ItemNotFoundException(exception.getMessage());
        }

        return laptopCollection;
    }

    /**
     * <p>
     * Gets all the products from the clothes inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the clothes inventory.
     */
    private List<Product> getClothesItems() {
        final List<Product> clothesCollection = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select p.id, c.clothes_type ,c.brand, c.gender, c.size, p.price,p.quantity  from clothes_inventory c join product p on p.id = c.product_id where p.product_category = 'CLOTHES'")) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int productId = resultSet.getInt(1);
                final String clothesType = resultSet.getString(2);
                final String brand = resultSet.getString(3);
                final String gender = resultSet.getString(4);
                final String size = resultSet.getString(5);
                final float price = resultSet.getFloat(6);
                final int quantity = resultSet.getInt(7);

                final Clothes clothes = new Clothes(clothesType, gender, size, price, brand, quantity);

                clothes.setId(productId);
                clothesCollection.add(clothes);
            }
        } catch (final SQLException exception) {
            throw new ItemNotFoundException(exception.getMessage());
        }

        return clothesCollection;
    }
}

