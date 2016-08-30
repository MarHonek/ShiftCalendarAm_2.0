package mh.shiftcalendaram;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import mh.calendarlibrary.Database.Database;

/**
 * Created by Martin on 29.08.2016.
 */
class ListActionModeMenu implements ActionMode.Callback {

    int statusBarColor;
    Context context;
    Activity ac;
    Class<?> editActivity;

    int listPosition;

    String deleteMessage;
    String deleteTitle;

    public ListActionModeMenu(Activity ac, Class<?> editActivity, int listPosition)
    {
        this.context = ac;
        this.ac = ac;
        this.editActivity = editActivity;
        this.listPosition = listPosition;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {


        if(item.getItemId() == R.id.ic_delete)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setMessage(deleteMessage)
                    .setTitle(deleteTitle)
                    .setPositiveButton("Odstranit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Database database = new Database(context);

                            dialog.dismiss();
                            mode.finish();

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
        else if(item.getItemId() == R.id.ic_edit)
        {
            Intent intent = new Intent(context, editActivity);

            intent.putExtra("position", listPosition);
            context.startActivity(intent);
            mode.finish();
            return true;

        }
        else
            return false;
    }

    public void setTextTitleDelete(String title, String message) {
        this.deleteTitle = title;
        this.deleteMessage = message;
    }

    public void delete() {
        /*database.deleteSymbol(position);
        adapter = new ShiftSymbolsAdapter(context);
        list.setAdapter(adapter);*/
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
