package org.anatkor.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDAO {

    private static final Logger log = LogManager.getLogger(UtilDAO.class);

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.debug("Exception during close {} from {}. {}", ac.toString(), UtilDAO.class, e.getMessage());
            }
        }
    }
}
