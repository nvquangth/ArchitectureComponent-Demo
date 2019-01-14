package com.quangnv.architecturecomponentdemo.data.source;

import android.arch.lifecycle.LiveData;

import com.quangnv.architecturecomponentdemo.data.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserDataSource {

    interface Local {

        Completable insert(User user);

        Completable update(User user);

        Completable delete(User user);

        Observable<User> getUser(int userId);

        LiveData<List<User>> getUsers();

        Completable deleteAll();

        Observable<List<User>> search(String q);
    }
}
