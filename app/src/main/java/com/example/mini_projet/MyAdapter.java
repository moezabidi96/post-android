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

public class MyAdapter extends ArrayAdapter<Job> {

    private List<Job> myList;
    Context context;

    public MyAdapter(Context context,List<Job> myList) {
        super(context,R.layout.item_list_home,myList);
        this.myList = myList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_list_home, parent,false);
        ImageView imageView=rowView.findViewById(R.id.image);
        TextView name=rowView.findViewById(R.id.name);
        TextView price=rowView.findViewById(R.id.price);

      // imageView.setImageResource(R.drawable.image);
        Glide.with(context).load(myList.get(position).getImage().get(0)).into(imageView);
        name.setText(myList.get(position).getName());
        price.setText("$"+myList.get(position).getPrice());


return rowView;
    }
}
