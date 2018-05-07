package com.toskey.framework.core.base.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;


public abstract class BaseEntity extends Model {

    protected String id;

    public abstract void preInsert();

    public abstract void preUpdate();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
