package org.abc.exception;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) {
        System.out.println("Starting the bundle - exception ");
    }

    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle");
    }
}