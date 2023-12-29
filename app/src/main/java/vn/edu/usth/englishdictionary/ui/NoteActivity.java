package vn.edu.usth.englishdictionary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.adapter.NoteAdapter;
import vn.edu.usth.englishdictionary.model.Note;

public class NoteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<Note> arr = new ArrayList<Note>();
    NoteAdapter adp=null;
    DataBase db = new DataBase(this);
    FloatingActionButton fabAdd;
    GridView gvHienThi;
    EditText edt_Tile, edt_Note;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_note);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Ghi Chú");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabAdd = findViewById(R.id.Fab);
        edt_Tile = findViewById(R.id.edt_Tile);
        edt_Note = findViewById(R.id.edt_Note);
        gvHienThi = findViewById(R.id.Gv_GhiChu);
        arr = new ArrayList<>();
        adp = new NoteAdapter(this, R.layout.item_gvghichu, arr);
        gvHienThi.setOnItemClickListener((parent, view, position, id) -> {
            Intent in = new Intent(NoteActivity.this, NoteEditActivity.class);
            Bundle b = new Bundle();
            Note note = adp.getItem(position);
            b.putInt("ID", note.getId());
            in.putExtras(b);
            startActivity(in);
        });
        load();
        getView();
        gvHienThi.setAdapter(adp);
        Add();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timkiem,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;

    }

    public void getView() {
        gvHienThi.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);//chọn nhiều itome trong ListView
        gvHienThi.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = gvHienThi.getCheckedItemCount();
                //hiện só lượng đã chọn
                mode.setTitle(checkedCount + " /" + adp.getSelectedCount());
                adp.toggleSelection(position);//xóa item hiện trên listView
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_opption, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.delete) {
                    SparseBooleanArray selected = adp.getSelectedIds();//lấy ra các vị trí đã check
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            Note note = adp.getItem(selected.keyAt(i));
                            adp.remove(note);//xóa trong mảng ArryList
                            delete(note.getId());//xóa trong CSDL
                        }
                    }
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
            }
        });

    }

    public void load() {
        Cursor cur = db.getCursor("select * from GhiChu");
        if (cur.moveToFirst()) {
            do {
                arr.add(new Note(cur.getInt(0), cur.getString(1), cur.getString(2)));
            } while (cur.moveToNext());
        }

    }

    public void Add() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NoteAddActivity.class);
            startActivity(intent);
        });
    }
    public void delete(int id)
    {
        db.ExecuteSQL("delete from GhiChu where id = \""+id+"\"");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Cursor cursor=db.getCursor("Select * from GhiChu where TenGhiChu like \"%"+newText+"%\"");
        /*Làm mới lại adp*/
        adp.clear();
        if(cursor.moveToFirst()){
            do{
                arr.add(new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        gvHienThi.setAdapter(adp);
        adp.notifyDataSetChanged();
        return true;

    }
}

