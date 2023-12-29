package vn.edu.usth.englishdictionary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
;import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.model.Note;

/**
 * Created by ST on 27/03/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int resource;
    private ArrayList<Note> arr;
    private ArrayAdapter<Note>adp;
    /**/
    private SparseBooleanArray selectedListItemsIds;

/*Khai báo contrustor*/
    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Note> arr) {
        super(context, resource, arr);
        this.context=context;
        this.resource=resource;
        this.selectedListItemsIds=new SparseBooleanArray();
        this.arr=arr;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.item_gvghichu,parent,false);
        ImageView img= convertView.findViewById(R.id.img);
        TextView txtTile= convertView.findViewById(R.id.txt_Tile);
        Note note=arr.get(position);
        txtTile.setText(note.getTile());
        GridView gv= convertView.findViewById(R.id.Gv_GhiChu);

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtTile=(TextView)v.findViewById(R.id.txt_Tile);

                Intent in =new Intent(v.getContext(),NoteEditActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("Ten",txtTile.getText().toString());
                in.putExtras(bundle);
                context.startActivity(in);
            }
        });*/
        return convertView;
    }


    public void remove(Note note) {
        arr.remove(note);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !selectedListItemsIds.get(position));
    }

    public void removeSelection() {
        this.selectedListItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            selectedListItemsIds.put(position, value);
        else
            selectedListItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return arr.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedListItemsIds;
    }
}
