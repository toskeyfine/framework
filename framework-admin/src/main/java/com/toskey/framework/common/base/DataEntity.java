package com.toskey.framework.common.base;

import com.toskey.framework.core.base.entity.BaseEntity;
import com.toskey.framework.core.constant.Global;
import com.toskey.framework.modules.admin.model.User;

import java.util.Date;

public class DataEntity<T> extends BaseEntity {

    private Integer sort;
    private Integer delFlag;
    private User createBy;
    private Date createTime;
    private User updateBy;
    private Date updateTime;
    private String remarks;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public void preInsert() {
        this.setDelFlag(Global.NORMAL);
        this.setCreateTime(new Date());

    }

    @Override
    public void preUpdate() {

    }
}
