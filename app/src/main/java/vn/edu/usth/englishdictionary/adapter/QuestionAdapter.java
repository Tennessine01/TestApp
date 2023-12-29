package vn.edu.usth.englishdictionary.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;



import java.util.ArrayList;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.model.Question;

/**
 * Created by binhn on 3/28/2017.
 */

public class QuestionAdapter extends ArrayAdapter<Question> {
    Context context = null;
    public ArrayList<Question> myArray = null;
    ArrayList<Integer> Array = null;
    int a = 0;
    int layoutId;

    public QuestionAdapter(Context context, int layoutId, ArrayList<Question> arr, int a) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = new ArrayList<>();
        this.myArray = arr;
        this.a = a;
    }
    public QuestionAdapter(Context context, int layoutId, ArrayList<Question> arr, int a, ArrayList<Integer> b) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = new ArrayList<Question>();
        this.myArray = arr;
        this.a = a;

        this.Array = b;
    }

    public class Holder {
        TextView ID, tvCauhoi;
        RadioButton rA, rB, rC, rD;
        ListView lv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
//
        final Question test = myArray.get(position);
        convertView = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(layoutId, null);
            //
            holder.ID = convertView.findViewById(R.id.txtID);
            holder.tvCauhoi = convertView.findViewById(R.id.txtCauHoi);
            holder.rA = convertView.findViewById(R.id.Radiobt_A);
            holder.rB = convertView.findViewById(R.id.Radiobt_B);
            holder.rC = convertView.findViewById(R.id.Radiobt_C);
            holder.rD = convertView.findViewById(R.id.Radiobt_D);
            holder.lv = convertView.findViewById(R.id.lvquiz);

            //
            convertView.setTag(holder);
            //
            holder.rA.setOnClickListener(v -> {
                RadioButton cb = (RadioButton) v;
                Question test1 = (Question) cb.getTag();
                setfalse(test1);
                test1.setSelectedA(true);
            });
            holder.rB.setOnClickListener(v -> {
                RadioButton cb = (RadioButton) v;
                Question test1 = (Question) cb.getTag();
                setfalse(test1);
                test1.setSelectedB(true);
            });
            holder.rC.setOnClickListener(v -> {
                RadioButton cb = (RadioButton) v;
                Question test1 = (Question) cb.getTag();
                setfalse(test1);
                test1.setSelectedC(true);
            });
            holder.rD.setOnClickListener(v -> {
                RadioButton cb = (RadioButton) v;
                Question test1 = (Question) cb.getTag();
                setfalse(test1);
                test1.setSelectedD(true);
            });
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.ID.setText(test.getId()+"");
        holder.tvCauhoi.setText("Câu " + (position + 1) + " : "
                + test.getCauhoi());

        holder.rA.setText(test.getA());
        holder.rA.setChecked(test.isSelectedA());
        holder.rA.setTag(test);

        holder.rB.setText(test.getB());
        holder.rB.setChecked(test.isSelectedB());
        holder.rB.setTag(test);
        holder.rC.setText(test.getC());
        holder.rC.setChecked(test.isSelectedC());
        holder.rC.setTag(test);
        holder.rD.setText(test.getD());
        holder.rD.setChecked(test.isSelectedD());
        holder.rD.setTag(test);
        if (a > 0) {
            display(holder);// vô hiêu hóa sự kiện check radioButton
            convertView.setClickable(false);
            convertView.setEnabled(false);

        }
        if (a == 2)
        {
            display(holder);// vô hiêu hóa sự kiện check radioButton
            setTich(holder, Array.get(position));
        }
        return convertView;
    }

    public void setfalse(Question test1) {
        test1.setSelectedA(false);
        test1.setSelectedB(false);
        test1.setSelectedC(false);
        test1.setSelectedD(false);
    }

    public int a() {
        return a + 1;
    }

    public void setTich(Holder holder, int i) {//set dap an da tich o lịch sử
        switch (i) {
            case 1:
                holder.rA.setChecked(true);
                break;
            case 2:
                holder.rB.setChecked(true);
                break;
            case 3:
                holder.rC.setChecked(true);
                break;
            case 4:
                holder.rD.setChecked(true);
                break;

        }
    }
    public void display(Holder holder)// set sự kiện không thể thay đổi trên listView
    {
        holder.rA.setClickable(false);
        holder.rB.setClickable(false);
        holder.rC.setClickable(false);
        holder.rD.setClickable(false);

    }
}
