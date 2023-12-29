package vn.edu.usth.englishdictionary.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.model.History;


public class HistoryApdater extends ArrayAdapter {
    Context context = null;
    ArrayList<History> myArray = null;
    private SparseBooleanArray selectedListItemsIds;
    int a = 0;

    int layoutId;

    public HistoryApdater(Context context, int layoutId, ArrayList<History> arr) {
        super(context,layoutId, arr );
        this.context = context;
        this.layoutId = layoutId;
        this.selectedListItemsIds = new SparseBooleanArray();
        this.myArray = new ArrayList<>();
        this.myArray = arr;
    }
    public class Holder
    {
        TextView tvTme, tvDiem, tvNgay;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        final History lichsu = myArray.get(position);
        convertView = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder= new Holder();
            convertView = mInflater.inflate(layoutId, null);
            holder.tvTme = convertView.findViewById(R.id.tvTimethi);
            holder.tvNgay = convertView.findViewById(R.id.tvTime);
            holder.tvDiem = convertView.findViewById(R.id.tvdiem);
        }
        holder.tvTme.setText(lichsu.getTimeThi());
        holder.tvNgay.setText(lichsu.getNgay());
        holder.tvDiem.setText(lichsu.getDiem() + "/10");
        return convertView;
    }
    public void remove(History history) {
        myArray.remove(history);
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
        return myArray.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedListItemsIds;
    }
}
