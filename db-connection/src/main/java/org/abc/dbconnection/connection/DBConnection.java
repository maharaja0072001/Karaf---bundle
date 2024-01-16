package org.abc.dbconnection.connection;

import org.abc.dbconnection.exceptions.FileUnavailableException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * <p>
 * Provides connection with the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class DBConnection {

    private static Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(DBConnection.class);

    /**
     * <p>
     * Default constructor of DBConnection class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private DBConnection() {}

    /**
     * <p>
     * Creates a connection with database and returns it.
     * </p>
     *
     * @return {@link Connection} of the database.
     */
    public static Connection getConnection() throws SQLException {
        if (null == connection) {
            final Properties properties = new Properties();

            try (final FileReader fileReader = new FileReader(String.join("",System.getenv("DB_CONFIG_PATH"), "/db.properties"))) {
                properties.load(fileReader);
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));

                connection.setAutoCommit(false);
                LOGGER.info("Database is connected");
            } catch (final IOException exception) {
                LOGGER.error("File not found");
                throw new FileUnavailableException("File not found");
            }
        }

        return connection;
    }
}
