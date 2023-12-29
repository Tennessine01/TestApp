package vn.edu.usth.englishdictionary.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.R;

public class GrammarActivity extends AppCompatActivity {
    TextView view_ten,view_CauKD,view_CauPD,view_CauNV,view_CachDung,view_ChuY;
    String ten;
    DataBase db=new DataBase(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_nguphap);
        view_ten= findViewById(R.id.view_Ten);
        view_CauKD= findViewById(R.id.view_CauKD);
        view_CauPD= findViewById(R.id.view_CauPD);
        view_CauNV= findViewById(R.id.view_CauNV);
        view_CachDung= findViewById(R.id.view_CachDung);
        view_ChuY= findViewById(R.id.view_ChuY);
        Intent in=getIntent();
        Bundle bundle=getIntent().getExtras();
        ten=bundle.getString("Ten");
        String ten=bundle.getString("Ten");
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Ngữ Pháp");

        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(ten!=null){
            Cursor cursor=db.getCursor("select * from Gramar where Ten='"+ten+"'");
            cursor.moveToFirst();
            view_ten.setText(cursor.getString(1));
            ab.setTitle("Ngữ Pháp: "+cursor.getString(1));
            view_CauKD.setText(cursor.getString(2));
            view_CauPD.setText(cursor.getString(3));
            view_CauNV.setText(cursor.getString(4));
            view_CachDung.setText(cursor.getString(5));
            view_ChuY.setText(cursor.getString(6));
        }
    }


}
