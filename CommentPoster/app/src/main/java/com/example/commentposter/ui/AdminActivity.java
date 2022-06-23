package com.example.commentposter.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.adapter.UserAdapter;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadUsers();

   }
    public void loadUsers(){
        CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getApplicationContext());
        UserDAO userDAO = commentPosterDB.userDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> users = userDAO.getAllUsers();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserAdapter userAdapter = new UserAdapter(users);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
            }
        }).start();
    }
}