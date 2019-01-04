package com.quangnv.architecturecomponentdemo.data.source;

import com.quangnv.architecturecomponentdemo.data.model.User;

import java.util.List;

public interface UserListener {

    void onGetAllUserComplete(List<User> users);
}