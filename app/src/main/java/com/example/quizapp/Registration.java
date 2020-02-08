package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText rpassword;
    public Button registration;

    private UserDao userDao;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainOpen(v);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        registration = (Button)findViewById(R.id.confirm);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        rpassword = (EditText)findViewById(R.id.rpassword);

        userDao = Room.databaseBuilder(this, UserDatabase.class, "user.db").allowMainThreadQueries().build().getUserDao();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyValidation()){
                    if(password.getText().toString() != rpassword.getText().toString()){
                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                User user = new User(username.getText().toString(), password.getText().toString());
                                userDao.insert(user);
                                progressDialog.dismiss();
                                Intent intent = new Intent(Registration.this, Login.class);
                                startActivity(intent);
                            }
                        },1000);
                    }
                    else {
                        Toast.makeText(Registration.this, "Retype password is not match with password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Registration.this, "Empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void MainOpen(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public boolean emptyValidation(){
        if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(rpassword.getText().toString())) {
            return true;
        }
        else {
            return false;
        }
    }
}
