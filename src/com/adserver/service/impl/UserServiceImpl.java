package com.adserver.service.impl;

import com.adserver.dao.IUserDao;
import com.adserver.service.IUserService;
import com.adserver.web.entity.User;

import java.util.List;

/**
 * Created by Gordon on 2014/4/15.
 *
 */
public class UserServiceImpl implements IUserService {

    private IUserDao userDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User manager) {
        userDao.addUser(manager);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allManager = userDao.getAllUser();
        System.out.println("all manager:" + allManager);
        return allManager;
    }

    @Override
    public boolean updateUser(User manager) {
        return userDao.updateUser(manager);
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }

    @Override
    public boolean delUser(int id) {
        return userDao.delUser(id);
    }
}
