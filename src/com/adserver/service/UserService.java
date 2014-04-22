package com.adserver.service;

import com.adserver.dao.IUserDao;
import com.adserver.web.entity.User;

import java.util.List;

/**
 * Created by Gordon on 2014/4/15.
 *
 */
public class UserService implements IUserService {

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
    public void updateUser(User manager) {
        userDao.updateUser(manager);
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }
}
