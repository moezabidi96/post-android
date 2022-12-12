package com.example.mini_projet.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mini_projet.Job;
import com.example.mini_projet.ProductDetail;
import com.example.mini_projet.R;
import com.example.mini_projet.SavedAdapter;
import com.example.mini_projet.dataBase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class save extends Fragment {
    ListView list ;

    dataBase db ;
    FirebaseFirestore dbFire;
    ArrayList<String> listItem;
    SavedAdapter savedAdapter ;
  public static  ArrayList<Job> savedlist ;


    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_save, container, false);
        list=view.findViewById(R.id.list);
        dbFire= FirebaseFirestore.getInstance();

        savedlist=new ArrayList<Job>();
        listItem=new ArrayList<String>();
        db=new dataBase(getActivity().getApplicationContext());
        getDataFromSQLIte();
        fetchData();




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), ProductDetail.class);
                Gson gson = new Gson();
                String myProduct = gson.toJson(savedlist.get(position));
                i.putExtra("product", myProduct);
                startActivity(i);



            }
        });

        return view;
    }
    private void getDataFromSQLIte() {
        Cursor cursor =db.viewData();

        if(cursor.getCount()==0){

        }else{
            while (cursor.moveToNext()){

                listItem.add(cursor.getString(0));




            }

        }
    }
    void fetchData(){

        dbFire.collection("Jobs")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        savedlist.clear();
                        if (value!=null){
                            for (DocumentSnapshot document : value){
                                for (int i=0 ; i<listItem.size();i++){
                                    if (listItem.get(i).equals(document.getId())){
                                        savedlist.add(
                                                new Job(
                                                        document.getId(),
                                                        document.get("name").toString(),
                                                        document.get("price").toString(),
                                                        document.get("description").toString(),

                                                        (ArrayList<String>) document.get("image")
                                                )

                                        );
                                    }
                                }




                                // Log.d("Lien Image", ""+((ArrayList<String>) document.get("image")).get(0));
                            }
                            savedAdapter=new SavedAdapter(getActivity().getApplicationContext(),savedlist) ;
                            savedAdapter.notifyDataSetChanged();
                            list.setAdapter(savedAdapter);

                        }

                    }
                });

    }
}