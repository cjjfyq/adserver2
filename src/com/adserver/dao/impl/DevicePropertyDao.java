package com.adserver.dao.impl;

import com.adserver.dao.AbstractBaseDao;
import com.adserver.web.entity.DeviceProperty;

public class DevicePropertyDao extends AbstractBaseDao<DeviceProperty, Integer> {

    
    /**
     * 添加一条手机设备
     * @param dp
     */
    public void addDevice(DeviceProperty dp) {
        sessionFactory.getCurrentSession().save(dp);
    }
    
}
