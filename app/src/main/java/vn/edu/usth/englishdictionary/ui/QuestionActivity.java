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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.model.Question;
import vn.edu.usth.englishdictionary.adapter.QuestionAdapter;
import vn.edu.usth.englishdictionary.R;

public class QuestionActivity extends AppCompatActivity {
    ListView lv;
    RadioButton rA, rB, rC, rD;
    QuestionAdapter adap;
    ArrayList<Question> arrTest = null;
    ArrayList<Integer> ID;
    String IDtich;
    DataBase db = new DataBase(this);
    Random rd = new Random();
    Chronometer time;
    FloatingActionButton fab;
    int d = 0, a = 0;//khởi tạo biến đếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        //
        fab = findViewById(R.id.FabTest);
        lv = findViewById(R.id.lvquiz);
        time = findViewById(R.id.chronometer2);
        //
        time.start();//bắt đầu chạy time
        //
        ID = new ArrayList<>();
        arrTest = new ArrayList<>();
        adap = new QuestionAdapter(QuestionActivity.this, R.layout.item_layout_quiz, arrTest, a);
        lv.setAdapter(adap);

        duyet();
        //sư kiện click tính điểm
        DoneTest();
    }

    public void DoneTest() {
        fab.setOnClickListener(v -> {
            if (a == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionActivity.this);
                dialog.setMessage("Bạn muốn kết thúc bài thi!");
                dialog.setPositiveButton("OK", (dialog1, which) -> {
                    a++;
                    setMau();//cập nhật lại đáp án và hiện trên listView
                    TinhDiem();//gọi đến phương thức tính điểm\
                    adap = new QuestionAdapter(QuestionActivity.this, R.layout.item_layout_quiz, arrTest, a);
                    lv.setAdapter(adap);
                    Hien(d);
                });
                dialog.setNegativeButton("Canel", (dialog12, which) -> dialog12.cancel());
                dialog.create().show();
            } else {
                Hien(d);
            }
        });
    }

    //Tính điểm
    public void TinhDiem() {
        IDtich = "";//láy ra vi tri da tích
        ArrayList<Question> ArrayQuestion = adap.myArray;// gợi myArray
        for (int i = 0; i < ArrayQuestion.size(); i++) {
            Question questions = ArrayQuestion.get(i);
            if (questions.isSelectedA()) {
                if (kiemtra(questions.getA().substring(0, 1), questions.getDung()))
                    d++;
                IDtich = IDtich + 1 + ",";
            }
            if (questions.isSelectedB()) {
                if (kiemtra(questions.getB().substring(0, 1), questions.getDung()))
                    d++;
                IDtich = IDtich + 2 + ",";
            }
            if (questions.isSelectedC()) {
                if (kiemtra(questions.getC().substring(0, 1), questions.getDung()))
                    d++;
                IDtich = IDtich + 3 + ",";
            }
            if (questions.isSelectedD()) {
                if (kiemtra(questions.getD().substring(0, 1), questions.getDung()))
                    d++;
                IDtich = IDtich + 4 + ",";
            }
            if (!questions.isSelectedA() && !questions.isSelectedB() && !questions.isSelectedC() && !questions.isSelectedD()) {
                IDtich = IDtich + 0 + ",";
            }
        }
    }

    //hiển thị điểm đã làm được
    public void Hien(int d) {
        time.stop();// dừng tính time
        String idCauhoi = "";
        for (int i = 0; i < ID.size(); i++) {
            idCauhoi = idCauhoi + ID.get(i) + ",";
        }
        Cursor cu = db.getCursor("Select * from LichSuTest where IDCAUHOI = '" + idCauhoi + "'");
        if (cu.getCount() == 0) {
            WriteHistory(getIDHistory(),idCauhoi, IDtich, d);
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionActivity.this);
        dialog.setTitle("Kết quả Test");
        dialog.setMessage("Điểm đạt : " + d + "/10" + "\n" + "Time: " + time.getText());
        dialog.setPositiveButton("OK", (dialog1, which) -> dialog1.cancel());
        dialog.setNegativeButton("EXIT", (dialog12, which) -> finish());
        dialog.create().show();
    }

    //kiểm tra đấp án đúng  thì trả ra true ngược lại
    public boolean kiemtra(String a, String b) {
        if (a.contains(b.replaceAll("\\s+", ""))) {
            return true;
        } else {
            return false;
        }
    }

    public void duyet() {
        RanRom();
        for (int i = 0; i < ID.size(); i++) {
            load(ID.get(i));// dưa dữ liệu nên listView theo id
        }
        adap.notifyDataSetChanged();
    }

    //đọc đọc hỏi ở CSDL đưa vào ArrayLisst theo ID
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

    //RamRom 10 câu hỏi
    public ArrayList RanRom() {
        int iNew = 0;
        int dem = db.GetCount("SELECT * FROM Question");
        for (int i = 0; i < 10; ) {
            iNew = rd.nextInt(dem);
            if (!ID.contains(iNew) || iNew == 0) {
                i++;
                ID.add(iNew);
            }
        }
        return ID;
    }

    //ghi lịch sử vào cơ sở dữ liệu
    public void WriteHistory(int id, String idCauhoi, String idtich, int diem) {
        db.ExecuteSQL("INSERT INTO LichSuTest VALUES('" + (id+1) + "','" + getDateTime() + "'," +
                "'" + idCauhoi + "', " +
                "'" + idtich + "', " +
                "" + diem + ",'" +
                "" + time.getText() + "')");
    }
    //lấy ra id của phần tử cuối trong bảng lichsu
    public int getIDHistory()
    {
        int id = 0;
        Cursor cu = db.getCursor("select id from LichSuTest");
        if (cu.moveToLast())
        {
            id = cu.getInt(0);
        }
        return id;
    }

    //lấy ra thời gian và ngày tháng
    public String getDateTime() {
        String time;
        Date date = new Date();
        String strDateFormat = "dd/MM/yyyy";
        String strDateFormat24 = "HH:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);//láy ra ngày
        SimpleDateFormat sdft = new SimpleDateFormat(strDateFormat24);//lấy ra giờ
        time = sdf.format(date) + "   " + sdft.format(date);
        return time;
    }

    //để sét mầy cho đáp án đúng vè ddaps án sai
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
                        //sét mầu đáp án đã tích
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
                    if (kiemtra(rA.getText().toString().substring(0, 1), DapAn)) {
                        setDung(1);
                    }

                    if (kiemtra(rB.getText().toString().substring(0, 1), DapAn)) {
                        setDung(2);
                    }
                    if (kiemtra(rC.getText().toString().substring(0, 1), DapAn)) {
                        setDung(3);
                    }
                    if (kiemtra(rD.getText().toString().substring(0, 1), DapAn)) {
                        setDung(4);
                    }

                }
            }
        });
    }

    //set mầy đáp án đúng
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

    //trả ra đáp án đúng theo ID
    public String getDung(int id) {
        String dung = "";
        ArrayList<Question> ArrayQuestion = adap.myArray;// gợi myArray
        for (int i = 0; i < ArrayQuestion.size(); i++) {
            Question question = ArrayQuestion.get(i);
            if (question.getId() == id) {
                dung = question.getDung();
            }
        }
        return dung;
    }
}
