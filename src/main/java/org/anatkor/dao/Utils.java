package org.anatkor.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Utils {
    //    static private final String driver = "org.postgresql.Driver";
    private static final Logger log = LogManager.getLogger(Utils.class);

//    public static Connection getConnection() {
//        Connection connection = null;
////        log.debug("connection try");
//        try {
//            Class.forName(driver);
//            connection = DriverManager.getConnection(getUrl());
//        } catch (SQLException | ClassNotFoundException e) {
//            log.debug("SQLException during get connection to url = {} from {}. {}", getUrl(), Utils.class, e.getMessage());
//        }
//        return connection;
//    }
//
//    public static String getUrl() {
//        Properties dbProperties = new Properties();
//        String filePropertiesName = "database.properties";
//        try (InputStream in = Utils.class.getClassLoader().getResourceAsStream(filePropertiesName)) {
//            dbProperties.load(in);
////            log.debug("connection created succesfully");
//        } catch (IOException e) {
//            log.error("IOException during read {} from {}.", new File(filePropertiesName).getAbsolutePath(), e.toString());
//        }
//        return dbProperties.getProperty("connection.url");
//    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.debug("Exception during close {} from {}. {}", ac.toString(), Utils.class, e.getMessage());
            }
        }
    }
}
