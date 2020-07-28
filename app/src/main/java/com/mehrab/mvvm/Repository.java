package com.mehrab.mvvm;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private RetrofitDao api;
    private PostDao postDao;
    private LiveData<List<Post>> allPosts;

    private static Repository repository;

    private Repository(Application application){
        api=RetrofitService.getInterface();
        postDao=PostDatabase.getInstance(application).postDao();
        allPosts=postDao.getAllPosts();
    }

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public LiveData<List<Post>> getAllPosts() {
        fetchPosts(); //fetching data from server
        return allPosts;
    }

    //This method fetches data from server and stores it locally through the insert() method
    public void fetchPosts() {
        final MutableLiveData<List<Post>> data=new MutableLiveData<>();
        api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.code() == 200) {
                    List<Post> temp = response.body();
                    data.setValue(response.body());
                    deleteAllPosts();
                    for(int i=0;i<response.body().size();i++)
                        insert(response.body().get(i));
                    //Log.d(TAG + "res", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Log.d("errorrr", t.getMessage());
            }
        });
        //return data;
    }

    public void insert(Post post) {
        new InsertPostAsyncTask(postDao).execute(post);
    }
    public void update(Post post) {
        new UpdatePostAsyncTask(postDao).execute(post);
    }
    public void delete(Post post) {
        new DeletePostAsyncTask(postDao).execute(post);
    }
    public void deleteAllPosts() {
        new DeleteAllPostsAsyncTask(postDao).execute();
    }
    private static class InsertPostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;
        private InsertPostAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            postDao.insert(posts[0]);
            return null;
        }
    }
    private static class UpdatePostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;
        private UpdatePostAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            postDao.update(posts[0]);
            return null;
        }
    }
    private static class DeletePostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;
        private DeletePostAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            postDao.delete(posts[0]);
            return null;
        }
    }
    private static class DeleteAllPostsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostDao postDao;
        private DeleteAllPostsAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            postDao.deleteAllPosts();
            return null;
        }
    }
}