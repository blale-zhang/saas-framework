package org.smr.cloud.saas.auth2.comp;

import org.smr.common.constant.SecurityConstants;
import org.smr.ministore.entities.Role;
import org.smr.ministore.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class UserDetailsImpl  implements UserDetails {


    /**
     *
     */
    private User user;

    public UserDetailsImpl( Long id, String userName, String password, Long organId, String organPath, Integer enable, Integer isSuperMgr, List<Role> roles ) {
        this.user = new User();
        this.user.setId(id);
        this.user.setUserName(userName);
        this.user.setPassword(password);
        this.user.setOrganId(organId);
        this.user.setOrganPath(organPath);
        this.user.setEnable(enable);
        this.user.setIsSuperMgr(isSuperMgr);
        this.user.setRoles(roles);
    }

    public UserDetailsImpl( User user ){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        authorityList.add(new SimpleGrantedAuthority(SecurityConstants.BASE_ROLE));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getIsLock().equals( User.IS_LOCK );
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnable().equals( User.USER_ENABLE );
    }
}
