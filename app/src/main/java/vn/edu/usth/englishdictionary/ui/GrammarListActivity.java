package vn.edu.usth.englishdictionary.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.adapter.GrammarAdapter;
import vn.edu.usth.englishdictionary.model.Grammar;
import vn.edu.usth.englishdictionary.R;

public class GrammarListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    DataBase db=new DataBase(this);
    ArrayList<Grammar>arr;
    GrammarAdapter adp;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_nguphap);
        //hiện tiêu đề;
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("Ngữ Pháp");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv= findViewById(R.id.lv_ngu_phap);
        arr= new ArrayList<>();
        adp=new GrammarAdapter(this,R.layout.item_nguphap,arr);
        lv.setAdapter(adp);
        load();
        adp.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timkiem,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    public void load() {
        Cursor cur = db.getCursor("Select * from Gramar");
        int count =cur.getCount();
        if (cur.moveToFirst()) {
            do {
                arr.add(new Grammar(count++, cur.getString(1), cur.getString(2),
                        cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));
            } while (cur.moveToNext());
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Cursor cur=db.getCursor("Select * from Gramar where Ten like '%"+newText+"%'");
        int count =cur.getCount();
        /*Làm mới lại adp*/
        adp.clear();

        if(cur.moveToFirst()){

            do{
                arr.add(new Grammar(count++, cur.getString(1), cur.getString(2),
                        cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));
            }while (cur.moveToNext());

        }
        lv.setAdapter(adp);
        adp.notifyDataSetChanged();
        return true;

    }
}
