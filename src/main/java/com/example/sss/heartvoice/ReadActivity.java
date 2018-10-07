package com.example.sss.heartvoice;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadActivity extends AppCompatActivity {
    Button btn_submit;
    TextView tv_context;
    ListView allcomment;
    ArrayAdapter<String> adapter;
    EditText et_comment;
    String key ;
    String context;
    int count;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("messages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tv_context = (TextView) findViewById(R.id.tv_context);
        allcomment = (ListView) findViewById(R.id.lv_comment) ;
        et_comment =(EditText) findViewById(R.id.et_comment);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        Bundle bundle=getIntent().getExtras();
        key = bundle.getString("key");
        ValueEventListener contextListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                context= dataSnapshot.getValue(String.class);
                Log.d("context",context);
                tv_context.setText(context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child(key).child("text").addValueEventListener(contextListener);
        //tv_context.setText(context);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        allcomment.setAdapter(adapter);

        myRef.child(key).child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    adapter.add(dataSnapshot.child(child.getKey()).getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference commentRef = myRef.child(key).child("comment");;
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        count = (int)dataSnapshot.getChildrenCount();
                        myRef.child(key).child("comment").child(""+count).setValue(et_comment.getText().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                commentRef.addListenerForSingleValueEvent(postListener);
            }
        });
    }
}
