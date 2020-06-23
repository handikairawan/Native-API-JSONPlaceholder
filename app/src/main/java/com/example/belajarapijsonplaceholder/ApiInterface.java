package com.example.belajarapijsonplaceholder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    //1.get posts
//    @GET("posts")
//    Call<List<Post>> getPosts();

    //or get post custom query
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") int[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    //or get post querymap hashmap
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String,String> parameters);

    //2.get comment
//    @GET("posts/1/comments")
//    Call<List<Comment>> getComments();

    //or get comment custom id
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    //or get comment from url
    @GET
    Call<List<Comment>> getComments(@Url String url);

    //3.create post, buat object post di MainActivity
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //or create post with formUrlEncode, ngisi value di parameters createPost MainActivity
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body
    );

    //create post field map, buat key,value di MainAct
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);

    //4.put post, buat update resource secara keseluruhan(nimpa)(replace)
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    //5.patch post, buat update resource, cuma beberapa bagian yang di update
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    //6.delete
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
