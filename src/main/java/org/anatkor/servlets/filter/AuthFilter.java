//package org.anatkor.servlets.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class AuthFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)
//            throws IOException, ServletException {
//
//        final HttpServletRequest req = (HttpServletRequest) request;
//        final HttpServletResponse res = (HttpServletResponse) response;
//
//        HttpSession session = req.getSession();
//        ServletContext context = request.getServletContext();
//        System.out.println(session);
//        System.out.println(session.getAttribute("role"));
//        System.out.println(context.getAttribute("loggedUsers"));
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
