package org.abc.launcher;

import org.abc.authentication.view.AuthenticationView;
import org.abc.product.view.homepage.HomepageView;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * <p>
 * Launches the flipkart application.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class Launcher {

    private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

    /**
     * <p>
     * Main entry to the flipkart application.
     * </p>
     *
     */
    public static void launch() {
        LOGGER.info("Flipkart application started");
        final AuthenticationView authenticationView = AuthenticationView.getInstance();

        authenticationView.setHomePageView(HomepageView.getInstance());
        authenticationView.showAuthenticationPage();
    }
}
