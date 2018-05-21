package sg.edu.rp.c346.p05_ndpsongs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Edit extends AppCompatActivity {

    EditText editId,editTitle,editSinger,editYear;
    RadioGroup rg;
    Button btnUpdate,btnDelete,btnCancel;
    Song data;
    RadioButton rb1,rb2,rb3,rb4,rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editId = (EditText)findViewById(R.id.editID);
        editTitle = (EditText)findViewById(R.id.editTitle);
        editSinger = (EditText)findViewById(R.id.editSingers);
        editYear = (EditText)findViewById(R.id.editYear);

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        editId.setText(data.getId()+"");
        editTitle.setText(data.getTitle());
        editSinger.setText(data.getSingers());
        editYear.setText(data.getYear()+"");

        rg = (RadioGroup)findViewById(R.id.editRadioGroupStars);

        if(data.getStars() ==5){
            rb5 = (RadioButton)findViewById(R.id.editRadio5);
            rb5.setChecked(true);
        }else if(data.getStars() ==4){
            rb4 = (RadioButton)findViewById(R.id.editRadio4);
            rb4.setChecked(true);
        }
        else if(data.getStars() ==3){
            rb3 = (RadioButton)findViewById(R.id.editRadio3);
            rb3.setChecked(true);
        }

        else if(data.getStars() ==2){
            rb2 = (RadioButton)findViewById(R.id.editRadio2);
            rb2.setChecked(true);
        }
        else if(data.getStars() ==1){
            rb1 = (RadioButton)findViewById(R.id.editRadio1);
            rb1.setChecked(true);
        }



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(Edit.this);
                data.setTitle(editTitle.getText().toString().trim());
                data.setSingers(editSinger.getText().toString().trim());
                data.setYear(Integer.parseInt(editYear.getText().toString()));
                int stars = getStars();
                data.setStars(stars);
                dbh.updateSong(data);
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK,i);
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(Edit.this);
                dbh.deleteSong(data.getId());
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });










    }

    private int getStars() {
        int stars = 1;
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.editRadio1:
                stars = 1;
                break;
            case R.id.editRadio2:
                stars = 2;
                break;
            case R.id.editRadio3:
                stars = 3;
                break;
            case R.id.editRadio4:
                stars = 4;
                break;
            case R.id.editRadio5:
                stars = 5;
                break;
        }
        return stars;
    }

}

