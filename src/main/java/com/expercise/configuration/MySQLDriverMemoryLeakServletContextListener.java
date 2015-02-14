package com.expercise.configuration;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class MySQLDriverMemoryLeakServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDriverMemoryLeakServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("An error occurred while deregistering drivers.", e);
            }
        }

        // MySQL driver leaves around a thread. This static method cleans it up.
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            LOGGER.error("An error occurred while shutting down MySQL driver thread.", e);
        }
    }

}
