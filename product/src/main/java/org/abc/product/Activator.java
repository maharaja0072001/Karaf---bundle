package org.abc.product;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) {
        System.out.println("Starting the bundle - product");
    }

    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle");
    }

}