package com.cool.hello.greenDB;

import android.content.Context;

import com.chinaso.greendaodemo.CardDao;
import com.chinaso.greendaodemo.DaoMaster;
import com.chinaso.greendaodemo.DaoSession;
import com.chinaso.greendaodemo.DeviceDao;
import com.chinaso.greendaodemo.PersonDao;
import com.chinaso.greendaodemo.SpaceDao;
import com.chinaso.greendaodemo.UserDao;
import com.cool.hello.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by cool on 2017/2/15.
 */

public class DBManager {
    private static DBManager mInstance;
    private static DaoMaster.DevOpenHelper openHelper;
    private static Context mContext;
    private UserDao userDao;
    private SpaceDao spaceDao;
    private DeviceDao deviceDao;
    private PersonDao personDao;
    private CardDao cardDao;

    private DBManager() {
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized ((DBManager.class)) {
                mContext = context;
                mInstance = new DBManager();
                DaoSession daoSession = App.getDaoSession(mContext);
                mInstance.userDao = daoSession.getUserDao();
                mInstance.spaceDao = daoSession.getSpaceDao();
                mInstance.deviceDao = daoSession.getDeviceDao();
                mInstance.personDao = daoSession.getPersonDao();
                mInstance.cardDao = daoSession.getCardDao();
            }
        }
        return mInstance;
    }

    public void insertUser(User user) {
        userDao.insert(user);
    }

    public void insertUserList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        userDao.insertInTx(users);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public void deleteAll() {
        userDao.deleteAll();
        spaceDao.deleteAll();
        deviceDao.deleteAll();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> queryUserList() {
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }

    public List<User> queryUserList(int age) {
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<User> list = qb.list();
        return list;
    }

    public List<Space> querySpaceList(){
        QueryBuilder<Space> qb = spaceDao.queryBuilder();
        List<Space> list = qb.list();
        return list;
    }
    public void insertSpace(Space space){
        spaceDao.insert(space);
    }
    public void insertDevice(Device device){
        deviceDao.insert(device);
    }

    public void insertPerson(Person person){
        personDao.insert(person);
    }
    public void insertCard(Card card){
        cardDao.insert(card);
    }

    public List<Card> queryCard(){
        QueryBuilder<Card> qb = cardDao.queryBuilder();
        List<Card> list = qb.list();
        return list;
    }
    public List<Person> queryPerson(){
        return personDao.queryBuilder().list();
    }
}
