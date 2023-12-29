package vn.edu.usth.englishdictionary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.R;

public class SearchDictionaryAtivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    ArrayList<String> ar = null;
    ArrayAdapter adap = null;
    EditText edt;
    ListView lv;
    String[] item = null;
    Integer[] icon = {R.drawable.ic_history_black_24dp, R.drawable.ic_search_black_24dp};
    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dictionary);
        edt =  findViewById(R.id.edtSearch);
        @SuppressLint("WrongViewCast") final ImageView a = (ImageView) findViewById(R.id.itemSearch);
        lv = findViewById(R.id.lv);
        //
        ar = new ArrayList<>();
        //
        edt.setOnEditorActionListener(this);// sét sự kiện phím ok
        getHistory();
        lv.setOnItemClickListener((parent, view, position, id) -> {
            if(edt.getText()+"" !=  "") {//lây dũ liệu tìm kiếm được
                String a1 = ((TextView) view.findViewById(R.id.itemSearch)).getText().toString();// lay ra từ
                SearchTu(a1);
            }
            else
            {//lấy dữ liệu trong lịch sử
                String a1 = ((TextView) view.findViewById(R.id.itemSearch1)).getText().toString();// lay ra từ
                SearchTu(a1);
            }
        });
        //hiện từ gợi ý
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = edt.getText().toString();
                if (edt.getText() + "" == "") {
                    getHistory();
                } else {
                    copy2ListView(search);
                    SearchDictionaryAtivity.this.adap.getFilter().filter(s);//lọc thông minh
                }
            }
        });

    }

    public void copy2ListView(String s) {
        ar.clear();
        if (s != "") {
            Cursor cu = db.getCursor("select * From anhviet where tu like \"" + s + "%\" limit 0, 50");
            if (cu.moveToFirst()) {
                do {
                    ar.add(cu.getString(0));
                } while (cu.moveToNext());
                adap = new ArrayAdapter<String>(this, R.layout.item_search, R.id.itemSearch, ar);
                lv.setAdapter(adap);
            }
        }

    }

    //tìm kiếm từ
    public void SearchTu(String s) {
        Cursor cu = db.getCursor("select * From anhviet where tu like \"" + s + "\"");
        if (cu.moveToFirst()) {
            Intent iDetail = new Intent(this, SearchActivity.class);
            Bundle b = new Bundle();
            b.putString("key_Word", cu.getString(0));
            b.putString("key_Mean", cu.getString(1));
            iDetail.putExtras(b);
            finish();
            startActivity(iDetail);
        }
    }

    // sự kiện ấn k trên phím ảo
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_GO) {
            SearchTu(edt.getText().toString());
            handled = true;
        }
        return handled;
    }

    //lấy lịch sử lên listView
    public void getHistory() {
        ar.clear();
        Cursor c = db.getCursor("select * from LichSuTraTu");
        if (c.moveToLast()) {
            do {
                ar.add(c.getString(1));
            } while (c.moveToPrevious());
            adap = new ArrayAdapter<String>(this, R.layout.history, R.id.itemSearch1, ar);
            lv.setAdapter(adap);
        }
    }
    public void DeleleHistory(String s)
    {
        db.ExecuteSQL("delete from LichSuTraTu where work = \""+s+"\" ");
    }
}
