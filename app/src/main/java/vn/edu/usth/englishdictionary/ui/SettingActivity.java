package vn.edu.usth.englishdictionary.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.R;

public class SettingActivity extends AppCompatActivity {
    Button btxoaTest, btxoasTratu;
    TextView tvthongtin;
    DataBase db = new DataBase(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        assert ab != null;
        ab.setTitle("Cài Đặt");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        btxoasTratu = findViewById(R.id.btxoatratu);
        btxoaTest = findViewById(R.id.btxoaTest);
        tvthongtin = findViewById(R.id.tvThongtin);
        btxoasTratu.setOnClickListener(v -> {
            db.ExecuteSQL("DELETE FROM LichSuTraTu");
            Toast.makeText(SettingActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
        });
        btxoaTest.setOnClickListener(v -> {
            db.ExecuteSQL("DELETE FROM LichSuTest");
            Toast.makeText(SettingActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
        });
        String s = "\n"+"\nPhiên Bản: 1.0"+"\n"+"Viết bởi Group 35"+"\n"+"https://github.com/Tennessine01/GroupProject";
        tvthongtin.setText(s);
    }
}
