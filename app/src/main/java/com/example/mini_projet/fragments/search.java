package com.example.mini_projet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mini_projet.MyAdapter;
import com.example.mini_projet.Job;
import com.example.mini_projet.ProductDetail;
import com.example.mini_projet.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;


public class search extends Fragment {
    ArrayList<Job> mylist ;
    ListView listView;
    FirebaseFirestore db;
    EditText search;
    String searchValue="";
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        listView=(ListView)view.findViewById(R.id.list);
        search=view.findViewById(R.id.search);




        mylist=new ArrayList<Job>();
        db= FirebaseFirestore.getInstance();
        fetchAllData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchValue=s.toString();
                fetchData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), ProductDetail.class);
                Gson gson = new Gson();
                String myProduct = gson.toJson(mylist.get(position));
                i.putExtra("product", myProduct);
                startActivity(i);
            }
        });





        return view;



    }
    void fetchData(){
        db.collection("Jobs")
                .whereEqualTo("search",searchValue.toLowerCase())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mylist.clear();

                        for (DocumentSnapshot document : value){
                            mylist.add(
                                    new Job(
                                            document.getId(),
                                            document.get("name").toString(),
                                            document.get("price").toString(),
                                            document.get("description").toString(),

                                            (ArrayList<String>) document.get("image")
                                    )
                            );

                        }

                        myAdapter=new MyAdapter(getContext(),mylist);
                        myAdapter.notifyDataSetChanged();
                        listView.setAdapter(myAdapter);
                    }
                });
    }
    void fetchAllData(){
        db.collection("Jobs")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mylist.clear();

                        for (DocumentSnapshot document : value){
                            mylist.add(
                                    new Job(
                                            document.getId(),
                                            document.get("name").toString(),
                                            document.get("price").toString(),
                                            document.get("description").toString(),

                                            (ArrayList<String>) document.get("image")
                                    )
                            );

                        }

                        myAdapter=new MyAdapter(getContext(),mylist);
                        myAdapter.notifyDataSetChanged();
                        listView.setAdapter(myAdapter);
                    }
                });
    }
}