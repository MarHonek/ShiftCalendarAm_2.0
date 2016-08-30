package mh.shiftcalendaram;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mh.calendarlibrary.Database.Database;
import mh.calendarlibrary.Templates.ChangedShiftTemplate;
import mh.calendarlibrary.Templates.ShiftTemplate;
import mh.shiftcalendaram.Adapters.ListViewAdapter;

public class ChangeShiftActivity extends AppCompatActivity {

    Database database;

    String color;
    LinearLayout shiftLayout, icon;
    ImageView deleteShift;

    GradientDrawable background;


    /* ArrayList<ShiftSymbolTemplates> list;
     ArrayList<ShiftNotesTemplate> notes;
     ShiftSymbolTemplates symbol;*/
    int shiftIndex;
    EditText note;

    TextView changeShiftTitle, iconText, date;
    ImageView deleteShiftIcon;
    Button deleteNote;
//    SharedPreferences pref;

    String noteText = "";
    ArrayList<ShiftTemplate> list;

    int symbolEditIndex;

    int accountIndex;

    boolean editAlter = false;
    boolean editNote = false;

    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_shift);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setShadows();

        shiftLayout = (LinearLayout) findViewById(R.id.linearLayout_change_shift);
        changeShiftTitle = (TextView) findViewById(R.id.textView_change_shift_title);
        iconText = (TextView) findViewById(R.id.textView_change_shift_icon_text);
        icon = (LinearLayout) findViewById(R.id.linearLayout_change_shift_circle);
        date = (TextView) findViewById(R.id.textView_change_shift_date);
        deleteShift = (ImageView) findViewById(R.id.imageView_change_shift_delete_shift);
        deleteShiftIcon = (ImageView) findViewById(R.id.imageView_change_shift_delete_shift);

        note = (EditText) findViewById(R.id.editext_change_snift_note);
        deleteNote = (Button) findViewById(R.id.button_change_shift_delete_text);

        background = (GradientDrawable) icon.getBackground();
        background.setColor(ContextCompat.getColor(ChangeShiftActivity.this, R.color.colorPrimary));

        database = new Database(ChangeShiftActivity.this);

        Intent i = getIntent();
        day = i.getIntExtra("day", 1);
        month = i.getIntExtra("month", 0);
        year = i.getIntExtra("year", 0);
        accountIndex = i.getIntExtra("accountIndex", -1);

        date.setText(day + ". " + String.valueOf(month + 1) + ". " + year);

        // getDataFromDatabase();

        color = "#ff9800";
        shiftIndex = -1;


        deleteShiftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // database.deleteChangedShift(positionOfCalendar, month, year, accountIndex);

                background = (GradientDrawable) icon.getBackground();
                background.setColor(ContextCompat.getColor(ChangeShiftActivity.this, R.color.colorPrimary));

                iconText.setText("?");
                changeShiftTitle.setText("Vybrat směnu");

                shiftIndex = -1;
                deleteShiftIcon.setVisibility(View.INVISIBLE);

            }
        });

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setText("");
            }
        });

        shiftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShiftDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.ic_accept) {
            if (shiftIndex != -1) {
                /*String color = String.format("#%06X", (0xFFFFFF & list.get(positionOfSymbol).getColor()));
                if(editAlter)
                {

                   database.updateAlter(positionOfCalendar,month,year,positionOfCustom,list.get(positionOfSymbol).getShortTitle(), color);
                }
                else*/
                //   database.insertAlternative(list.get(positionOfSymbol).getShortTitle(), positionOfCalendar,month,year,positionOfCustom, color);

                //TODO: overit zda accountIndex se rovná indexu v databázi
                Log.v("qwqd", "wdqdqdqdwdqd");
                database.insertChangedShift(list.get(shiftIndex).getID(), day, month, year, accountIndex);
            }


            if (note.getText().length() > 0) {
               /* if (editNote)
                    database.updateNote(day,month,year,accountIndex,note.getText().toString());
                else
                    database.insertNote(positionOfCalendar, month,year, note.getText().toString(),positionOfCustom);*/
                database.insertNote(day, month, year, note.getText().toString(), accountIndex);

            } else {
                //  database.deleteNotes(day,month,year, accountIndex);
            }

            setResult(2000, null);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openShiftDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeShiftActivity.this);

        View view = getLayoutInflater().inflate(R.layout.change_shift_list, null);

        ListView listView = (ListView) view.findViewById(R.id.listView_shift);

        list = database.getShifts();
        ListViewAdapter adapter = new ListViewAdapter(ChangeShiftActivity.this, list);
        listView.setAdapter(adapter);

        builder.setTitle("Vyberte směnu");
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {

                shiftIndex = index;

                changeShiftTitle.setText(list.get(shiftIndex).getName());
                iconText.setText(list.get(shiftIndex).getShortName());
                GradientDrawable background = (GradientDrawable) icon.getBackground();
                background.setColor(list.get(shiftIndex).getColor());
                deleteShiftIcon.setVisibility(View.VISIBLE);
                alert.dismiss();

            }

        });
    }

  /*  public void getDataFromDatabase() {
         ArrayList<ChangedShiftTemplate> alter;
        ArrayList<ShiftTemplate> shifts;
        for (int i = 0; i<alter.size();i++)
        {
            if ((alter.get(i).getDay() == day) && (alter.get(i).getMonth() == month) && (alter.get(i).getYear()) == year &&(alter.get(i).getAccID() == accountIndex))
            {

                for (int j = 0;j<list.size();j++)
                {
                    shifts.get(j).getShortName()
                    if (list.get(j).getShortTitle().equals(alter.get(i).getKind())) {
                        changeShiftTitle.setText(list.get(j).getTitle());
                        symbolEditIndex = j;
                    }
                }
                background = (GradientDrawable) icon.getBackground();
                background.setColor(Color.parseColor(alter.get(i).getColor()));

                iconText.setText(alter.get(i).getKind());
                deleteShiftIcon.setVisibility(View.VISIBLE);
                editAlter = true;
            }
        }
    }*/


    public void setShadows() {
        View shadow = (View) findViewById(R.id.shadow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shadow.setVisibility(View.GONE);
        }
    }
}
