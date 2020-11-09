package com.newcoder.forum.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // main do

            for (Cookie cookie :cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
