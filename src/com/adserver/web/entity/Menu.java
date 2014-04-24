package com.adserver.web.entity;

/**
 * @author Administrator
 * 菜单
 */
public class Menu {
    /**
     * 主键
     */
    private int id;
    
    private int menuId;
    
    private String name;
    
    private String url;
    
    /**
     * 父菜单id
     */
    private int parentId;
    
    
    private int orderNum;
    
    @Override
    public String toString() {
        return "Menu [id=" + id + ", menuId=" + menuId + ", name=" + name
                + ", url=" + url + ", parentId=" + parentId + ", orderNum="
                + orderNum + ", command=" + command + "]";
    }

    /**
     * 备注
     */
    private String command;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
