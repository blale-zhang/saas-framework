package org.smr.cloud.saas.auth2.comp.service;

import org.smr.ministore.entities.Role;
import org.smr.ministore.entities.User;
import org.smr.ministore.micro.UserMicroService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMicroService userMicroService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMicroService.queryByUserName(username);
        List<Role>  roleList = userMicroService.getRoleByUser(user.getId());
        org.springframework.security.core.userdetails.User securityUser =
                new org.springframework.security.core.userdetails.User(
                        user.getUserName(),
                        user.getPassword(),
                        createAuthorities(roleList));

        return securityUser;
    }

    public UserMicroService getUserMicroService() {
        return userMicroService;
    }

    public void setUserMicroService(UserMicroService userMicroService) {
        this.userMicroService = userMicroService;
    }

    /**
     * 权限字符串转化
     *
     * 如 "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     *
     * @param roleList 权限字符串
     */
    private List<SimpleGrantedAuthority> createAuthorities(List<Role> roleList){

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Role role : roleList) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        return simpleGrantedAuthorities;
    }

}
