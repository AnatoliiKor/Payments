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

    public static String getRequestParamSortOrDefault(HttpServletRequest req, String sortBy) {
        if (req.getParameter(Constant.SORT_BY) != null) {
            sortBy = req.getParameter(Constant.SORT_BY);
        }
        return sortBy;
    }

    public static String getRequestParamOrderOrDefault(HttpServletRequest req, String order) {
        if (req.getParameter(Constant.ORDER) != null) {
            order = req.getParameter(Constant.ORDER);
        }
        return order;
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
