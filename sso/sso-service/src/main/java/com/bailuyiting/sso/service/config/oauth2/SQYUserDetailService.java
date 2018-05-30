package com.bailuyiting.sso.service.config.oauth2;

import com.bailuyiting.commons.core.property.OAuth2Property;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户名密码认证
 */
@Component
public class SQYUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //用户名密码自定义认证
        return new User(s,OAuth2Property.userDetailPassword,AuthorityUtils.commaSeparatedStringToAuthorityList("admin,FOO_READ"));
    }
}
