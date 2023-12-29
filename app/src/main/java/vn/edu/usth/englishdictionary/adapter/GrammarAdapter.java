package vn.edu.usth.englishdictionary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import vn.edu.usth.englishdictionary.ui.GrammarActivity;
import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.model.Grammar;

public class GrammarAdapter extends ArrayAdapter<Grammar> {
    private Context context;
    private int resource;
    private ArrayList<Grammar>arr;
    private ArrayAdapter<Grammar>adp;
    public GrammarAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Grammar> arr) {
        super(context, resource, arr);
        this.context=context;
        this.resource=resource;
        this.arr=arr;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_nguphap,parent,false);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imgNguPhap);
        final TextView txtTenNguPhap=(TextView)convertView.findViewById(R.id.txt_TenNguPhap);
        Grammar nguPhap=arr.get(position);
        txtTenNguPhap.setText(nguPhap.getTen());
        convertView.setOnClickListener(v -> {
            Intent in=new Intent(v.getContext(), GrammarActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("Ten",txtTenNguPhap.getText().toString());
            in.putExtras(bundle);
            context.startActivity(in);
        });
        return convertView;
    }
}
