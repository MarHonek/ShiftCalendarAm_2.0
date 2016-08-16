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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.MiniProfileDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mh.calendarlibrary.CalendarView;
import mh.calendarlibrary.MonthDay;
import mh.shiftcalendaram.Database.Database;
import mh.shiftcalendaram.Templates.AccountTemplate;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDatePickListener {

    CalendarView calendarView;
    Toolbar toolbar;

    Drawer result;


    Database database;
    ArrayList<AccountTemplate> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        IconicsDrawable d = new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_person).sizeDp(100);
        d.color(Color.WHITE);
        d.backgroundColor(getResources().getColor(R.color.colorPrimary));
        d.paddingDp(25);
        d.alpha(230);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_wallpaper_small)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(d)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        Log.v("dw", "OK");
                        return true;
                    }
                })
                .build();


        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("dqw"),
                        new PrimaryDrawerItem().withName("dwdw"),
                        new SecondaryDrawerItem().withName("dwq")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return true;
                    }
                })
                .build();

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        calendarView.setOnDatePickListener(this);

        database = new Database(MainActivity.this);
        accounts = database.getAccounts();



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

    @Override
    public void onBackPressed() {

        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
