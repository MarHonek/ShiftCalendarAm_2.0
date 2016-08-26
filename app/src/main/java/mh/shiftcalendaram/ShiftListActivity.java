package mh.shiftcalendaram;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                mActionMode = toolbar.startActionMode(new ActionBarCallBack(ShiftListActivity.this, ShiftListActivity.this));
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

class ActionBarCallBack implements ActionMode.Callback {

    int statusBarColor;
    Context context;
    Activity ac;

    public ActionBarCallBack(Context context, Activity ac)
    {
        this.context = context;
        this.ac = ac;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

        if(item.getItemId() == R.id.ic_delete)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Opravdu chcete smazat tuto směnu?")
                    .setTitle("Smazat směnu")
                    .setPositiveButton("Odstranit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                           /* Database data = new Database(context);
                            data.deleteSymbol(position);
                            adapter = new ShiftSymbolsAdapter(context);
                            list.setAdapter(adapter);



                            dialog.dismiss();
                            mode.finish();

                            */

                        }
                    });
            builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
        else if(item.getItemId() == R.id.ic_accept)
        {
            /*Intent intent = new Intent(context, CreateShiftSymbolActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            context.startActivity(intent);
            mode.finish();*/
            return true;

        }
        else
            return false;




    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_menu, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //hold current color of status bar
            statusBarColor = ac.getWindow().getStatusBarColor();
            //set your gray color
            ac.getWindow().setStatusBarColor(0xFF555555);
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //return to "old" color of status bar
            ac.getWindow().setStatusBarColor(statusBarColor);
        }

    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub

        return false;
    }

}
