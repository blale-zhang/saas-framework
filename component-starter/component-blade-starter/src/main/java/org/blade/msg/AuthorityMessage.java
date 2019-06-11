package org.blade.msg;

/**
 * 权限息
 */
public class AuthorityMessage  extends  TokenMessage{

    private Object authorities;


    public AuthorityMessage(int status, String description, String token, Object data) {
        super(status, description, token, data);
    }

    public Object getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Object authorities) {
        this.authorities = authorities;
    }
}
