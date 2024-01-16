package org.abc.authentication;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) {
        System.out.println("Starting the bundle - db");
    }

    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle");
    }

}