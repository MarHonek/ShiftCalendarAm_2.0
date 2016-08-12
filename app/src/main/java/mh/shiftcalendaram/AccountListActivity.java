package mh.shiftcalendaram;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mh.shiftcalendaram.Adapters.ListViewAdapter;
import mh.shiftcalendaram.Database.Database;
import mh.shiftcalendaram.Templates.AccountTemplate;
import mh.shiftcalendaram.Templates.ListTemplate;

public class AccountListActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    ArrayList<AccountTemplate> accounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Database database = new Database(AccountListActivity.this);
       /* for (int i = 0; i < 5;i++) {
            database.insertShift("AAA", "A", 0, Color.RED);
        }*/
        accounts = database.getAccounts();
       /* listView = (ListView)findViewById(R.id.listView_accounts);
        adapter = new ListViewAdapter(AccountListActivity.this, accounts);
        listView.setAdapter(adapter);*/
        Toast.makeText(AccountListActivity.this, accounts.get(0).getName()+", "+accounts.get(0).getShiftSchemeGroup()+", "+String.valueOf(accounts.get(0).getShiftSchemeID())+", "+accounts.get(0).getColorHex(),Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountListActivity.this, CreateAccountFormActivity.class));
            }
        });
    }

}
