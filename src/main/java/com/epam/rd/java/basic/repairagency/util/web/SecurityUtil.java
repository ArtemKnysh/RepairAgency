package com.epam.rd.java.basic.repairagency.util.web;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.web.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityUtil {

    public static boolean isLoginRequired(HttpServletRequest httpRequest) {
        String urlPattern = WebUtil.getUrlPattern(httpRequest);
        return isLoginRequired(urlPattern);
    }

    private static boolean isLoginRequired(String urlPattern) {
        return SecurityConfig.getLoginRequiredUrlPatterns().contains(urlPattern);
    }

    public static boolean isLoggedUserHasPermission(HttpServletRequest request) {
        String urlPattern = WebUtil.getUrlPattern(request);
        User loggedUser = WebUtil.getLoggedUser(request);
        if (loggedUser == null) {
            return false;
        }
        List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(loggedUser.getRole());
        if (urlPatterns != null) {
            for (String pattern : urlPatterns) {
                if (urlPattern.contains(pattern)) {
                    return true;
                }
            }
        }
        return !isLoginRequired(urlPattern);
    }

}
