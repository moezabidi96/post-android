package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {
    TextView name,price,desc;

    String selected_size="";
    TextView quantity;

    ImageButton saved;

    FirebaseFirestore db;

    dataBase SQLdb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Gson gson = new Gson();
        Job job=gson.fromJson(getIntent().getStringExtra("product"), Job.class);
        Log.d("id", ""+job.getId());
        ImageSlider imageSlider=findViewById(R.id.slider);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);



        saved=findViewById(R.id.save);


        SQLdb=new dataBase(getApplicationContext());

        db= FirebaseFirestore.getInstance();

        List<SlideModel> slideModelList = new ArrayList<>();
        for (int i=0 ; i< job.getImage().size();i++){
            slideModelList.add(new SlideModel(job.getImage().get(i),null));
        }
        imageSlider.setImageList(slideModelList);
        name.setText(job.getName());
        price.setText("$"+job.getPrice());
        desc.setText(job.getDesc());





      /*  add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                    Global_listCart.listCart.add(new Commande(product.getName(),product.getId(),selected_size,Integer.toString(quantity_Product),product.getPrice(),product.getImage().get(0)));
                    Intent intent =new Intent(getApplicationContext(),Page_Principal.class);
                    startActivity(intent);



            }
        });
*/
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLdb.insertData(job.getId());
                Toast.makeText(ProductDetail.this, "Pressed ", Toast.LENGTH_SHORT).show();


            }
        });
    }
}