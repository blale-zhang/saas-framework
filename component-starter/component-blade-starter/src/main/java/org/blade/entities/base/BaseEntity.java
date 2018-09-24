package org.blade.entities.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 * @author blade
 *
 * @param <ID>
 */
public class BaseEntity<ID> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5781844807680316189L;

	/**
	 * id
	 */
	private ID id;
	
	/**
	 * 记录生成时间
	 */
	private Date createTime;
	
	/**
	 * 记录修改时间
	 */
	private Date modifyTime;


	private boolean isDeleted;


	private ID createBy;

	private ID modifyBy;


	/**
	 * uuid
	 */
	private String uuid;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public ID getCreateBy() {
		return createBy;
	}

	public void setCreateBy(ID createBy) {
		this.createBy = createBy;
	}

	public ID getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(ID modifyBy) {
		this.modifyBy = modifyBy;
	}
}
