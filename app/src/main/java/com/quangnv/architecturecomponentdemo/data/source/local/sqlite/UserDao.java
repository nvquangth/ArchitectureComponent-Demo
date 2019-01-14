package com.quangnv.architecturecomponentdemo.data.source.local.sqlite;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.quangnv.architecturecomponentdemo.data.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUser(int id);

    @Query("SELECT * FROM user WHERE first_name LIKE :q AND last_name LIKE :q")
    List<User> search(String q);
}
