package mh.shiftcalendaram;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import mh.shiftcalendaram.Adapters.ListViewAdapter;
import mh.shiftcalendaram.Database.Database;
import mh.shiftcalendaram.Templates.ListTemplate;
import mh.shiftcalendaram.Templates.ShiftTemplate;

public class ShiftListActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    ArrayList<ListTemplate> shifts;
    Database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(ShiftListActivity.this);

     /*   for (int i = 0; i < 5;i++) {
            database.insertShifts("ewfw", "A",  "#FF0000");
        }*/
        database.getShifts();

      /*  listView = (ListView)findViewById(R.id.listView_sifts);
        adapter = new ListViewAdapter(ShiftListActivity.this, shifts);
        listView.setAdapter(adapter);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShiftListActivity.this, CreateShiftFormActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scheme_list, menu);
        return true;
    }


}
