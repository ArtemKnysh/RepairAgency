package com.epam.rd.java.basic.repairagency.web.config;

import com.epam.rd.java.basic.repairagency.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public final class SecurityConfig {

    private static final Logger log = LogManager.getLogger(SecurityConfig.class);
    private static final Map<UserRole, List<String>> userRoleUrlPatterns = new HashMap<>();
    private static final Set<String> loginRequiredUrlPatterns = new HashSet<>();

    public static List<String> getUrlPatternsForRole(UserRole userRole) {
        return userRoleUrlPatterns.get(userRole);
    }

    public static Set<String> getLoginRequiredUrlPatterns() {
        return loginRequiredUrlPatterns;
    }

    public static void clearUrls() {
        userRoleUrlPatterns.clear();
        loginRequiredUrlPatterns.clear();
    }

    public static void putUrl(String name, String url) {
//        log.info("name = " + name);
//        log.info("url = " + url);
        for (UserRole value : UserRole.values()) {
            String role = value.toString().toLowerCase();
            String roleInName = name.substring(0, role.length());
            if (roleInName.equals(role)) {
                userRoleUrlPatterns.computeIfAbsent(value, k -> new ArrayList<>()).add(url);
                loginRequiredUrlPatterns.add(url);
                return;
            }
        }
    }

    public static void printAll() {
        log.info("loginRequiredUrlPatterns = " + loginRequiredUrlPatterns);
        for (Map.Entry<UserRole, List<String>> userRoleUrlList : userRoleUrlPatterns.entrySet()) {
            log.info("UserRole ==> " + userRoleUrlList.getKey());
            log.info("UserRole URLs ==> " + userRoleUrlList.getValue());
        }
    }
}
