package org.smr.common.prop;



/**
 * 社交登录配置项
 *
 * @author paascloud.net @gmail.com
 */
public class SocialProperties {

	/**
	 * 社交登录功能拦截的url
	 */
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();

	private WeixinProperties weixin = new WeixinProperties();

}
