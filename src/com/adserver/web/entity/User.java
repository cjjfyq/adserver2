package com.adserver.web.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Gordon on 2014/4/15.
 * 后台管理员
 */
//@Entity
//@Table(name = "t_user")
public class User {

//    @Id
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(generator = "system-uuid")
//    @Column(length = 32)
    private int id;

    //登录名
//    @Column(length = 32)
    private String name;

    //登录密码
//    @Column(length = 32)
    private String password;

    //该用户名地查看的菜单 权限
//    @Column(length = 32)
    private String menus;

    //昵称
//    @Column(length = 32)
    private String nickName;
    
    /**
     * 用户类型，依据用户类型来决定用户的权限
     * 
     */
    private int userType;
    
    private int userscope;
    
    private String projectIds;
    

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public int getUserscope() {
        return userscope;
    }

    public void setUserscope(int userscope) {
        this.userscope = userscope;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password
                + ", menus=" + menus + ", nickName=" + nickName + ", userType="
                + userType + ", userscope=" + userscope + ", projectIds="
                + projectIds + "]";
    }
    
}
