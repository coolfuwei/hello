package com.cool.hello.greenDB;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cool.hello.R;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        initDB();
    }

    public void initDB() {
        DBManager dbManager = DBManager.getInstance(this);
        dbManager.deleteAll();
        for (int i = 0; i < 5; i++) {
            User user = new User();
//            user.setId(new Long((long) i));
            user.setAge(i * 10);
            user.setName("第" + i + "人");
            dbManager.insertUser(user);
        }


        Space space = new Space();
        space.setSpaceId("112233");
        space.setSpaceName("空间1");
        dbManager.insertSpace(space);

        for (int i = 0; i < 5; i++) {
            Device device = new Device();
            device.setDeviceId(space.getId());
            device.setDeviceName("设备" + i);
            dbManager.insertDevice(device);
        }
        Space space2 = new Space();
        space2.setSpaceId("2222222");
        space2.setSpaceName("空间2");
        dbManager.insertSpace(space2);

        for (int i = 0; i < 5; i++) {
            Device device = new Device();
            device.setDeviceId(space2.getId());
            device.setDeviceName("设备" + i);
            dbManager.insertDevice(device);
        }

        Card card = new Card();
        card.setId(new Long(4));
        card.setCardId("no1");
        card.setCardName("card1");
        dbManager.insertCard(card);

        Card card2 = new Card();
        card2.setId(new Long(5));
        card2.setCardId("no2");
        card2.setCardName("card2");
        dbManager.insertCard(card2);

        Person person = new Person();
        person.setCardId(card.getId());
        dbManager.insertPerson(person);

        Person person2 = new Person();
        person2.setCardId(card2.getId());
        dbManager.insertPerson(person2);
        Log.e("399", "插入完成");
    }

    /**
     * 查询所有user
     *
     * @param view
     */
    public void queryUserList(View view) {
        DBManager dbManager = DBManager.getInstance(this);
        List<User> users = dbManager.queryUserList();
        for (User user : users) {
            Log.e("399", "id:" + user.getId() + "name:" + user.getName() + " age:" + user.getAge());
        }
    }

    /**
     * 插入use
     *
     * @param view
     */
    public void insertUser(View view) {
        DBManager dbManager = DBManager.getInstance(this);
        List<User> users = dbManager.queryUserList();
        int len = users.size();
        User user = new User();
//        user.setId(new Long((long)len));
        user.setName("第" + len + "人");
        user.setAge(len * 10);
        dbManager.insertUser(user);
    }

    /**
     * 删除用户
     *
     * @param view
     */
    public void deleteUser(View view) {
        DBManager dbManager = DBManager.getInstance(this);
        List<User> users = dbManager.queryUserList();
        for (User user : users) {
            if (user.getId() == 0) {
                dbManager.deleteUser(user);
            }
        }
    }

    /**
     * 更新User
     *
     * @param v
     */
    public void updateUser(View v) {
        DBManager dbManager = DBManager.getInstance(this);
        List<User> users = dbManager.queryUserList();
        for (User user : users) {
            if (user.getId() == 3) {
                user.setAge(11112);
                user.setName("张三");
                dbManager.updateUser(user);
            }
        }
    }

    /**
     * 查询所有空间
     *
     * @param view
     */
    public void querySpace(View view) {
        DBManager dbManager = DBManager.getInstance(this);
        List<Space> spaces = dbManager.querySpaceList();
            for (Space space : spaces){
            Log.e("399", "spaceId: " + space.getSpaceId() + "spaceName: " + space.getSpaceName());
            List<Device> devices = space.getDevices();
            for (Device device : devices) {
                Log.e("399", "deviceId: " + device.getDeviceId() + "deviceName: " + device.getDeviceName());
            }
        }
    }

    public void queryOneToOne(View view){
        DBManager dbManager = DBManager.getInstance(this);
        List<Person> persons = dbManager.queryPerson();
        int i = 0;
        for (Person person : persons){
            i++;
            Card card = person.getCard();
            Log.e("399","person" + i+ card.getCardName());
        }
    }
}
