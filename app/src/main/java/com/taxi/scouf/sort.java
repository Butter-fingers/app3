package com.taxi.scouf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class sort extends AppCompatActivity {
    RadioButton[] buttons;
    boolean checked;
    ArrayList<String> drivers;
    ArrayList<String> drivers_clone;
    BottomNavigationView extendedFloatingActionButton;
    int number_line;
    int position;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    int Selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        radioGroup = (RadioGroup) findViewById(R.id.radio_1);
        extendedFloatingActionButton = (BottomNavigationView) findViewById(R.id.float__1);
        drivers = new ArrayList<>();
        drivers_clone = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        number_line = 1;
        drivers = getIntent().getStringArrayListExtra("drivers");
        position = getIntent().getExtras().getInt("item");

        buttons = new RadioButton[drivers.size()];

        for (int i = 0; i < drivers.size(); i++) {
            buttons[i] = new RadioButton(getApplicationContext());
            buttons[i].setText(number_line + ". "+ drivers.get(i));
            radioGroup.addView(buttons[i]);
            number_line++;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checked = true;
                //position = checkedId;
            }
        });

       extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                progressDialog.show();
                drivers_clone = (ArrayList<String>) drivers.clone();
                int selected = radioGroup.getCheckedRadioButtonId();
                int AL = drivers.size();

                if (selected >= 0) {

                    for (int i = 0; i < AL - selected; i++) {
                        drivers_clone.set(i, drivers.get(selected +i ));
                    }

                    for (int i = 0 ; i < selected ; i ++ ) {
                        drivers_clone.set(i + (AL - selected), drivers.get(i));
                    }

                    Intent intent = new Intent(getApplicationContext(), lock.class);
                    intent.putStringArrayListExtra("drivers", drivers_clone);
                    intent.putExtra("item", position);
                    progressDialog.cancel();
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(sort.this, "Select a driver", Toast.LENGTH_SHORT).show();
                }


           }
       });
    }
}