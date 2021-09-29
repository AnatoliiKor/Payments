package org.anatkor.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ConnectionPoolTest {
    private static final Logger log = LogManager.getLogger(ConnectionPoolTest.class);

    private static DataSource dataSource;
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String USER_NAME;
    private static final String PASSWORD;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("database", Locale.ENGLISH);
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url.test");
        USER_NAME = config.getString("database.username");
        PASSWORD = config.getString("database.password");
        dataSource = initDataSource();
    }

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
//            log.debug("Was getting Connection from {}.",ConnectionPool.class);
            return connection;
        } catch (SQLException e) {
            log.error("SQLException during get Connection from resource {}. {}",
                    new File("/database.properties").getAbsolutePath(), e);
        }
        return null;
    }

    private static DataSource initDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(URL);
        cpds.setUser(USER_NAME);
        cpds.setPassword(PASSWORD);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(30);
        return cpds;
    }
}
