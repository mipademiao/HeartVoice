package com.example.sss.heartvoice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import ks.common.utils.DLog;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_LOGIN = 2315;
     FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    TextView tv;
    ListView allmessage;
    String loginuseruid; // 取得登入者FIRBASE上的UID
    ArrayAdapter<String> adapter,adapter2; //FOR Listview
    Button btn_write, btn_signout;
    Context m_this = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();
        DLog.d("Firebase - Token: " + token);

        tv=(TextView) findViewById(R.id.tv_userid);
        tv.setText("hi");

        auth =FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user==null) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_LOGIN);

        }else{
            tv.setText(user.getEmail().toString());
            // TODO after login
        }
        btn_signout = (Button) findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_LOGIN);
            }
        });
        btn_write= (Button) findViewById(R.id.write_btn);
        btn_write. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("uid", loginuseruid);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        allmessage= (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(m_this,android.R.layout.simple_list_item_1,android.R.id.text1);
        adapter2 = new ArrayAdapter<String>(m_this,android.R.layout.simple_list_item_1,android.R.id.text1);
        allmessage.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages" );// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                adapter.clear();
                adapter2.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    adapter.add(dataSnapshot.child(child.getKey()).child("text").getValue(String.class));
                    adapter2.add(dataSnapshot.child(child.getKey()).getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "讀取失敗", Toast.LENGTH_SHORT).show();
            }
        });

        allmessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent textintent = new Intent(MainActivity.this, ReadActivity.class);
                Bundle textbundle=new Bundle();
                textbundle.putString("key", adapter2.getItem(arg2));
//                Log.d("arg2",""+arg2);
//                Log.d("key",adapter2.getItem(arg2));
                textintent.putExtras(textbundle);
                startActivity(textintent);
            }
        });
//        以上有用


//        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("message");
//        reference_contacts.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                adapter.clear();
//                for (DataSnapshot ds : dataSnapshot.getChildren() ){
//                    adapter.add(ds.getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode != RESULT_OK){
                    finish();
                }
                else{
                    auth =FirebaseAuth.getInstance();

                    FirebaseUser user = auth.getCurrentUser();
                    tv.setText(user.getEmail().toString()+"hello");
                    loginuseruid=user.getUid();
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        //auth.signOut();
        super.onDestroy();
    }
}
