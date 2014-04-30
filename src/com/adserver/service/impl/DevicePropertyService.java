package com.adserver.service.impl;

import com.adserver.dao.impl.DevicePropertyDao;
import com.adserver.web.entity.DeviceProperty;

public class DevicePropertyService {

    private DevicePropertyDao devicePropertyDao;
    
    
    
    public DevicePropertyDao getDevicePropertyDao() {
        return devicePropertyDao;
    }



    public void setDevicePropertyDao(DevicePropertyDao devicePropertyDao) {
        this.devicePropertyDao = devicePropertyDao;
    }



    public void addDeviceProperty(DeviceProperty dp) {
        devicePropertyDao.addDevice(dp);
    }
    
}
