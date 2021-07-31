package org.anatkor.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Utils {
    static private final String driver = "org.postgresql.Driver";
    private static final Logger log = LogManager.getLogger(Utils.class);

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(getUrl());
        } catch (SQLException | ClassNotFoundException e) {
            log.debug("SQLException during get connection to url = {} from {}.", getUrl(), Utils.class, e);
            e.printStackTrace();
        }
        return connection;
    }

    public static String getUrl() {
        Properties dbProperties = new Properties();
        String filePropertiesName = "database.properties";
//      TODO   String filePropertiesName = "database.properties"; not tomcat bin
//
//        try (InputStream in = Utils.class.getClassLoader().getResourceAsStream(filePropertiesName)) {
//            dbProperties.load(in);
//            log.info("connection created succesfully");
        try {
            dbProperties.load(new FileReader(filePropertiesName));
            log.info("connection created succesfully");
        } catch (IOException e) {
            log.error("IOException during read {} from {}.", new File(filePropertiesName).getAbsolutePath(), e.toString());
        }
        return dbProperties.getProperty("connection.url");
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                log.info("Connection is closed");
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    log.warn(e1.getMessage());
                }
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.debug("SQLException during close ResultSet from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    log.warn(e1.getMessage());
                }
            }
        }
    }



}
