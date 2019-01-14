package org.blade.prop;


/**
 * 认证服务器注册的第三方应用配置项
 *
 * @author paascloud.net @gmail.com
 */
public class OAuth2ClientProperties {

	/**
	 * 第三方应用appId
	 */
	private String clientId;
	/**
	 * 第三方应用appSecret
	 */
	private String clientSecret;
	/**
	 * 针对此应用发出的token的有效时间
	 */
	private int accessTokenValidateSeconds = 7200;

	private int refreshTokenValiditySeconds = 2592000;

	private String scope;


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getAccessTokenValidateSeconds() {
		return accessTokenValidateSeconds;
	}

	public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
		this.accessTokenValidateSeconds = accessTokenValidateSeconds;
	}

	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
