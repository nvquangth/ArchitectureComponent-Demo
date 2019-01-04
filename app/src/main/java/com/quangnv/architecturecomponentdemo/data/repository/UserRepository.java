package com.quangnv.architecturecomponentdemo.data.repository;

import android.os.AsyncTask;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.source.UserDataSource;
import com.quangnv.architecturecomponentdemo.data.source.UserListener;

import java.util.List;

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
    public void insert(User user) {
        new InsertAsyncTask(mLocal).execute(user);
    }

    @Override
    public void update(User user) {
        mLocal.update(user);
    }

    @Override
    public void delete(User user) {
        mLocal.delete(user);
    }

    @Override
    public User getUser(int userId) {
        return mLocal.getUser(userId);
    }

    @Override
    public List<User> getUsers(UserListener listener) {
        new GetAllAsyncTask(mLocal, listener).execute();
        return null;
    }

    @Override
    public void deleteAll() {
        mLocal.deleteAll();
    }

    @Override
    public List<User> search(String q) {
        return mLocal.search(q);
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDataSource.Local mLocal;

        public InsertAsyncTask(UserDataSource.Local local) {
            mLocal = local;
        }

        @Override
        protected Void doInBackground(User... users) {
            mLocal.insert(users[0]);
            return null;
        }
    }

    private static class GetAllAsyncTask extends AsyncTask<Void, Void, List<User>> {

        private UserDataSource.Local mLocal;
        private UserListener mListener;

        public GetAllAsyncTask(UserDataSource.Local local, UserListener listener) {
            mLocal = local;
            mListener = listener;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return mLocal.getUsers(mListener);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            mListener.onGetAllUserComplete(users);
        }
    }
}
