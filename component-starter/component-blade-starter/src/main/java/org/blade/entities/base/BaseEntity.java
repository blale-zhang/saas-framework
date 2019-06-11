package org.blade.entities.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

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
	@JSONField(serializeUsing = ToStringSerializer.class)
	private ID id;
	
	/**
	 * 记录生成时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	/**
	 * 记录修改时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;


	private boolean isDeleted;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private ID createBy;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private ID updateBy;


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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

    public ID getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(ID updateBy) {
        this.updateBy = updateBy;
    }
}
