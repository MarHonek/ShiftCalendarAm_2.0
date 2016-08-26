package mh.shiftcalendaram;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import mh.calendarlibrary.Database.Database;

public class CreateShiftFormActivity extends AppCompatActivity {

    TextInputLayout nameLayout, shortNameLayout;
    EditText name, shortName, alarm;
    TextView startWork, endWork, startWorkLabel, endWorkLabel;
    SwitchCompat allDaySwitch;

    ImageView palette;
    AppBarLayout head;
    int selectedColor;
    String strColor;

    int hour, minute;

    TimePickerDialog timeDialog;

    Database data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        hour = 8;
        minute = 0;

        data = new Database(CreateShiftFormActivity.this);

        alarm = (EditText) findViewById(R.id.editText_createShift_alarm);
        startWork = (TextView)findViewById(R.id.textView_createShift_workStart_time);
        endWork = (TextView)findViewById(R.id.textView_createShift_workEnd_time);
        startWorkLabel = (TextView)findViewById(R.id.textView_createShift_workStart_label);
        endWorkLabel = (TextView)findViewById(R.id.textView_createShift_workEnd_label);
        nameLayout = (TextInputLayout)findViewById(R.id.input_layout_createShift_name);
        shortNameLayout = (TextInputLayout)findViewById(R.id.input_layout_createShift_shortName);
        name = (EditText)findViewById(R.id.edit_text_createShift_name);
        shortName = (EditText)findViewById(R.id.edit_text_createShift_shortName);
        allDaySwitch = (SwitchCompat)findViewById(R.id.toggleButton_createShift_allDay);
        head = (AppBarLayout)findViewById(R.id.create_shift_head);
        palette = (ImageView)findViewById(R.id.imageView_palette);

        selectedColor = ContextCompat.getColor(getBaseContext(), R.color.colorPrimary);
        strColor = String.format("#%06X", 0xFFFFFF & selectedColor);
        int[] mColors = getResources().getIntArray(R.array.rainbow);

        final ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title, mColors, selectedColor, 5, ColorPickerDialog.SIZE_SMALL);

        palette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePalette(dialog);

            }
        });

        allDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                startWork.setEnabled(!isChecked);
                endWork.setEnabled(!isChecked);
                startWorkLabel.setEnabled(!isChecked);
                endWorkLabel.setEnabled(!isChecked);
            }
        });


    }

    public void setAlarm(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateShiftFormActivity.this);
        builder.setTitle("Zvolte čas")
                .setItems(R.array.time_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        /*if(which == 8) {
                            timeDialog = new TimePickerDialog(CreateShiftFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                }
                            }, 0, 0, true);
                            timeDialog.show();
                        }*/
                        selectTime(alarm);
                    }
                });
        builder.show();
    }

    //time setOnClick
    public void selectTime(final View view) {
        final TextView time = (TextView) view;

        timeDialog= new TimePickerDialog(CreateShiftFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                time.setText(String.format("%d:%02d", hourOfDay, minute));
            }
        },12 , 10, true);
        timeDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.ic_accept) {
            if(CheckAndSetErrors() == true) {
                insertShiftToDatabase(data);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void usePalette(final ColorPickerDialog dialog) {

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                selectedColor = color;
                head.setBackgroundColor(selectedColor);
                strColor = String.format("#%06X", 0xFFFFFF & selectedColor);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Colors.convertColorToDark(selectedColor));
                }
            }
        });

        dialog.show(getFragmentManager(), "color_dialog");
    }

    public boolean CheckAndSetErrors() {

        boolean isOk = true;
        if(nameLayout.getError() != null || shortNameLayout.getError() != null) {
            Snackbar.make(getCurrentFocus(), "Povinné položky nejsou vyplněny správně", Snackbar.LENGTH_LONG).show();
        }

        if(shortName.getText().length()> shortNameLayout.getCounterMaxLength()) {
            isOk = false;
            Snackbar.make(getCurrentFocus(), "Zkrátka může obsahovat max. 3 znaky", Snackbar.LENGTH_LONG).show();
        }

        if(name.getText().length() == 0) {
            isOk = false;
            nameLayout.setError("Vyplňte název");
        } else {
            nameLayout.setErrorEnabled(false);
        }

        if(shortName.getText().length() == 0) {
            isOk = false;
            shortNameLayout.setError("Vyplňte zkratku");
        } else {
            shortNameLayout.setErrorEnabled(false);
        }

        return isOk;

    }

    public void insertShiftToDatabase(Database data) {
        data.insertShifts(name.getText().toString(), shortName.getText().toString(), Integer.toHexString(selectedColor), null, null, null);
    }


}
