package vn.edu.usth.englishdictionary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.usth.englishdictionary.R;


public class ListView extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList itemname = new ArrayList<String>();
    private final Integer[] imgid;

    public ListView(Activity context, ArrayList itemname, Integer[] imgid) {
        super(context, R.layout.menu, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.menu, null, true);

        TextView txtTitle = rowView.findViewById(R.id.item);
        ImageView imageView = rowView.findViewById(R.id.icon);

        txtTitle.setText((CharSequence) itemname.get(position));
        if (imgid.length > 1) {
            imageView.setImageResource(imgid[position]);
            return rowView;
        } else {
            imageView.setImageResource(imgid[0]);
            return rowView;
        }
    }
}

