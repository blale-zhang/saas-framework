package org.blade.msg;

/**
 * 接口之间调用返回
 * @author blade
 *
 */
public class Message {

	private int status;
	
	private String description;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
