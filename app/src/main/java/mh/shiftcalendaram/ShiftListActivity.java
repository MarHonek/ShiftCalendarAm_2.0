package mh.shiftcalendaram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import mh.shiftcalendaram.Adapters.ListViewAdapter;
import mh.calendarlibrary.Database.Database;
import mh.calendarlibrary.Templates.ShiftTemplate;

public class ShiftListActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    ArrayList<ShiftTemplate> shifts;
    Database database;

    ActionMode mActionMode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_manage);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(ShiftListActivity.this);

        shifts = database.getShifts();

        listView = (ListView)findViewById(R.id.listView_sifts);
        adapter = new ListViewAdapter(ShiftListActivity.this, shifts);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                ListActionModeMenu listActionMode = new ListActionModeMenu(ShiftListActivity.this, CreateShiftFormActivity.class, index);
                listActionMode.setTextTitleDelete("Smazat směnu", "Opravdu chcete smazat tuto směnu?\n \n(Směna bude  kalendáře)");
                mActionMode = toolbar.startActionMode(listActionMode);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShiftListActivity.this, CreateShiftFormActivity.class));
            }
        });
    }


}


