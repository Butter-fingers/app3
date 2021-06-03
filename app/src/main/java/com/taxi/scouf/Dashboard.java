package com.taxi.scouf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    ArrayList<String> arrayList;
    ClipboardManager clipboardManager;
    ArrayList<String> contents;
    Map data;
    DocumentReference documentReference;
    ArrayList<ArrayList<String>> drivers;
    BottomNavigationView floatingActionButton;
    //ExtendedFloatingActionButton floatingActionButton2;
    ArrayList<String> lane_names;
    ListView listView;
    ArrayList<String> new_drivers;
    Intent next;
    ProgressDialog progressDialog;
    String rank_name;
    int real;
    TabLayout tabLayout;
    Toolbar toolbar;
    String user_name;
    ViewPager viewPager;
    ClipboardManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        contents = new ArrayList<>();
        drivers = new ArrayList<>();
        lane_names = new ArrayList<>();
        new_drivers = new ArrayList<>();
        arrayList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.fragment);
        tabLayout = (TabLayout) findViewById(R.id.tab_Layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        floatingActionButton = (BottomNavigationView) findViewById(R.id.bottom_navi);
        //floatingActionButton2 = (ExtendedFloatingActionButton) findViewById(R.id.floating);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        setSupportActionBar(toolbar);
        Database db = new Database(this);
        lane_names = db.getData(5);



        for (int i = 0 ; i <lane_names.size(); i++) {
            drivers.add(db.getData(i));
        }
        //remove null
        lane_names.removeAll(Collections.singleton(null));
        drivers.removeAll(Collections.singleton(null));

        preViewpager(viewPager, lane_names);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem();
               //copy
                manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Names", drivers.get(current).toString());
                manager.setPrimaryClip(clipData);

                //  Toast.makeText(Dashboard.this, "Copied", Toast.LENGTH_SHORT).show();
                //

                System.out.println("Item id: " + floatingActionButton.getSelectedItemId()  );
            }

            private Object getSystemService(String clipboard) {
                return clipboard;
            }
        });

        /*floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewPager.getCurrentItem();
                 try {
                     Intent intent  = new Intent(Dashboard.this, sort.class);
                     progressDialog.show();
                     intent.putStringArrayListExtra("drivers", drivers.get(pos));
                     intent.putExtra("item", pos);
                     progressDialog.cancel();
                     startActivity(intent);

                 } catch (Exception e) {
                     Toast.makeText(Dashboard.this, "Error submitting drivers", Toast.LENGTH_SHORT).show();
                     System.out.println(e);
                 }
            }
        });*/



    }

    public class PageViewer extends FragmentPagerAdapter {

        ArrayList<String> arrayList;
        List listF;

        public PageViewer(@NonNull FragmentManager fm) {
            super(fm);
            arrayList = new ArrayList<>();
            listF = new ArrayList();
        }

        public void addFragment(Fragment fragment, String name) {
            listF.add(fragment);
            arrayList.add(name);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return (Fragment)listF.get(position);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        public CharSequence getPageTitle(int p) {
            return (CharSequence)arrayList.get(p);
        }
    }

    private void preViewpager(ViewPager viewPager, ArrayList<String> lane_names) {
        PageViewer pageViewer = new PageViewer(getSupportFragmentManager());

        fragment_list fragment_list = new fragment_list();
        Bundle bundle;
        Intent intent = new Intent(Dashboard.this, fragment_list.getClass());

        for (int i = 0; i < lane_names.size(); i++) {
            bundle = new Bundle();
            bundle.putStringArrayList("drivers", drivers.get(i));
            bundle.putStringArrayList("lanes", lane_names);
            fragment_list.setArguments(bundle);
            pageViewer.addFragment(fragment_list, arrayList.get(i));
            fragment_list = new fragment_list();

        }

        viewPager.setAdapter(pageViewer);
    }
}