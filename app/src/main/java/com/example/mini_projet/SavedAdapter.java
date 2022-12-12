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

public class SavedAdapter extends ArrayAdapter<Job> {

    private List<Job> myList;
    Context context;


    public SavedAdapter(Context context,List<Job> myList) {
        super(context,R.layout.item_list_save,myList);
        this.myList = myList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_list_save, parent,false);
        dataBase  db=new dataBase(context);

        ImageView imageView=rowView.findViewById(R.id.image);
        ImageView delete=rowView.findViewById(R.id.delete);
        TextView name=rowView.findViewById(R.id.produit_nom);
        TextView price=rowView.findViewById(R.id.produit_prix);

        Glide.with(context).load(myList.get(position).getImage().get(0)).into(imageView);

        name.setText(myList.get(position).getName());
        price.setText("$"+myList.get(position).getPrice());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteproduct(myList.get(position).getId());
                savedlist.remove(position);
                notifyDataSetChanged();

            }
        });


        return rowView;
    }
}
