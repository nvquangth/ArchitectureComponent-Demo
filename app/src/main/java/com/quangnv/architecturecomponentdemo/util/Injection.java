package com.quangnv.architecturecomponentdemo.util;

import android.content.Context;

import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.data.source.local.UserLocalDataSource;
import com.quangnv.architecturecomponentdemo.data.source.local.sqlite.UserDatabase;

public class Injection {

    public static UserRepository provideUserRepository(Context context) {
        UserDatabase db = UserDatabase.getInstance(context);
        return UserRepository.getInstance(UserLocalDataSource.getInstance(db.userDao()));
    }
}
