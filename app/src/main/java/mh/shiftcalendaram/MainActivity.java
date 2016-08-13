package mh.shiftcalendaram;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mh.calendarlibrary.CalendarView;
import mh.calendarlibrary.MonthDay;
import mh.shiftcalendaram.Database.Database;
import mh.shiftcalendaram.Templates.AccountTemplate;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDatePickListener {

    DrawerLayout drawerLayout;
    CalendarView calendarView;
    Toolbar toolbar;

    LinearLayout drawerCircle;
    TextView drawerName, drawerShortName, drawerCircleText;

    Database database;
    ArrayList<AccountTemplate> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setShadows();

        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        calendarView.setOnDatePickListener(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);


        database = new Database(MainActivity.this);
        accounts = database.getAccounts();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(MainActivity.this, ShiftListActivity.class));

                        break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, CreateAccountFormActivity.class));
                        break;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View header = navigationView.getHeaderView(0);

        drawerName = (TextView)header.findViewById(R.id.drawer_name);
        drawerShortName = (TextView)header.findViewById(R.id.drawer_short_name);
        drawerCircleText = (TextView)header.findViewById(R.id.textView_circle_text);
        drawerCircle = (LinearLayout)header.findViewById(R.id.linearLayout_circle);

        Log.v("dwq", String.valueOf(accounts.size()));
        if(accounts.size() > 0) {
            drawerName.setText(accounts.get(0).getName());
            drawerShortName.setText(Schemes.getStringArray().get(accounts.get(0).getShiftSchemeID()));
            drawerCircleText.setText(accounts.get(0).getShiftSchemeGroup());
            GradientDrawable background = (GradientDrawable) drawerCircle.getBackground();
            background.setColor(Color.parseColor(accounts.get(0).getColorHex()));
        }



    }

    public void setShadows() {
        View shadow = (View)findViewById(R.id.shadow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shadow.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.ic_today) {
            calendarView.backToToday();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDatePick(CalendarView view, MonthDay monthDay) {
        int year = monthDay.getCalendar().get(Calendar.YEAR);
        int month = monthDay.getCalendar().get(Calendar.MONTH);
        toolbar.setTitle(monthDay.getMonthStr(month) + " " + String.valueOf(year));

    }
}
