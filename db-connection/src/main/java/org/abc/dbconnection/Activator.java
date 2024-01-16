package org.abc.dbconnection;

import org.abc.dbconnection.connection.DBConnection;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.sql.SQLException;

public class Activator implements BundleActivator {

    public void start(BundleContext context) {
        System.out.println("Starting the bundle - db");
    }

    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle");
    }

}