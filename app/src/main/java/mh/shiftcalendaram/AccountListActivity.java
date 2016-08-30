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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mh.shiftcalendaram.Adapters.AccountListViewAdapter;
import mh.calendarlibrary.Database.Database;
import mh.calendarlibrary.Templates.AccountTemplate;

public class AccountListActivity extends AppCompatActivity {

    ListView listView;
    AccountListViewAdapter adapter;
    ArrayList<AccountTemplate> accounts;

    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  setShadows();

        Database database = new Database(AccountListActivity.this);
        accounts = database.getAccounts();
        listView = (ListView)findViewById(R.id.listView_accounts);
        adapter = new AccountListViewAdapter(AccountListActivity.this, accounts);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListActionModeMenu listActionMode = new ListActionModeMenu(AccountListActivity.this, CreateAccountFormActivity.class, i);
                listActionMode.setTextTitleDelete("Smazat kalendář", "Opravdu chcete smazat tento kalendář?");
                mActionMode = toolbar.startActionMode(listActionMode);
                return true;
            }
        });
    //    Toast.makeText(AccountListActivity.this, accounts.get(0).getName()+", "+accounts.get(0).getShiftSchemeGroup()+", "+String.valueOf(accounts.get(0).getShiftSchemeID())+", "+accounts.get(0).getColorHex(),Toast.LENGTH_LONG).show();
    }

    public void setShadows() {
        View shadow = (View)findViewById(R.id.shadow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shadow.setVisibility(View.GONE);
        }
    }

}

