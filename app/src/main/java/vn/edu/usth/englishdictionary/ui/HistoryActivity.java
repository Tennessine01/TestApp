package vn.edu.usth.englishdictionary.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.model.Question;
import vn.edu.usth.englishdictionary.adapter.QuestionAdapter;
import vn.edu.usth.englishdictionary.R;

public class HistoryActivity extends AppCompatActivity {
    String id, idcheck;
    QuestionAdapter adap;
    ListView lv;
    ArrayList<Question> arrTest = null;
    ArrayList<Integer> arr = null;
    DataBase db = new DataBase(this);
    Chronometer time;
    RadioButton rA, rB, rC, rD;
    QuestionActivity mainQuestion = new QuestionActivity();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lvquiz);
        lv = findViewById(R.id.lvquiz);
        arrTest = new ArrayList<>();

        time = findViewById(R.id.chronometer2);

        Bundle b = getIntent().getExtras();
        id = b.getString("ID");
        String t = b.getString("Time");
        idcheck = b.getString("IDCheck");
        time.setText(t);
        //
        arr = new ArrayList<>();
        adap = new QuestionAdapter(HistoryActivity.this, R.layout.item_layout_quiz, arrTest, 2, arr);
        duyet(id);
        IdCheck(idcheck);
        lv.setAdapter(adap);
        setMau();
    }

    public void load(int ID) {
        Question test = new Question();
        Cursor cu = db.getCursor("select * from Question where ID = '" + ID + "'");
        if (cu.moveToFirst()) {
            do {
                test.setId(cu.getInt(0));
                test.setCauhoi(cu.getString(1));
                test.setA(cu.getString(2));
                test.setB(cu.getString(3));
                test.setC(cu.getString(4));
                test.setD(cu.getString(5));
                test.setDung(cu.getString(6));
                arrTest.add(test);
            } while (cu.moveToNext());
        }

    }
    public void duyet(String id) {
        String ID [] = id.split(",");
        for (int i = 0; i < ID.length; i++) {
            load(Integer.parseInt(ID[i]));// dưa dữ liệu nên listView theo id
        }
        lv.setAdapter(adap);
    }
    public void IdCheck(String id) {
        String ID [] = id.split(",");
        for (int i = 0; i < ID.length; i++) {
            arr.add(Integer.valueOf(ID[i]));// đưa lịch suwaar check vào
        }
        lv.setAdapter(adap);
    }
    public void setMau() {
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = 0; i < visibleItemCount; i++) {
                    View v = lv.getChildAt(i);
                    TextView IDCauHoi = v.findViewById(R.id.txtID);
                    rA = v.findViewById(R.id.Radiobt_A);
                    rB = v.findViewById(R.id.Radiobt_B);
                    rC = v.findViewById(R.id.Radiobt_C);
                    rD = v.findViewById(R.id.Radiobt_D);
                    String DapAn = "";
                    DapAn = getDung(Integer.parseInt(IDCauHoi.getText().toString()));//lấy ra đáp án đúng từ id
                    RadioGroup rg = v.findViewById(R.id.radioGroup);
                    int Check = rg.getCheckedRadioButtonId();
                    switch (Check) {
                        case R.id.Radiobt_A:
                            rA.setTextColor(getResources().getColor(R.color.colorSelect));
                            break;
                        case R.id.Radiobt_B:
                            rB.setTextColor(getResources().getColor(R.color.colorSelect));
                            break;
                        case R.id.Radiobt_C:
                            rC.setTextColor(getResources().getColor(R.color.colorSelect));
                            break;
                        case R.id.Radiobt_D:
                            rD.setTextColor(getResources().getColor(R.color.colorSelect));
                            break;
                    }
                    if (mainQuestion.kiemtra(rA.getText().toString().substring(0, 1), DapAn)) {
                        setDung(1);
                    }

                    if (mainQuestion.kiemtra(rB.getText().toString().substring(0, 1), DapAn)) {
                        setDung(2);
                    }
                    if (mainQuestion.kiemtra(rC.getText().toString().substring(0, 1), DapAn)) {
                        setDung(3);
                    }
                    if (mainQuestion.kiemtra(rD.getText().toString().substring(0, 1), DapAn)) {
                        setDung(4);
                    }

                }
            }
        });
    }
    public void setDung(int i) {
        switch (i) {
            case 1:
                rA.setTextColor(getResources().getColor(R.color.colordung));
                break;
            case 2:
                rB.setTextColor(getResources().getColor(R.color.colordung));
                break;
            case 3:
                rC.setTextColor(getResources().getColor(R.color.colordung));
                break;
            case 4:
                rD.setTextColor(getResources().getColor(R.color.colordung));
                break;
        }
    }
    public String getDung(int id) {
        String dung = "";
        ArrayList<Question> countryList = adap.myArray;// gợi myArray
        for (int i = 0; i < countryList.size(); i++) {
            Question question = countryList.get(i);
            if (question.getId() == id)
            {
                dung = question.getDung();
            }
        }
        return dung;
    }
}
