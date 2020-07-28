package com.mehrab.mvvm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitDao {
    @GET("posts")
    Call<List<Post>> getPosts();
}
