package mh.shiftcalendaram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
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
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mh.calendarlibrary.CalendarView;
import mh.calendarlibrary.MonthDay;
import mh.calendarlibrary.Schemes;
import mh.shiftcalendaram.Database.Database;
import mh.shiftcalendaram.Templates.AccountTemplate;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDatePickListener {

    Database data;

    CalendarView calendarView;
    Toolbar toolbar;

    Drawer result;


    AccountHeader headerResult;
    ArrayList<IProfile> profiles;


    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setShadows();


        int accountIndex = sharedPref.getInt("account", -1);
        int schemeIndex = sharedPref.getInt("scheme", 0);
        String schemeGroup = sharedPref.getString("schemeGroup", "");

        data = new Database(MainActivity.this);
        profiles = getProfiles(data);


        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_wallpaper_small)
                .withProfiles(profiles)
                .withCurrentProfileHiddenInList(true)
                .addProfiles(
                        new ProfileSettingDrawerItem().withName("Přidat kalendář").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(1000),
                        new ProfileSettingDrawerItem().withName("Správa kalendářů").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(1001)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        if (profile.getIdentifier() == 1000) {
                            startActivity(new Intent(MainActivity.this, CreateAccountFormActivity.class));
                        }

                        editor.putInt("account", (int) profile.getIdentifier());
                        editor.putInt("scheme", -1);
                        editor.putString("schemeGroup", "-");
                        editor.commit();

                        calendarView.reset();

                        ArrayList<AccountTemplate> accounts = data.getAccounts();
                        calendarView.setAccount(accounts.get((int) profile.getIdentifier()).getShiftSchemeID(), accounts.get((int) profile.getIdentifier()).getShiftSchemeGroup());

                        result.resetDrawerContent();
                        result.closeDrawer();
                        return true;
                    }
                })
                .build();


        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("schémata"),
                        new PrimaryDrawerItem().withName("stále schéma"),
                        new SecondaryDrawerItem().withName("dwq")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.v("positiin", String.valueOf(position));
                        switch (position) {
                            case 1:
                                startActivity(new Intent(MainActivity.this, SchemeListActivity.class));
                                break;
                        }


                        return true;
                    }
                })
                .build();

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);

        if (accountIndex <= -1) {
            ArrayList<AccountTemplate> accounts = data.getAccounts();
            calendarView.setAccount(accounts.get(accountIndex).getShiftSchemeID(), accounts.get(accountIndex).getShiftSchemeGroup());
        } else {
            calendarView.setAccount(schemeIndex, schemeGroup);
        }
        calendarView.setOnDatePickListener(this);


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

    @Override
    protected void onResume() {
        super.onResume();
        profiles = getProfiles(data);
        headerResult.setProfiles(profiles);
        headerResult.addProfiles(
                new ProfileSettingDrawerItem().withName("Přidat kalendář").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(1000),
                new ProfileSettingDrawerItem().withName("Správa kalendářů").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(1001)
        );


    }

    public ArrayList<IProfile> getProfiles(Database database) {
        ArrayList<String> schemes = Schemes.getStringArray();
        ArrayList<AccountTemplate> accounts = database.getAccounts();
        ArrayList<IProfile> profiles = new ArrayList<>();



        for (int i = 0; i<accounts.size();i++) {
            IconicsDrawable accountIcon = new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_person).sizeDp(100);
            accountIcon.color(Color.WHITE);
            accountIcon.paddingDp(25);
            accountIcon.alpha(230);
            accountIcon.backgroundColor(accounts.get(i).getColor());
            profiles.add(new ProfileDrawerItem().withName(accounts.get(i).getName()).withEmail(schemes.get(accounts.get(i).getShiftSchemeID()) + " -  " + accounts.get(i).getShortName()).withIcon(accountIcon).withNameShown(true).withIdentifier(i));
        }

        return profiles;
    }
}
