package com.example.sss.heartvoice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText edEmail;
    EditText edPw;
    Button login_btn;
    Button reg_btn;
    public static final int REQUEST_LOGIN = 2315;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edEmail= (EditText) findViewById(R.id.ed_email);
        edPw= (EditText) findViewById(R.id.ed_pw);
        login_btn= (Button) findViewById(R.id.login_btn);
        reg_btn= (Button) findViewById(R.id.reg_btn);
        final FirebaseAuth auth = FirebaseAuth.getInstance();




        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegActivity.class), REQUEST_LOGIN);
            }
        });
login_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        auth.signInWithEmailAndPassword(edEmail.getText().toString(),
                edPw.getText().toString()).addOnSuccessListener(
                new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setResult(RESULT_OK);
                       finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        login_btn.setError("登入失敗，請檢查email/password");


                    }
                });




    }
});

    }
//    protected void creatAccount(){
//        startActivityForResult(new Intent(LoginActivity.this, RegActivity.class), REQUEST_LOGIN);
//    }
    }

