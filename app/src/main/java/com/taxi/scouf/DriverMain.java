package com.taxi.scouf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class DriverMain extends AppCompatActivity {
    MaterialButton add;
    ArrayList<String> array;
    ArrayList<String> array1;
    ArrayList<ArrayList<String>> array2;
    ArrayAdapter arrayAdapter;
    int columns;
    private CollectionReference documentReference2;
    TextInputEditText drive_NAME;
    TextView driver_view;
    ArrayList<String> drivers, names, lane;
    TextInputLayout inputLayout;
    MaterialButton next;
    int number;
    int pos;
    ProgressDialog progressDialog;
    String rank_NAME;
    MaterialButton remove;
    AutoCompleteTextView spinner;
    boolean update;
    String user_NAME;
    Database db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        array = new ArrayList<>();
        array1 = new ArrayList<>();
        array2 = new ArrayList<ArrayList<String>>();
        drivers = new ArrayList<>();
        drive_NAME = (TextInputEditText) findViewById(R.id.driver_name);
        driver_view = (TextView) findViewById(R.id.driver_view);
        inputLayout = (TextInputLayout) findViewById(R.id.input_layout);
        add = (MaterialButton) findViewById(R.id.add);
        next = (MaterialButton) findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        remove = (MaterialButton) findViewById(R.id.removes);
        spinner = (AutoCompleteTextView) findViewById(R.id.spinner);

        number = 1;
        columns = 5;
        //always get from the db
        db = new Database(getApplicationContext());

        names = db.getData(5);
        lane = db.getData(6);
        //remove the null
        //names.removeAll(Collections.singleton(null));

        user_NAME = names.get(0);
        rank_NAME = names.get(1);
        update = getIntent().getBooleanExtra("update", false);
        //array = getIntent().getStringArrayListExtra("lane_names");
        db = new Database(getApplicationContext());
        array = db.getData(5);
        //remove null
        array.removeAll(Collections.singleton(null));

        // set array2 to be the size of array(lanes)
        for (int i = 0 ; i < array.size(); i++) {
            ArrayList<String> arrayList = new ArrayList<>();
            array1 = arrayList;
            array2.add(arrayList);

        }

        if (update) {


            db = new Database(getApplicationContext());

            array = db.getData(5);
            //remove null
            array.removeAll(Collections.singleton(null));

            for (int i = 0 ; i < array.size(); i++) {
                array2.set(i, db.getData(i));

            }

        }


        //if array is more than 5 remove rest
        if (array.size() > 5) {
            for (int i =0; i < array.size(); i++) {
                if (i > 4) {
                    array.remove(i);
                }
            }
        }

        //add arrays
        //for (int)



        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, array);
        //arrayAdapterr = arrayAdapter;
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
//                inputLayout.setBoxStrokeColor(R.color.red);
                driver_view.setText("");
                drivers = new ArrayList<>();

                if (update) {
                    for (int i = 0; i < array2.size(); i++) {
                        array2.get(i).removeAll(Collections.singleton(null));
                    }
                }

                System.out.println("Array2: " + array2 + " :" + position);
                if (array2.get(position).toString() != "null") {
                    System.out.println("nottttt");
                    for (int i = 0; i < array2.get(position).size(); i++) {
                        drivers.add(array2.get(position).get(i));
                    }

                    for (int i = 0 ; i <drivers.size(); i++) {
                        driver_view.append(number + ". " + drivers.get(i) + "\n");
                        number++;
                    }
                } else {
                    for (int i = 0; i < array2.get(position).size(); i++) {
                        drivers.add(array2.get(position).get(i));
                    }
                }


                number = 1;
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driver_view.setText("");
                if (!drivers.isEmpty()) {
                    int last = drivers.size() - 1;
                    drivers.remove(last);

                    for (int i = 0; i < drivers.size(); i++) {
                        driver_view.append(number + ". " + drivers.get(i) + "\n");
                        number++;
                    }

                    number = 1;
                    drive_NAME.setText("");
                    array2.get(pos).remove(last);

                 }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = drive_NAME.getText().toString();
                if (!name.isEmpty()) {
                    driver_view.setText("");

                    if (drivers.size() < 50) {
                        drivers.add(name);


                        for (int i = 0; i <drivers.size(); i++) {
                            driver_view.append(number + ". " + drivers.get(i) + "\n");
                            number++;

                        }

                        number = 1;
                        drive_NAME.setText("");
                        array2.get(pos).add(name);
                        remove.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(DriverMain.this, "only up to 50 drivers allowed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fi = 0;

                int sec = -1;


                try {
                    //check if array 2 has values inside/ not null
                    sec = 0;
                    for (int i = 0; i < array.size(); i++ ) {
                        System.out.println("array: " + array + ", array2: "+ array2);
                        // boolean ch = !array2.get(i).isEmpty(); or check if it > 0
                        if (!array2.get(i).isEmpty()) {

                            System.out.println("not null: "+ sec + "i: " + i);
                            sec ++;

                        }

                    }

                } catch (Exception e) {
                    System.out.println("Drive next except: "+ e);
                }


                System.out.println("array.sizess: "+ array.size() + ", sec: " + sec);
                if ((array.size() - sec) == 0) {
                    //inputLayout.setBoxStrokeColor();
                    progressDialog.show();
                    Database db = new Database(getApplicationContext());

                    // add the drivers
                    if (!update) {
                        for (int i = 0; i < array.size(); i++) {
                            db.addOne(array2.get(i), i , i);
                        }
                    } else {
                        //update
                        for (int i = 0; i < array.size(); i++) {
                            db.delete(array2.get(i), i);
                        }
                    }


                    // add the lanes

                    //db.addOne(array,5, false);
                    Intent var3 = new Intent(getApplicationContext(), Dashboard.class);
                    progressDialog.cancel();
                    startActivity(var3);

                    /*for ( int i =0; i < array.size(); i++) {
                        System.out.println("username: " + user_NAME);
                       documentReference2 =  FirebaseFirestore.getInstance().collection("users").document(user_NAME).collection("rank").document(rank_NAME).collection(array.get(i));
                        HashMap map = new HashMap();

                        for (int z = 0; z < array2.get(i).size(); z++) {
                            map.put(array.get(i)+z, array2.get(i).get(z));

                        }

                        if (i == (array.size() - 1)) {
                            documentReference2.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    // go to the next activity
                                    try {
                                        Intent var3 = new Intent(getApplicationContext(), Dashboard.class);
                                        progressDialog.cancel();
                                        startActivity(var3);
                                    } catch (Exception var2) {
                                        System.out.println(var2);
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(DriverMain.this, "submission failed ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            documentReference2.add(map);
                        }

                    }*/

                } else {
                    Toast.makeText(DriverMain.this, "Add Drivers to all lanes", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}