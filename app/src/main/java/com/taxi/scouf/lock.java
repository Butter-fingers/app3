package com.taxi.scouf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class lock extends AppCompatActivity {
    int AL;
    int ALF;
    int ALL;
    int ALLL;
    int checked;
    RadioGroup chipgroup;
    ArrayList<String> drivers;
    ArrayList<String> drivers_clone;
    ArrayList<String> drivers_clone2;
    BottomNavigationView floatingActionButton;
    int number;
    int number_line;
    String oneO;
    int origin;
    int out;
    int outF;
    int position;
    ProgressDialog progressDialog;
    Switch[] switches;
    int theI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        floatingActionButton = (BottomNavigationView) findViewById(R.id.floating_3);
        chipgroup = (RadioGroup) findViewById(R.id.radio_2);
        drivers = new ArrayList<>();
        drivers_clone = new ArrayList<>();
        drivers_clone2 = new ArrayList<>();
        number_line = 1;
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        checked = 0 ;
        // the alg should be int the listener



        drivers = getIntent().getStringArrayListExtra("drivers");
        position = getIntent().getExtras().getInt("item");
        switches = new Switch[drivers.size()];
        number_line = 1;

        for (int i = 0; i < drivers.size(); i++) {
            switches[i] =  new Switch(this);
            switches[i].setText(number_line + ". "+ drivers.get(i));
            chipgroup.addView(switches[i]);
            number_line++;
        }

        floatingActionButton.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                for (int i = 0 ; i< drivers.size(); i++) {

                    dialog.show();
                    drivers_clone = (ArrayList<String>) drivers.clone();
                    drivers_clone2 = (ArrayList<String>) drivers.clone();

                    AL = drivers.size();
                    number = 1;
                    if (switches[i].isChecked()) {

                        int last = number;
                        int L = i;
                        String oneO = drivers_clone2.get(i);

                        int ALL = AL - i ;
                        int ALLL = AL - number - ALL;
                        int theI = i - ALLL;
                        int ALF = AL - i;
                        int outF = i - number;

                        if (checked > 0) {
                            oneO = drivers_clone2.get(i - checked).toString();

                        } else {
                            oneO = drivers_clone2.get(i).toString();
                        }

                        if (checked >0) {
                            for (int k = 0 ; k <(i - checked) ; k++) {
                                drivers_clone.set(k, drivers_clone2.get(k));

                            }
                        } else  {
                            for (int p = 0 ; p < i ; p ++) {
                                drivers_clone.set(p, drivers_clone2.get(p));

                            }
                        }

                        if (checked > 0) {
                            for (int h = 0 ; h < drivers_clone.size() - i + (checked - number); h++) {
                                drivers_clone.set(i + h, drivers_clone2.get(i + h - (checked - number)));

                            }
                        } else {
                            for (int j = 0; j < drivers_clone.size() - (i - number); j ++) {
                                drivers_clone.set(j+i, drivers_clone2.get(j+ i + number));

                            }
                        }

                        drivers_clone.set(AL - number, oneO);
                        drivers_clone2 = (ArrayList<String>) drivers_clone.clone();
                        checked++;
                    }
                }

                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putStringArrayListExtra("new", drivers_clone);
                intent.putExtra("item", position);
                Database db = new Database(getApplicationContext());
                db.delete(drivers_clone, position);
                progressDialog.cancel();
                startActivity(intent);
                finish();

                return true;
            }
        });







    }
}