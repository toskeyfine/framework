package com.toskey.framework.modules.admin.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.toskey.framework.common.base.DataEntity;

/**
 * 字典
 */
@TableName("sys_dict")
public class Dict extends DataEntity<Dict> {

    private String value;

    private String label;

    private String type;

    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
