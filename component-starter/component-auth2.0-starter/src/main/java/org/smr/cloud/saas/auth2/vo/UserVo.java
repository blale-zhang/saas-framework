package org.smr.cloud.saas.auth2.vo;

import java.util.List;

public class UserVo {


    /**
     * 主键
     **/
    private java.lang.Long id;

    /**
     * 设置主键的值
     * @param id
     **/
    public void  setId(java.lang.Long id){
        this.id = id;
    }
    /**
     * 获取主键的值
     **/
    public  java.lang.Long getId(){
        return this.id;
    }


    /**
     * 用户名
     **/
    private java.lang.String userName;

    /**
     * 设置用户名的值
     * @param userName
     **/
    public void  setUserName(java.lang.String userName){
        this.userName = userName;
    }
    /**
     * 获取用户名的值
     **/
    public  java.lang.String getUserName(){
        return this.userName;
    }


    /**
     * 密码
     **/
    private java.lang.String password;

    /**
     * 设置密码的值
     * @param password
     **/
    public void  setPassword(java.lang.String password){
        this.password = password;
    }
    /**
     * 获取密码的值
     **/
    public  java.lang.String getPassword(){
        return this.password;
    }


    /**
     * 所属机构
     **/
    private java.lang.Long organId;

    /**
     * 设置所属机构的值
     * @param organId
     **/
    public void  setOrganId(java.lang.Long organId){
        this.organId = organId;
    }
    /**
     * 获取所属机构的值
     **/
    public  java.lang.Long getOrganId(){
        return this.organId;
    }


    /**
     * 机构路径
     **/
    private java.lang.String organPath;

    /**
     * 设置机构路径的值
     * @param organPath
     **/
    public void  setOrganPath(java.lang.String organPath){
        this.organPath = organPath;
    }
    /**
     * 获取机构路径的值
     **/
    public  java.lang.String getOrganPath(){
        return this.organPath;
    }


    /**
     * 是否生效
     **/
    private java.lang.Integer enable;

    /**
     * 设置是否生效的值
     * @param enable
     **/
    public void  setEnable(java.lang.Integer enable){
        this.enable = enable;
    }
    /**
     * 获取是否生效的值
     **/
    public  java.lang.Integer getEnable(){
        return this.enable;
    }


    /**
     * 是否超管
     **/
    private java.lang.Integer isSuperMgr;

    /**
     * 设置是否超管的值
     * @param isSuperMgr
     **/
    public void  setIsSuperMgr(java.lang.Integer isSuperMgr){
        this.isSuperMgr = isSuperMgr;
    }
    /**
     * 获取是否超管的值
     **/
    public  java.lang.Integer getIsSuperMgr(){
        return this.isSuperMgr;
    }


    /**
     *
     **/
    private java.lang.Long createBy;

    /**
     * 设置的值
     * @param createBy
     **/
    public void  setCreateBy(java.lang.Long createBy){
        this.createBy = createBy;
    }
    /**
     * 获取的值
     **/
    public  java.lang.Long getCreateBy(){
        return this.createBy;
    }


    /**
     *
     **/
    private java.lang.Long updateBy;

    /**
     * 设置的值
     * @param updateBy
     **/
    public void  setUpdateBy(java.lang.Long updateBy){
        this.updateBy = updateBy;
    }
    /**
     * 获取的值
     **/
    public  java.lang.Long getUpdateBy(){
        return this.updateBy;
    }


    /**
     *
     **/
    private java.util.Date createDate;

    /**
     * 设置的值
     * @param createDate
     **/
    public void  setCreateDate(java.util.Date createDate){
        this.createDate = createDate;
    }
    /**
     * 获取的值
     **/
    public  java.util.Date getCreateDate(){
        return this.createDate;
    }


    /**
     *
     **/
    private java.util.Date updateDate;

    /**
     * 设置的值
     * @param updateDate
     **/
    public void  setUpdateDate(java.util.Date updateDate){
        this.updateDate = updateDate;
    }
    /**
     * 获取的值
     **/
    public  java.util.Date getUpdateDate(){
        return this.updateDate;
    }


    /**
     *
     **/
    private java.lang.String remark;

    /**
     * 设置的值
     * @param remark
     **/
    public void  setRemark(java.lang.String remark){
        this.remark = remark;
    }
    /**
     * 获取的值
     **/
    public  java.lang.String getRemark(){
        return this.remark;
    }


    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
