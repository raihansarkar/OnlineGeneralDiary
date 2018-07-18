package com.example.raihansarkar.onlinegeneraldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminUser;
    EditText adminPassword;
    Button btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUser= (EditText) findViewById(R.id.adminUserId);
        adminPassword= (EditText) findViewById(R.id.adminPasswordId);
        btnAdminLogin= (Button) findViewById(R.id.adminLoginBtn);



        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=adminUser.getText().toString();
                String password=adminPassword.getText().toString();

                login(username, password);
            }
        });
    }

    public void login(String username, String password){

        Log.d("login", "admin: "+username+" password: "+password);

        if(username.equals("Mirpur") && password.equals("12345")){
            Intent intent=new Intent(AdminLoginActivity.this, UpdatePage.class);
            intent.putExtra("Username",username);
            startActivity(intent);
        }else if (username.equals("Ramna") && password.equals("67890")){
            Intent intent=new Intent(AdminLoginActivity.this, UpdatePage.class);
            intent.putExtra("Username",username);
            startActivity(intent);
        }
        else{
            Toast.makeText(getBaseContext(),"DO not match password or user",Toast.LENGTH_LONG).show();
        }
    }
}
