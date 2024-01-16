package org.abc.launcher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) {
        System.out.println("Starting the bundle-Launcher");
        System.out.println("Launcher is started");
        Launcher.launch();
    }
    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle");
    }
}