package com.quangnv.architecturecomponentdemo.data.source.local;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.source.UserDataSource;
import com.quangnv.architecturecomponentdemo.data.source.UserListener;
import com.quangnv.architecturecomponentdemo.data.source.local.sqlite.UserDao;

import java.util.List;

public class UserLocalDataSource implements UserDataSource.Local {

    private static UserLocalDataSource sInstance;

    private UserDao mUserDao;

    private UserLocalDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    public static UserLocalDataSource getInstance(UserDao userDao) {
        if (sInstance == null) {
            sInstance = new UserLocalDataSource(userDao);
        }
        return sInstance;
    }

    @Override
    public void insert(User user) {
        mUserDao.insert(user);
    }

    @Override
    public void update(User user) {
        mUserDao.update(user);
    }

    @Override
    public void delete(User user) {
        mUserDao.delete(user);
    }

    @Override
    public User getUser(int userId) {
        return mUserDao.getUser(userId);
    }

    @Override
    public List<User> getUsers(UserListener listener) {
        return mUserDao.getUsers();
    }

    @Override
    public void deleteAll() {
        mUserDao.deleteAll();
    }

    @Override
    public List<User> search(String q) {
        return mUserDao.search(q);
    }
}
