package com.example.commentposter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                    startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("name","Admin").putExtra("type","adminUser").putExtra("email",email));
                }
                else
                {
                    UserDatabase userDatabase=UserDatabase.getUserDatabase(getApplicationContext());
                    UserDAO userDAO=userDatabase.userDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity=userDAO.login(email,password);
                            if(userEntity==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                String name=userEntity.name;
                                String email=userEntity.email;

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
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }
}