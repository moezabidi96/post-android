package com.example.mini_projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.example.mini_projet.fragments.save.savedlist;

public class CartAdapter extends ArrayAdapter<Commande> {

    private List<Commande> myList;
    Context context;


    public CartAdapter(Context context,List<Commande> myList) {
        super(context,R.layout.item_list_cart,myList);
        this.myList = myList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_list_cart, parent,false);

        ImageView imageView=rowView.findViewById(R.id.image);
        ImageView delete=rowView.findViewById(R.id.delete);
        TextView name=rowView.findViewById(R.id.produit_nom);
        TextView price=rowView.findViewById(R.id.produit_prix);
        TextView size=rowView.findViewById(R.id.produit_size);
        TextView quantity=rowView.findViewById(R.id.produit_quantity);

        Glide.with(context).load(myList.get(position).getImage()).into(imageView);

        name.setText(myList.get(position).getName());
        price.setText("$"+myList.get(position).getPrice());
        size.setText("Size : "+myList.get(position).getSize());
        quantity.setText("Quantity : "+myList.get(position).getQuantity());

         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Global_listCart.listCart.remove(position);

                
                 notifyDataSetChanged();
             }
         });


        return rowView;
    }
}
