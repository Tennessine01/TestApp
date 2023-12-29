package vn.edu.usth.englishdictionary.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.utils.DataBase;

public class NoteEditActivity extends AppCompatActivity {
    DataBase db = new DataBase(this);
    ArrayList<String> arr;
    EditText edtTenGhiChu, edtNoiDungGhiChu;
    String tenGhiChu, NoiDungGhiChu;
    int id=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        edtTenGhiChu = findViewById(R.id.edt_Tile);
        edtNoiDungGhiChu = findViewById(R.id.edt_Note);
        Bundle b=getIntent().getExtras();
        id=b.getInt("ID");
        if(id!=0) {
            Cursor cursor = db.getCursor("select * from GhiChu where Id =\"" + id + "\"");
            cursor.moveToFirst();
            edtTenGhiChu.setText(cursor.getString(1));
            edtNoiDungGhiChu.setText(cursor.getString(2));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item_save){
            Update();
            Toast.makeText(this, "cập nhật thành công!", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(NoteEditActivity.this, NoteActivity.class);
            startActivity(in);
            finish();
        }
        if(id==R.id.item_delete){
            Delete();
            Intent in = new Intent(NoteEditActivity.this, NoteActivity.class);
            startActivity(in);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

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

    public void Update(){
        tenGhiChu = edtTenGhiChu.getText().toString();
        NoiDungGhiChu = edtNoiDungGhiChu.getText().toString();
        SQLiteDatabase sQLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NDGhiChu", NoiDungGhiChu + "");
        contentValues.put("TenGhiChu", tenGhiChu + "");
        sQLiteDatabase.update("GhiChu", contentValues, "Id = '" + id + "'", null);
        sQLiteDatabase.close();
    }

    public void Delete(){
        tenGhiChu = edtTenGhiChu.getText().toString();
        db.ExecuteSQL("delete from GhiChu where Id=\""+id+"\"");
    }
}

