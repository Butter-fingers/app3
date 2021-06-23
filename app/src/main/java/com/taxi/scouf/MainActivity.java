package com.taxi.scouf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    PhoneAuthCredential phoneAuthCredential;
    Button button;
    CountryCodePicker ccps;
    ArrayList list;
    Button next;
    TextInputEditText phone;
    ProgressDialog progressDialog;
    String realNum;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ccps = (CountryCodePicker) findViewById(R.id.ccp);
        phone = (TextInputEditText) findViewById(R.id.phone);
        next = (Button) findViewById(R.id.button);
        //realNum = phone.getText().toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            try {
                Intent intent ;
                System.out.println("try");
                Database db = new Database(getApplicationContext());
                System.out.println("past");
                ArrayList<String> arrayList = db.getData(0);
                ArrayList<String> reg = db.getData(5);
                System.out.println("past2");
                //remove null
                arrayList.removeAll(Collections.singleton(null));
                reg.removeAll(Collections.singleton(null));
                String driver = null;
                try {

                    driver = arrayList.get(0);
                }catch (Exception e) {
                    System.out.println("Exception: "+ e);
                }


                //String lane = arrayList2.get(0);
                
                if (reg.isEmpty()) {
                    //if the reg is empty then user needs to add
                    System.out.println("Register");
                    intent = new Intent(getApplicationContext(), RegisterUser.class);
                    startActivity(intent);
                    finish();
                } else if (driver != null) {
                    //if the driver is not empty then to the dash board
                    System.out.println("Dash" + arrayList) ;
                    intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("user", 1);
                    startActivity(intent);
                    finish();
                } else if (driver == null) {
                    //if empty then edit
                    System.out.println("Driver");
                    intent = new Intent(getApplicationContext(), DriverMain.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                System.out.println("auth !=null : " + e);
            }

        } else {
            System.out.println("next");
            next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String num = phone.getText().toString();
                    String code = ccps.getSelectedCountryCodeWithPlus();
                    // check if the country code is selected

                    if (!num.isEmpty() ) {
                        //country code plus the number
                        realNum = code + num ;

                        sendCode(realNum);
                    } else {
                        Toast.makeText(MainActivity.this, "Enter Number", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }





    }

    public void sendCode(String phone){
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setTitle(R.string.title);
        dialogBuilder.setMessage(phone);
        dialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //continue to send the code
                progressDialog.show();
                sendVerification(phone);

            }
        }).setNegativeButton("Edit", null);
        dialogBuilder.show();

    }

    private void sendVerification(String phone) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                System.out.println("Verification successful");

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.cancel();
                System.out.println("verification failed " + e);
                Toast.makeText(MainActivity.this, "Failed to verify", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent intent = new Intent(getApplicationContext(), Verify.class);
                progressDialog.cancel();
                intent.putExtra("number", realNum);
                intent.putExtra("id", s);

                startActivity(intent);
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}