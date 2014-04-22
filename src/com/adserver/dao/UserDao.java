package com.adserver.dao;

import com.adserver.web.entity.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Gordon on 2014/4/15.
 */
public class UserDao implements IUserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void addUser(User user) {
//        Session session = sessionFactory.openSession();
//        session.save(user);
//        session.close();
        System.out.println("add user ----");
        sessionFactory.getCurrentSession().save(user);
        
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public void updateUser(User manager) {

    }

    @Override
    public User getUser(String name) {
        String hql = "from User u where u.name=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, name);
        return (User) query.uniqueResult();
    }
}
