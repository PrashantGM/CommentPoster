package com.example.commentposter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;

public class RegistrationActivity extends AppCompatActivity {

    EditText etEmail,etPassword,etName,etDateRegistered;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etName=findViewById(R.id.etName);
        btnSubmit=findViewById(R.id.btnSubmit);
        etDateRegistered=findViewById(R.id.etDateRegistered);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               User userEntity=new User();
               userEntity.setName(etName.getText().toString());
               userEntity.setEmail(etEmail.getText().toString());
               userEntity.setPassword(etPassword.getText().toString());
               userEntity.setDateRegistered(etDateRegistered.getText().toString());
               if(validateInput(userEntity)){
                   CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getApplicationContext());
                   final UserDAO userDAO= commentPosterDB.userDAO();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           Boolean check=userDAO.does_exist(etEmail.getText().toString());
                           if(check==false){
                               userDAO.registerUser(userEntity);
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {

                                       etName.setText("");
                                       etEmail.setText("");
                                       etPassword.setText("");
                                       etDateRegistered.setText("");
                                       Toast.makeText(getApplicationContext(), "Success! User Registered!", Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }
                           else{
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       etEmail.setText("");
                                       Toast.makeText(getApplicationContext(), "Error! This email is already registered!", Toast.LENGTH_LONG).show();
                                   }
                               });

                           }


                       }
                   }).start();
               }
               else{
                   Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_LONG).show();
               }
                finish();
//                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
    }

    private Boolean validateInput(User userEntity){
        if(userEntity.getName().isEmpty()|| userEntity.getEmail().isEmpty()||
                userEntity.getPassword().isEmpty()|| userEntity.getDateRegistered().isEmpty()){
                return false;
        }
        return true;
    }
}