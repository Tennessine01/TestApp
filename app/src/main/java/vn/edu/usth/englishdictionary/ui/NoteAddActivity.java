package vn.edu.usth.englishdictionary.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.utils.DataBase;

public class NoteAddActivity extends AppCompatActivity {
    DataBase db = new DataBase(this);
    ArrayList<String> arr;
    EditText edtTenGhiChu, edtNoiDungGhiChu;
    String tenGhiChu, NoiDungGhiChu;
    String tile=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle("Thêm Ghi Chú");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtTenGhiChu = findViewById(R.id.edt_Tile);
        edtNoiDungGhiChu = findViewById(R.id.edt_Note);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_save) {
            Insert();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Insert() {
        tenGhiChu = edtTenGhiChu.getText().toString();
        NoiDungGhiChu = edtNoiDungGhiChu.getText().toString();
        int sbg = db.GetCount("Select * from GhiChu where TenGhiChu=\"" + tenGhiChu + "\"");
        if (sbg == 1) {
            AlertDialog.Builder al = new AlertDialog.Builder(this);
            al.setTitle("Thông báo");
            al.setMessage("Tên ghi chú đã tồn tại. ");
            al.create().show();
        } else if (sbg == 0) {
            SQLiteDatabase sQLiteDatabase = db. getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("TenGhiChu",  tenGhiChu.trim()+"");
            contentValues.put("NDGhiChu",NoiDungGhiChu + "");
            sQLiteDatabase.insert("GhiChu", null, contentValues);
            sQLiteDatabase.close();
            Intent in = new Intent(NoteAddActivity.this, NoteActivity.class);
            startActivity(in);
        }
    }
    //get ra id cua phân tử cuỗi cùng trong ghi chú
    public int getId()
    {
        int id = 0;
        Cursor cu = db.getCursor("select id from GhiChu");
        if (cu.moveToLast())
        {
            id = cu.getInt(0);
        }
        return id;
    }
}

