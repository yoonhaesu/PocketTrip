package com.example.pockettrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Join extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        //Intent intent = getIntent();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");

        final EditText idText = (EditText)findViewById((R.id.idText));
        final EditText pwText = (EditText)findViewById((R.id.pwText));
        final EditText nameText = (EditText)findViewById((R.id.nameText));

        Button joinBtn = (Button)findViewById(R.id.joinBtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idText.getText().toString().equals(""))
                    Toast.makeText(Join.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                else if(pwText.getText().toString().equals(""))
                    Toast.makeText(Join.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                else if(nameText.getText().toString().equals(""))
                    Toast.makeText(Join.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(idText.getText().toString()).exists()) //아이디 중복시
                            {
                                Toast.makeText(Join.this, "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                UserDTO user = new UserDTO(pwText.getText().toString(), nameText.getText().toString());
                                myRef.child(idText.getText().toString()).setValue(user);
                                finish();

                                Intent myintent = new Intent(Join.this,MainActivity.class);
                                startActivity(myintent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }
}
