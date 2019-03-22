package org.smr.ministore.oauth2.ext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface UacUserService {

    public void handlerLoginData(OAuth2AccessToken token, final UserDetails principal, HttpServletRequest request);
}
