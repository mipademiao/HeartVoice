package com.example.sss.heartvoice;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteActivity extends AppCompatActivity {
EditText mymessage;
    Button subm;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Bundle bundle=getIntent().getExtras();
        uid=bundle.getString("uid");
        subm= (Button) findViewById(R.id.btn_sub);
        mymessage= (EditText) findViewById(R.id.messagetext);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("messages");
            Message m= new Message(uid, mymessage.getText().toString());
            myRef.push().setValue(m).addOnCompleteListener(WriteActivity.this, new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(WriteActivity.this, "Write FAIL", Toast.LENGTH_SHORT).show();
                    }
                    else
                        finish();
                }
            });
        }
    });


        }

}

