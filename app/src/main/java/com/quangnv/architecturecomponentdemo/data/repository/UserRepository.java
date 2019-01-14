package com.quangnv.architecturecomponentdemo.data.repository;

import android.arch.lifecycle.LiveData;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.source.UserDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class UserRepository implements UserDataSource.Local {

    private static UserRepository sInstance;
    private UserDataSource.Local mLocal;

    private UserRepository(UserDataSource.Local local) {
        mLocal = local;
    }

    public static UserRepository getInstance(UserDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new UserRepository(local);
        }
        return sInstance;
    }

    @Override
    public Completable insert(User user) {
        return mLocal.insert(user);
    }

    @Override
    public Completable update(User user) {
        return mLocal.update(user);
    }

    @Override
    public Completable delete(User user) {
        return mLocal.delete(user);
    }

    @Override
    public Observable<User> getUser(int userId) {
        return mLocal.getUser(userId);
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return mLocal.getUsers();
    }

    @Override
    public Completable deleteAll() {
        return mLocal.deleteAll();
    }

    @Override
    public Observable<List<User>> search(String q) {
        return mLocal.search(q);
    }
}
