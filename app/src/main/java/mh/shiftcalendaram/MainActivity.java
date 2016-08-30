package mh.shiftcalendaram;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Calendar;

import mh.calendarlibrary.CalendarView;
import mh.calendarlibrary.Holidays;
import mh.calendarlibrary.MonthDay;
import mh.calendarlibrary.Schemes;
import mh.calendarlibrary.Database.Database;
import mh.calendarlibrary.Templates.AccountTemplate;
import mh.calendarlibrary.Templates.ChangedShiftTemplate;
import mh.calendarlibrary.Templates.NoteTemplate;
import mh.shiftcalendaram.calendarHeader.FragmentHeaderChangeble;
import mh.shiftcalendaram.calendarHeader.FragmentHeaderScheme;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDatePickListener, CalendarView.OnChangeShiftListener, CalendarView.onCalendarDayClickListener {

    Database database;

    CalendarView calendarView;
    Toolbar toolbar;

    Drawer result;

    AccountHeader headerResult;
    ArrayList<IProfile> profiles;



    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    int year;
    int month;

    int accountIndex = -1;
    ArrayList<AccountTemplate> accounts;

//    Fragment fr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //  setShadows();

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        database = new Database(MainActivity.this);
        profiles = getProfiles(database);
        accounts = database.getAccounts();


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
                            startActivityForResult(new Intent(MainActivity.this, CreateAccountFormActivity.class), 1000);
                        }
                        else if (profile.getIdentifier() == 1001) {
                            startActivityForResult(new Intent(MainActivity.this, AccountListActivity.class), 1001);
                        } else {
                            int profileIndex = ((int) profile.getIdentifier());
                            accountIndex = accounts.get(profileIndex).getID();
                            editor.putInt("account", accountIndex);
                            editor.commit();


                            calendarView.reset(year, month);
                            calendarView.setAccount(accountIndex);
                        }

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
                        new PrimaryDrawerItem().withName("Směny").withIcon(R.drawable.ic_shifts),
                        new PrimaryDrawerItem().withName("Náhledy kalendářů").withIcon(R.drawable.ic_scheme),
                        new PrimaryDrawerItem().withName("Statistika").withIcon(R.drawable.ic_chart),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Zálohování").withIcon(R.drawable.ic_backup),
                        new PrimaryDrawerItem().withName("O aplikaci").withIcon(R.drawable.ic_about)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 1:
                                Intent i = new Intent(MainActivity.this, ShiftListActivity.class);
                                startActivity(i);
                                break;
                            case 2:
                                startActivityForResult(new Intent(MainActivity.this, SchemeListActivity.class), 2);
                                result.closeDrawer();
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, ShiftListActivity.class));
                        }


                        return true;
                    }
                })
                .build();


        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);

        accountIndex = sharedPref.getInt("account", -1);

        if(accountIndex == -1) {
            calendarView.setAccount(-1, "A");
        }
        else {
            calendarView.setAccount(accountIndex);
        }

        calendarView.setOnDatePickListener(this);
        calendarView.setOnChangeShiftListener(this);
        calendarView.setOnCalendarDayClickListener(this);

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
        year = monthDay.getCalendar().get(Calendar.YEAR);
        month = monthDay.getCalendar().get(Calendar.MONTH);
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

/*    @Override
    protected void onResume() {
        super.onResume();
        profiles = getProfiles(database);
        headerResult.setProfiles(profiles);
        headerResult.addProfiles(
                new ProfileSettingDrawerItem().withName("Přidat kalendář").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(1000),
                new ProfileSettingDrawerItem().withName("Správa kalendářů").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(1001)
        );

       /* int accountIndex = sharedPref.getInt("account", -1);
        ArrayList<AccountTemplate> accounts = database.getAccounts();
        calendarView.setAccount(accounts.get(accountIndex).getShiftSchemeID(), accounts.get(accountIndex).getShiftSchemeGroup());
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2) {
            calendarView.reset(year, month);
            calendarView.setAccount(data.getIntExtra("scheme",0), data.getStringExtra("schemeGroup"));
            accountIndex = -1;
        } else if(resultCode == 1000) {
            profiles = getProfiles(database);
            headerResult.setProfiles(profiles);
            headerResult.addProfiles(
                    new ProfileSettingDrawerItem().withName("Přidat kalendář").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(1000),
                    new ProfileSettingDrawerItem().withName("Správa kalendářů").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(1001)
            );
            headerResult.setActiveProfile(profiles.get(profiles.size()-3));


            accounts = database.getAccounts();
            accountIndex = accounts.get(accounts.size()-1).getID();
            editor.putInt("account", accountIndex);
            editor.commit();
            calendarView.reset(year, month);
            calendarView.setAccount(accountIndex);
        }
        else if(requestCode == 2000) {
            calendarView.reset(year, month);
            calendarView.setAccount(accountIndex);
        }


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
            profiles.add(new ProfileDrawerItem().withName(accounts.get(i).getName()).withEmail(schemes.get(accounts.get(i).getShiftSchemeID()) + " -  " + accounts.get(i).getShiftSchemeGroup()).withIcon(accountIcon).withNameShown(true).withIdentifier(i));
        }

        return profiles;
    }


    @Override
    public void onChangeShift(CalendarView view, MonthDay monthDay) {
        Calendar calendar = monthDay.getCalendar();
        Intent intent = new Intent(MainActivity.this, ChangeShiftActivity.class);
        intent.putExtra("day", calendar.get(Calendar.DAY_OF_MONTH));
        intent.putExtra("month", calendar.get(Calendar.MONTH));
        intent.putExtra("year", calendar.get(Calendar.YEAR));
        if (accountIndex != -1) {
            intent.putExtra("accountIndex", accountIndex);
            startActivityForResult(intent, 2000);
        }
    }

    @Override
    public void onCalendarDayClick(CalendarView view, MonthDay monthDay) {
        Calendar day = monthDay.getCalendar();
        Holidays holiday = new Holidays(day.getTimeInMillis());
        ArrayList<NoteTemplate> notes = database.getNotesByAccount(accountIndex);
        String note = null;
        for(int i = 0;i < notes.size();i++) {
            if(notes.get(i).getDay() == day.get(Calendar.DAY_OF_MONTH) && notes.get(i).getMonth() == day.get(Calendar.MONTH) && notes.get(i).getYear() == day.get(Calendar.YEAR)) {
                note = notes.get(i).getNotes();

            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        if(holiday.isTodayHoliday())
        {
            builder.setMessage("Svátek:\n" + holiday.getNameByDay())
                    .setTitle("Poznámky a svátky")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
        }
        else if(note != null)
        {
            builder.setMessage("Poznámka:\n"+ note)
                    .setTitle("Poznámky a svátky")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            dialog.dismiss();
                        }
                    });
        }
        else
        {
            builder.setMessage("Poznámka:\n"+note + "\n\n" + "Svátek:\n"+holiday.getNameByDay())
                    .setTitle("Poznámky a svátky")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            dialog.dismiss();
                        }
                    });
        }
        // 3. Get the AlertDialog from create()
        if(holiday.isTodayHoliday())
        {
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

}
