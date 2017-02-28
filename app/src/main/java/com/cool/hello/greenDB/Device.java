package com.cool.hello.greenDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cool on 2017/2/15.
 */

@Entity
public class Device {

    @Id(autoincrement = true)
    private Long id;
    private long deviceId;
    private String deviceName;
    @Generated(hash = 1678579982)
    public Device(Long id, long deviceId, String deviceName) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }
    @Generated(hash = 1469582394)
    public Device() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
