package com.taxi.scouf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_list extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayAdapter adapter;
    ArrayList<String> arrayList;
    int num = 0;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_list.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_list newInstance(String param1, String param2) {
        fragment_list fragment = new fragment_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //num for removing more than 1, 1

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.fragment);

        if (getArguments() != null) {
            arrayList = getArguments().getStringArrayList("drivers");
            ArrayList another = arrayList;
            System.out.println("Arguments: "+ arrayList);
            //put in numbers
            if (num < 1) {
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.set(i, (i + 1) + ". " + arrayList.get(i));
                }
            }
            num++;
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
            //listView.setAdapter(
            listView.setAdapter(arrayAdapter);
            arrayList = another;

        } else {

        }

        if (!arrayList.isEmpty()) {
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    return false;
                }
            });
        }
        //View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }
}