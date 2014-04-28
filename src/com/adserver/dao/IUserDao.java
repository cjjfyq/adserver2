package com.adserver.dao;

import com.adserver.web.entity.User;

import java.util.List;

/**
 * Created by Gordon on 2014/4/15.
 */
public interface IUserDao {

    /**
     * 添加用户
     * @param manager
     */
    void addUser(User manager);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 更新用户
     * @param manager
     */
    boolean updateUser(User user);

    User getUser(String name);

    boolean delUser(int id);
    

}
