package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    TextView totalprice;
    double total=0;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CartAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        ListView commandeListView=findViewById(R.id.listCommande);
        totalprice=findViewById(R.id.totalPrice);
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
         myAdapter=new CartAdapter(getApplicationContext(),Global_listCart.listCart);
        myAdapter.notifyDataSetChanged();
        commandeListView.setAdapter(myAdapter);

        for (int i=0;i<Global_listCart.listCart.size();i++){
            total=total+Double.valueOf(Global_listCart.listCart.get(i).getQuantity())*Double.valueOf(Global_listCart.listCart.get(i).getPrice());
        }
        totalprice.setText("$"+total);
    }

    public void buyProduct(View view) {
     if (Global_listCart.listCart.size()>0){


        for (int i=0;i<Global_listCart.listCart.size();i++){
            Map<String, Object> commande = new HashMap<>();
            commande.put("price", Global_listCart.listCart.get(i).getPrice());
            commande.put("quantity", Global_listCart.listCart.get(i).getQuantity());
            commande.put("size", Global_listCart.listCart.get(i).getSize());
            db.collection("Commandes")
                    .document(mAuth.getCurrentUser().getUid())
                    .collection("Cart")
                    .document(Global_listCart.listCart.get(i).getId())
                    .set(commande)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
        total=0;
        totalprice.setText("$"+total);
        Global_listCart.listCart.clear();
        myAdapter.notifyDataSetChanged();
        Intent intent=new Intent(getApplicationContext(),Page_Principal.class);
        startActivity(intent);
     }
    }
}