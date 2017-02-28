package com.cool.hello.greenDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.chinaso.greendaodemo.DaoSession;
import com.chinaso.greendaodemo.DeviceDao;
import com.chinaso.greendaodemo.SpaceDao;

/**
 * Created by cool on 2017/2/15.
 */
@Entity
public class Space {
    @Id(autoincrement = true)
    private Long id;
    private String spaceId;
    private String spaceName;
    @ToMany(referencedJoinProperty = "deviceId")
    private List<Device> devices;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 627154630)
    private transient SpaceDao myDao;
    @Generated(hash = 1090734766)
    public Space(Long id, String spaceId, String spaceName) {
        this.id = id;
        this.spaceId = spaceId;
        this.spaceName = spaceName;
    }
    @Generated(hash = 878774195)
    public Space() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSpaceId() {
        return this.spaceId;
    }
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }
    public String getSpaceName() {
        return this.spaceName;
    }
    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 43414454)
    public List<Device> getDevices() {
        if (devices == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DeviceDao targetDao = daoSession.getDeviceDao();
            List<Device> devicesNew = targetDao._querySpace_Devices(id);
            synchronized (this) {
                if (devices == null) {
                    devices = devicesNew;
                }
            }
        }
        return devices;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1428662284)
    public synchronized void resetDevices() {
        devices = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1680882170)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSpaceDao() : null;
    }

}
