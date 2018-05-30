package com.bailuyiting.sso.service.Interceptor;


import com.bailuyiting.commons.until.SQYRequestUntils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 白鹿易停扩展接口用拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

//    private static final Logger logger = LoggerFactory.getLogger(SQYInterceptor.class);
    @Autowired
    private TokenStore tokenStore;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
        /**
         * 处理请求
         */
        String xToken = req.getHeader("x-token");
        if(StringUtils.isBlank(xToken)){
            resp.sendError(600,"未携带token，没有访问权限");
            return false;
        }
        OAuth2AccessToken token=null;
        try {
            token = this.tokenStore.readAccessToken(xToken);
        }
        catch (Exception e){
            resp.sendError(600,"解析token有误，请重新登入");
            return false;
        }
        if (token.isExpired()) {
            resp.sendError(600,"非法访问，token已过期");
            return false;
        } else {
            String user_name = (String) token.getAdditionalInformation().get("user_name");
            if(user_name==null){
                resp.sendError(600,"token有问题，请重新申请");
                return false;
            }
            req.setAttribute("account",user_name);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }





}
