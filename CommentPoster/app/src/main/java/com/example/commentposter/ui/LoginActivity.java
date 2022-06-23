package com.example.commentposter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;
    TextView tvlSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvlSignup=findViewById(R.id.tvlSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();

                if(email.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter both email and password!", Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("admin@gmail.com") && password.equals("admin@123")){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("name","Admin").putExtra("type","adminUser").putExtra("email",email));
                }
                else
                {
                    CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getApplicationContext());
                    UserDAO userDAO= commentPosterDB.userDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user =userDAO.login(email,password);
                            if(user ==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                String name= user.name;
                                String email= user.email;

                                startActivity(new Intent(LoginActivity.this,MainActivity.class)
                                        .putExtra("name",name).putExtra("type","normalUser").putExtra("email",email));
                            }
                        }
                    }).start();

                }

            }

        });

        tvlSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}