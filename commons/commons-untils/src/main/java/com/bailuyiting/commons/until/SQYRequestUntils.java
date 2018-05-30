package com.bailuyiting.commons.until;

import com.bailuyiting.commons.core.entity.sso.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 自己用
 */
public class SQYRequestUntils {

    public static Object get(HttpServletRequest request,String key){
        return request.getAttribute(key);
    }
    public static String getAccount(HttpServletRequest request){
        return (String) SQYRequestUntils.get(request,"account");
    }
    public static String getUserId(HttpServletRequest request){
        return (String) SQYRequestUntils.get(request,"id");
    }
    public static SysUser getUser(HttpServletRequest request){
        return (SysUser) SQYRequestUntils.get(request,"user");
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
          if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("Proxy-Client-IP");
                   }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                      ip = request.getHeader("WL-Proxy-Client-IP");
                 }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                      ip = request.getHeader("HTTP_CLIENT_IP");
                   }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                   }
          if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                      ip = request.getRemoteAddr();
                   }
                return ip;
           }
}
