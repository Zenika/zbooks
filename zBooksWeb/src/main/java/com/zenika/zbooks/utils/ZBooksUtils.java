package com.zenika.zbooks.utils;


import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;

public class ZBooksUtils {

    private static final Logger LOG = Logger.getLogger(ZBooksUtils.class);

    public final static String COOKIE_TOKEN_KEY = "token";
    
    public static String getCookieValue(Cookie[] cookies, String cookieKey) {
        String cookieValue = null;
        if (cookies != null) {
            int i = 0;
            while (i < cookies.length && cookieValue == null) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(cookieKey)) {
                    cookieValue = cookie.getValue();
                }
                i++;
            }
        }
        return cookieValue;
    }

}
