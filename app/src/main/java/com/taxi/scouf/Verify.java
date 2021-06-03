package com.taxi.scouf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Verify extends AppCompatActivity {

    String Scode;
    Button button;
    TextInputEditText editcode;
    Button next;
    String number;
    ProgressDialog progressDialog;
    TextView textview2;
    TextView textview3;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        editcode = (TextInputEditText) findViewById(R.id.Ucode);

        number = getIntent().getStringExtra("number");
        next = (Button) findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        textview2 = (TextView) findViewById(R.id.textviewss);
        textview3 = (TextView) findViewById(R.id.textView3);
        textview2.append(" " + number);
        id = getIntent().getStringExtra("id");


        textview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent var2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(var2);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editcode.getText().toString();

                if(!code.isEmpty()) {
                    progressDialog.show();
                    verify(code);

                } else {
                    Toast.makeText(Verify.this, "Enter code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verify(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, code);

        signInWithPhoneCredential(credential);


    }

    private void signInWithPhoneCredential(PhoneAuthCredential credential) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                System.out.println("Sign in success");
                Intent var2 = new Intent(getApplicationContext(), RegisterUser.class);
                progressDialog.cancel();
                startActivity(var2);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Sign in unsuccessful  " + e);
                button.setVisibility(View.VISIBLE);
                Toast.makeText(Verify.this, "Invalid OTP", Toast.LENGTH_SHORT).show();



            }
        });
    }
}