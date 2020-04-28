package com.jxm.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    public static volatile boolean isLog = true;

    public static void delCookie(HttpServletResponse res, String cookieName) throws Exception {
        Cookie resCookie = new Cookie(cookieName, "");
        resCookie.setPath("/");
        res.addCookie(resCookie);
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieVal, int age) {
        Cookie resCookie2 = new Cookie(cookieName, cookieVal);
        resCookie2.setPath("/");
        resCookie2.setMaxAge(age);
        response.addCookie(resCookie2);
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieVal) {
        addCookie(response, cookieName, cookieVal, 365 * 24 * 60 * 60);
    }
}
