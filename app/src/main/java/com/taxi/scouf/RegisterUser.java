package com.taxi.scouf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    MaterialButton add_lane;
    private DocumentReference documentReference;
    ArrayList<String> lane_NAMES;
    AppCompatTextView lane_Views;
    ArrayList<String> lane_names;
    int lane_size;
    Button next;
    int number = 1;
    ProgressDialog progressDialog;
    TextInputEditText rank;
    String rank_NAME;
    MaterialButton removelane;
    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;
    String user_NAME;
    TextInputEditText user_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        add_lane = (MaterialButton) findViewById(R.id.add);
        removelane = (MaterialButton) findViewById(R.id.remove);
        lane_Views = (AppCompatTextView) findViewById(R.id.lane_view);
        rank = (TextInputEditText) findViewById(R.id.rank_name);
        textInputEditText = (TextInputEditText) findViewById(R.id.lane);
        user_name = (TextInputEditText) findViewById(R.id.username);
        textInputLayout = (TextInputLayout) findViewById(R.id.text_Input);
        textInputLayout = (TextInputLayout) findViewById(R.id.input_layout);
        next = (Button) findViewById(R.id.button);

        lane_NAMES = new ArrayList<>();
        lane_names = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");


        add_lane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lane = textInputEditText.getText().toString();

                if (!lane.isEmpty()) {
                    if (lane_names.size() < 5) {
                        lane_names.add(lane);
                        lane_Views.setText("");

                        for (String lanez : lane_names) {
                            lane_Views.append(number + ". " + lanez + "\n");
                            number++;
                        }

                        number = 1;
                        textInputEditText.setText("");
                        removelane.setVisibility(View.VISIBLE);

                    } else if (lane.isEmpty()) {
                        removelane.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(RegisterUser.this, "You can only use 5 lanes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        removelane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lane_names.isEmpty()) {
                    lane_Views.setText("");
                    int last = lane_names.size();
                    lane_names.remove(last - 1);
                }

                for (String lanez : lane_names) {
                    lane_Views.append(number + ". " + lanez + "\n");
                    number++;
                }

                number = 1;
                textInputEditText.setText("");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rank_NAME = rank.getText().toString();
                user_NAME = user_name.getText().toString();

                if (!user_NAME.isEmpty() && !rank_NAME.isEmpty() && !lane_names.isEmpty()) {
                    progressDialog.show();
                    Intent intent = new Intent(RegisterUser.this, DriverMain.class);
                    ArrayList<String> names = new ArrayList<>();
                    intent.putStringArrayListExtra("lane_names", lane_names);
                    names.add(user_NAME);
                    names.add(rank_NAME);
                    intent.putExtra("userName", user_NAME);
                    intent.putExtra("rankName", rank_NAME);
                    intent.putExtra("laneSize", lane_size);
                    intent.putStringArrayListExtra("lane_names", lane_NAMES);
                    //put em in the database
                    Database db = new Database(getApplicationContext());
                    db.addOne(lane_names,5,false);
                    db.addOne(names,6,false);
                    progressDialog.cancel();

                    startActivity(intent);
                } else {

                    Toast.makeText(RegisterUser.this, "Please fill up empty fields", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterUser.this, "add lanes", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}