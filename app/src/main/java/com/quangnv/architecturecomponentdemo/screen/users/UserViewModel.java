package com.quangnv.architecturecomponentdemo.screen.users;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;

import java.util.List;

/**
 * Created by quangnv on 11/01/2019
 */

public class UserViewModel extends ViewModel {

    private UserRepository mRepository;

    public LiveData<List<User>> getUsers() {
        return mRepository.getUsers();
    }

    public void setRepository(UserRepository repository) {
        mRepository = repository;
    }
}
