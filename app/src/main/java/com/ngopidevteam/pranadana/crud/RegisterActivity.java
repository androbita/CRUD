package com.ngopidevteam.pranadana.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edtemail)
    EditText edtemail;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.btnregister)
    Button btnregister;

    //declare firebase authentication
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btnregister)
    public void onViewClicked() {

        String email = edtemail.getText().toString().trim();
        String pass = edtpassword.getText().toString().trim();
        
        if (email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else if (pass.length() < 6){
            Toast.makeText(this, "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show();
        }else {
            registerUser(email, pass);
        }
    }

    private void registerUser(final String email, final String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", id);
                    map.put("email", email);
                    map.put("pass", pass);

                    reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    });

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
