package com.example.mini_projet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mini_projet.AddJob;
import com.example.mini_projet.CartActivity;
import com.example.mini_projet.Global_listCart;
import com.example.mini_projet.MyAdapter;
import com.example.mini_projet.Job;
import com.example.mini_projet.ProductDetail;
import com.example.mini_projet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class home extends Fragment {
   ArrayList<Job> mylist ;
   ListView listView;
    FirebaseFirestore db;
    RelativeLayout cart;

    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        listView=(ListView)view.findViewById(R.id.list);
        cart=view.findViewById(R.id.cart);

        mAuth = FirebaseAuth.getInstance();




        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AddJob.class);
                startActivity(intent);
            }
        });
        mylist=new ArrayList<Job>();
        db= FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser()!=null){

            fetchData();
        }


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
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mylist.clear();
                        if (value!=null){
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
                                // Log.d("Lien Image", ""+((ArrayList<String>) document.get("image")).get(0));
                            }

                            MyAdapter myAdapter=new MyAdapter(getContext(),mylist);
                            myAdapter.notifyDataSetChanged();
                            listView.setAdapter(myAdapter);
                        }

                    }
                });

    }
}