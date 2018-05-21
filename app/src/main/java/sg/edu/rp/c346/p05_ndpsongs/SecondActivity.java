package sg.edu.rp.c346.p05_ndpsongs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    SongArrayAdapter adapter;
    Button btnFive;
    ArrayList<Song> songs;
    Spinner spinner;
    ArrayList<Integer>year;
    DBHelper dbh = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView) this.findViewById(R.id.lv);
        btnFive = (Button)findViewById(R.id.view5star);


        songs = dbh.getAllSongs();
        adapter = new SongArrayAdapter(this, R.layout.row, songs);
        lv.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        year = dbh.getAllyear();

        ArrayAdapter<Integer> dAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, year);

        spinner.setAdapter(dAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                songs.clear();
                int a = year.get(position);
                songs.addAll(dbh.getAllSongsByYear(a));
                adapter.notifyDataSetChanged();
               Log.v("item",  parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this,
                        Edit.class);
                Song s = songs.get(position);
                i.putExtra("data", s);
                startActivityForResult(i, 5);
            }
        });


        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbh = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(dbh.getAllSongsStar());
                dbh.close();

                adapter.notifyDataSetChanged();

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 5){

            songs.clear();
            DBHelper dbh = new DBHelper(this);
            songs.addAll(dbh.getAllSongs());
            adapter.notifyDataSetChanged();

            Toast.makeText(SecondActivity.this, "Update successful",
                    Toast.LENGTH_SHORT).show();
        }
    }




}
