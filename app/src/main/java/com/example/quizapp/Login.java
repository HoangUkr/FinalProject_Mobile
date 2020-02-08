package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private UserDao userDao;
    private UserDatabase database;
    private ProgressDialog progressDialog;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = Room.databaseBuilder(this, UserDatabase.class, "user.db").allowMainThreadQueries().build();
        userDao = database.getUserDao();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking user");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        username = (EditText)findViewById(R.id.text1);
        password = (EditText)findViewById(R.id.text2);
        TextView registration = (TextView)findViewById(R.id.registration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRegistration(v);
            }
        });

        CardView login = (CardView)findViewById(R.id.cardView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyValidation()){
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.getUser(username.getText().toString(), password.getText().toString());
                            if(user != null){
                                Intent intent = new Intent(Login.this, Grade.class);
                                intent.putExtra("User",user);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, "Not found user", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);
                }else{
                    Toast.makeText(Login.this, "Empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void OpenRegistration(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    private boolean emptyValidation(){
        if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
            return true;
        }
        else {
            return false;
        }
    }
}
