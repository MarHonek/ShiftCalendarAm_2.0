package mh.shiftcalendaram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;


import java.util.ArrayList;

import mh.calendarlibrary.Schemes;
import mh.shiftcalendaram.Adapters.ExpandableList;

public class SchemeListActivity extends AppCompatActivity {

  //TODO: dodelat drawable k tlacitkum
    ArrayList<Schemes> schemes;

    boolean showSchemeGroup;

    int schemeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showSchemeGroup = getIntent().getBooleanExtra("showSchemeGroup", true);

        schemes = Schemes.createList();
        ArrayList<String> schemes = Schemes.getStringArray();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.scheme_list_row, R.id.header_text, schemes);
        final ExpandableList expandableList = (ExpandableList)findViewById(R.id.listview);
        expandableList.setExpand(showSchemeGroup);
        expandableList.setAdapter(arrayAdapter);
        expandableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(showSchemeGroup) {
                    hideGroupsBySchemeNumber(view, i);
                    schemeIndex = i;
                } else {
                    Intent intent = getIntent();
                    intent.putExtra("positionOfScheme", i);
                    setResult(1, intent);
                    finish();
                }
            }
        });

    }

    public void hideGroupsBySchemeNumber(View view, int index) {
        Button bC = (Button)view.findViewById(R.id.button_scheme_choose_C);
        Button bD = (Button)view.findViewById(R.id.button_scheme_choose_D);

        int groupTypes = schemes.get(index).getNumberOfSchemes();
        if(groupTypes < 4) {
            bD.setVisibility(View.GONE);
        }
        if(groupTypes < 3) {
            bC.setVisibility(View.GONE);
        }

    }


    public void chooseGroup(View view) {

        Button btn = (Button)view;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("account", -1);
        editor.commit();

        Intent i = getIntent();
        i.putExtra("scheme", schemeIndex);
        i.putExtra("schemeGroup", btn.getText().toString());
        setResult(2, i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scheme_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_show_mode) {
            item.setChecked(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }



}
