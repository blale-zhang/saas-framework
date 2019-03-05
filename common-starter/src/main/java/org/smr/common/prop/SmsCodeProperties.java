package org.smr.common.prop;


/**
 * The class Sms code properties.
 *
 * @author paascloud.net @gmail.com
 */
public class SmsCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 过期时间
	 */
	private int expireIn = 60;
	/**
	 * 要拦截的url，多个url用逗号隔开，ant pattern
	 */
	private String url;
	/**
	 * 每天每个手机号最大送送短信数量
	 */
	private int mobileMaxSendCount;
	/**
	 * 每天每个IP最大送送短信数量
	 */
	private int ipMaxSendCount;
	/**
	 * 每天最大送送短信数量
	 */
	private int totalMaxSendCount;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMobileMaxSendCount() {
		return mobileMaxSendCount;
	}

	public void setMobileMaxSendCount(int mobileMaxSendCount) {
		this.mobileMaxSendCount = mobileMaxSendCount;
	}

	public int getIpMaxSendCount() {
		return ipMaxSendCount;
	}

	public void setIpMaxSendCount(int ipMaxSendCount) {
		this.ipMaxSendCount = ipMaxSendCount;
	}

	public int getTotalMaxSendCount() {
		return totalMaxSendCount;
	}

	public void setTotalMaxSendCount(int totalMaxSendCount) {
		this.totalMaxSendCount = totalMaxSendCount;
	}
}
