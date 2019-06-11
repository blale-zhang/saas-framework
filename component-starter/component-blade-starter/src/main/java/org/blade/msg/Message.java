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

	public Message() {
	}

	public Message(int status) {
		this.status = status;
	}

	public Message(int status, String description) {
		this.status = status;
		this.description = description;
	}

	/**
	 * 成功消息
	 * @param description
	 * @return
	 */
	public static Message success(String description){

		return new Message(ErrorCode.Success.SUCCESS,description);
	}

	/**
	 * 失败消息
	 * @param failCode
	 * @param description
	 * @return
	 */
	public static Message fail(Integer failCode, String description){

		return new Message(failCode,description);
	}

    /**
     * 权限验证失败
     * @param description
     * @return
     */
	public static Message fof(String description){

		return new Message(ErrorCode.Login.PERMISSION_VERIFICATION_FAILED,description);
	}

}
