package com.ngopidevteam.pranadana.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.register:
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                break;
        }
    }
}
