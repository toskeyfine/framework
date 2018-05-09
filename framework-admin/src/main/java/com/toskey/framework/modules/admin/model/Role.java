package com.toskey.framework.modules.admin.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.toskey.framework.common.base.DataEntity;

@TableName("sys_role")
public class Role extends DataEntity<Role> {

    private String roleName;
    private String enName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
