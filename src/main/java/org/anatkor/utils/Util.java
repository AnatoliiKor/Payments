package org.anatkor.utils;

import org.anatkor.constants.Constant;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String getFormattedDate(LocalDateTime registered) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.format(formatter);
    }

    public static String getRequestParamOrDefault(HttpServletRequest req, String param, String def) {
        if (req.getParameter(param) != null) {
            def = req.getParameter(param);
        }
        return def;
    }

    public static HttpServletRequest requestGetAndSetPage(HttpServletRequest req) {
        String page = req.getParameter(Constant.PAGE);
        if (page == null || page.equals("")) {
            req.setAttribute(Constant.PAGE, 1);
        } else {
            req.setAttribute(Constant.PAGE, Integer.parseInt(page));
        }
        return req;
    }
}
