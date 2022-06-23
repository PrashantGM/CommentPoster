package com.example.commentposter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
               UserEntity userEntity=new UserEntity();
               userEntity.setName(etName.getText().toString());
               userEntity.setEmail(etEmail.getText().toString());
               userEntity.setPassword(etPassword.getText().toString());
               userEntity.setDate(etDateRegistered.getText().toString());
               if(validateInput(userEntity)){
                   UserDatabase userDatabase=UserDatabase.getUserDatabase(getApplicationContext());
                   final UserDAO userDAO=userDatabase.userDAO();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           userDAO.registerUser(userEntity);
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                               }
                           });

                       }
                   }).start();
               }
               else{
                   Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private Boolean validateInput(UserEntity userEntity){
        if(userEntity.getName().isEmpty()|| userEntity.getEmail().isEmpty()||
                userEntity.getPassword().isEmpty()|| userEntity.getDate().isEmpty()){
                return false;
        }
        return true;
    }
}