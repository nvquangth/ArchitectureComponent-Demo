package com.quangnv.architecturecomponentdemo.data.source.local.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.quangnv.architecturecomponentdemo.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase sInstance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "architecture.db").build();
        }
        return sInstance;
    }
}
