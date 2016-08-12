package mh.shiftcalendaram;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.w3c.dom.Text;
import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

public class CreateAccountFormActivity extends AppCompatActivity {

    TextInputLayout nameLayout, schemeLayout;
    EditText name, scheme;


    MultiStateToggleButton button;
    ImageView palette;
    AppBarLayout head;
    int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        name = (EditText)findViewById(R.id.edit_text_createAccount_name);
        nameLayout = (TextInputLayout)findViewById(R.id.input_layout_createAccount_name);
        scheme = (EditText)findViewById(R.id.edit_text_createAccount_scheme);
        schemeLayout = (TextInputLayout)findViewById(R.id.input_layout_createAccount_scheme);

        head = (AppBarLayout)findViewById(R.id.create_account_head);
        palette = (ImageView)findViewById(R.id.imageView_palette);

        button = (MultiStateToggleButton) this.findViewById(R.id.toggleButton_createAccount_scheme);
        button.setValue(0);
       // button.setEnabled(false);

        selectedColor = ContextCompat.getColor(getBaseContext(), R.color.red);
        int[] mColors = getResources().getIntArray(R.array.rainbow);


        //TODO: (nespecha)upravit ColorPicker aby se neopakoval ve vsech activitach
        final ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                mColors,
                selectedColor,
                5,
                ColorPickerDialog.SIZE_SMALL);


        palette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePalette(dialog);

            }
        });

        scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountFormActivity.this, SchemeListActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.ic_accept) {
            CheckAndSetErrors();
        }
        return super.onOptionsItemSelected(item);
    }

    public void usePalette(ColorPickerDialog dialog) {

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                selectedColor = color;
                head.setBackgroundColor(selectedColor);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Colors.convertColorToDark(selectedColor));
                }
            }
        });

        dialog.show(getFragmentManager(), "color_dialog_test");

    }
    //TODO: opravit Snackbar. Zobrazuje se chybně
    public void CheckAndSetErrors() {

        if(nameLayout.getError() != null) {
            Snackbar.make(getCurrentFocus(), "Povinné položky nejsou vyplněny správně", Snackbar.LENGTH_LONG).show();
        }

        if(name.getText().length() == 0) {
            nameLayout.setError("Vyplňte název");
        } else {
            nameLayout.setErrorEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
  //      button.setEnabled(true);
        button.setAlpha(1);

    }
}
