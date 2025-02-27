package com.example.belajarapijsonplaceholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);

        Gson gson = new GsonBuilder().serializeNulls().create(); //di log muncul sesuai format json dari url

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();   //log http request
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        getPosts();
//        getComments();
//        createPost();
//        updatePost();
//        deletePost();
    }

    private void deletePost() {
        Call<Void> call = apiInterface.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                tvResult.setText("Code: "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12,null,"New Text");

        Call<Post> call = apiInterface.putPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();

                String content="";
                content +="Code: "+response.code()+"\n";
                content +="ID: "+postResponse.getId()+"\n";
                content +="User ID: "+postResponse.getUserId()+"\n";
                content +="Title: "+postResponse.getTitle()+"\n";
                content +="Text: "+postResponse.getText()+"\n";
                tvResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
//        for createPost(@Body Post post)
//        Post post = new Post(23, "New Title", "New Text");

        //for createPost(@fieldMap)
        Map<String,String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");



        Call<Post> call = apiInterface.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();

                String content ="";
                content +="Code: "+response.code()+"\n";
                content +="ID: "+postResponse.getId()+"\n";
                content +="User ID: "+postResponse.getUserId()+"\n";
                content +="Title: "+postResponse.getTitle()+"\n";
                content +="Text: "+postResponse.getText()+"\n";

                tvResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = apiInterface.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code: "+response.code());
                    return;
                }
                List<Comment> comments = response.body();

                for (Comment comment:comments){
                    String content ="";
                    content +="ID: "+comment.getId()+"\n";
                    content +="Post ID: "+comment.getPostId()+"\n";
                    content +="Name: "+comment.getName()+"\n";
                    content +="Email: "+comment.getEmail()+"\n";
                    content +="Text: "+comment.getText()+"\n"+"\n";

                    tvResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","asc");

        Call<List<Post>> call = apiInterface.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code : "+ response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post:posts){
                    String content ="";
                    content += "ID: "+post.getId()+"\n";
                    content +="User ID: "+post.getUserId()+"\n";
                    content +="Title: "+post.getTitle()+"\n";
                    content +="Text: "+post.getText()+"\n"+"\n";

                    tvResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }
}
