package com.example.commentposter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.commentposter.adapter.UserAdapter;

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
        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        UserDAO userDAO = userDatabase.userDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<UserEntity> users = userDAO.getAllUsers();
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