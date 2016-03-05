package com.zxzx74147.devlib.utils;


import android.os.AsyncTask;

/**
 * Created by zhengxin on 15/5/15.
 */
public class AsyncHelper {
    public final static AsyncTask executeAsyncTask(BDTask task) {
        InnerAsyncTask<?> mInnerTask = new InnerAsyncTask(task);
        mInnerTask.execute();
        return mInnerTask;
    }

    public interface BDTask<T> {
        T executeBackGround();

        void postExecute(T result);
    }

    private static class InnerAsyncTask<T> extends AsyncTask<String, String, T> {
        private BDTask<T> mBDTask = null;

        public InnerAsyncTask(BDTask<T> task) {
            mBDTask = task;
        }

        @Override
        protected T doInBackground(String... params) {
            return mBDTask.executeBackGround();
        }

        @Override
        protected void onPostExecute(T result) {
            mBDTask.postExecute(result);
        }
    }
}
