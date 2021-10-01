package org.anatkor.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    private static final Logger log = LogManager.getLogger(Utils.class);

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
