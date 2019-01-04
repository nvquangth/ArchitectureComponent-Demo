package com.quangnv.architecturecomponentdemo.data.source;

import com.quangnv.architecturecomponentdemo.data.model.User;

import java.util.List;

public interface UserDataSource {

    interface Local {

        void insert(User user);

        void update(User user);

        void delete(User user);

        User getUser(int userId);

        List<User> getUsers(UserListener listener);

        void deleteAll();

        List<User> search(String q);
    }
}
