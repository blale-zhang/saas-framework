package org.blade.prop;

/**
 * 图片验证码配置项
 *
 * @author paascloud.net @gmail.com
 */

public class ImageCodeProperties extends SmsCodeProperties {

	/**
	 * Instantiates a new Image code properties.
	 */
	ImageCodeProperties() {
		super.setLength(4);
	}

}
