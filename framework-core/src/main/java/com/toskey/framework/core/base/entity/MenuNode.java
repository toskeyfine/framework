package com.toskey.framework.core.base.entity;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单借点
 * @author toskey
 */
public class MenuNode implements Comparable {

    private String id;

    private String parentId;

    private String parentIds;

    private String name;

    private String href;

    private String target;

    private String icon;

    private Integer sort;

    private Integer show;

    private String permission;

    private Integer level;

    private Integer delFlag;

    private List<MenuNode> childrenList = Lists.newArrayList();

    private List<MenuNode> linkedList = Lists.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public List<MenuNode> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<MenuNode> childrenList) {
        this.childrenList = childrenList;
    }

    public List<MenuNode> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(List<MenuNode> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public int compareTo(Object o) {
        MenuNode menuNode = (MenuNode) o;
        Integer sort = menuNode.getSort();
        if(sort == null) {
            sort = 0;
        }
        return sort.compareTo(sort);
    }

    /**
     * 构建菜单树
     * @param nodeList
     * @author toskey
     */
    public static List<MenuNode> buildNodeTree(List<MenuNode> nodeList) {
        // 创建一个用于存放顶级菜单的集合
        List<MenuNode> topMenuList = new ArrayList<MenuNode>();
        for(MenuNode treeNode : nodeList) {
            if(treeNode.getLevel() == 1) {
                topMenuList.add(treeNode);
            }
        }
        // 遍历顶级菜单，获取其子节点集合
        for(MenuNode treeNode : topMenuList) {
            findChildNodes(treeNode, nodeList);
        }
        return topMenuList;
    }

    /**
     * 递归查询子节点的集合
     * @param treeNode
     * @param nodeList
     * @author toskey
     */
    private static void findChildNodes(MenuNode treeNode, List<MenuNode> nodeList) {
        if (nodeList == null && treeNode.getId() == null)
            return;
        List<MenuNode> children = new ArrayList<MenuNode>();
        for(MenuNode node : nodeList) {
            if(null != node.getParentId() && node.getParentId().equals(treeNode.getId())) {
                children.add(node);
                findChildNodes(node, nodeList);
            }
        }
        treeNode.setChildrenList(children);
    }
}
