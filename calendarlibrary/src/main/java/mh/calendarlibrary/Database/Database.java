package mh.calendarlibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import mh.calendarlibrary.MonthView;
import mh.calendarlibrary.Templates.AccountTemplate;
import mh.calendarlibrary.Templates.ShiftTemplate;

/**
 * Created by Martin on 01.08.2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final long serialVersionUID = 1L;
    static int databaseVersion = 4;
    static String databaseName = "DBCalendar";

    String createTableAccounts = "CREATE TABLE Accounts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, shiftSchemeGroup TEXT, shiftSchemeID INTEGER, color TEXT, desc TEXT)";

    String createTable2 = "CREATE TABLE alternative ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "kind TEXT, "+
            "position INTEGER, month TEXT, year TEXT, custom INTEGER, color TEXT )";

    String createTable3 = "CREATE TABLE notes ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, position INTEGER, month TEXT, year TEXT, note TEXT, custom INTEGER )";

    String createTableShifts = "CREATE TABLE Shifts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, short TEXT, color TEXT, timeFrom TEXT, timeTo TEXT, desc TEXT)";


    public Database(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public void insertDefaultSymbols(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('Náhradní volno', 'NV', '#9b1c20')");
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('Dovolená', 'D', '#1abc9c')");
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('\"Z\" volno', 'Z', '#9b59b6')");
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('Paragraf', 'P', '#99CC00')");
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('Přesčas', 'PČ', '#3498db')");
        db.execSQL("INSERT INTO Shifts (name, short, color) VALUES ('Odpolední+Noční', 'O+N', '#D7DF01')");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableAccounts);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTableShifts);

        insertDefaultSymbols(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion<2) {
            db.execSQL(createTable3);
            db.execSQL(createTableShifts);

            insertDefaultSymbols(db);
        }
        else if (oldVersion < 3)
        {
            db.execSQL(createTableShifts);

            insertDefaultSymbols(db);
        }
        else if (oldVersion < 4) {
          //  repairAccountTable(db);
            renameSymbolsToShifts(db);
        }
        else
        {
            Log.v("HOlo", "hmm");
            db.execSQL("DROP TABLE IF EXISTS Accounts");
            db.execSQL("DROP TABLE IF EXISTS alternative");
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("DROP TABLE IF EXISTS symbols");
        }

    }

    public void insertAccount(String name, String shiftSchemeGroup, int shiftSchemeID, String color, String desc) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("shiftSchemeGroup", shiftSchemeGroup);
        values.put("shiftSchemeID", shiftSchemeID);
        values.put("color", color);
        values.put("desc", desc);

        db.insert("Accounts",null, values);
        Log.v("wdwd", "OK");
        db.close();
    }

    public void insertShifts(String name, String shortName, String color, String timeFrom, String timeTo, String desc) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("short", shortName);
        values.put("color", color);
        values.put("timeFrom", timeFrom);
        values.put("timeTo", timeTo);
        values.put("desc", desc);

        db.insert("Shifts", null, values);
        db.close();
    }

    public void insertAlternative(String kind, int position, String month, String year, int custom, String color)
    {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("kind", kind);
        values.put("position", position);
        values.put("month", month);
        values.put("year", year);
        values.put("custom", custom);
        values.put("color", color);

        db.insert("alternative", null, values);

        db.close();


    }

    public void insertNote(int position, String month, String year, String note, int custom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("position", position);
        values.put("month", month);
        values.put("year", year);
        values.put("note", note);
        values.put("custom", custom);

        db.insert("notes", null, values);

        db.close();
    }

    /*
    ------------------------------------------------------------------------------------------------------------
                                            Get method
    ------------------------------------------------------------------------------------------------------------
    */

   /* public ArrayList<AlternativeShifts> getSpecial()
    {

        ArrayList<AlternativeShifts> list = new ArrayList<AlternativeShifts>();
        String query = "SELECT * FROM alternative";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                String kind = cursor.getString(1);
                int position = cursor.getInt(2);
                String month = cursor.getString(3);
                String year = cursor.getString(4);
                int custom = cursor.getInt(5);
                String color = cursor.getString(6);



                list.add(new AlternativeShifts(kind, position, month, year, custom, color));
            } while (cursor.moveToNext());
        }

        return list;
    }*/


    /*public ArrayList<ShiftNotesTemplate> getTextNote()
    {

        ArrayList<ShiftNotesTemplate> list = new ArrayList<ShiftNotesTemplate>();

        String query = "SELECT * FROM notes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String text = "";
        int position = 0;
        String month = "";
        String year = "";
        int custom = -1;
        if (cursor.moveToFirst()) {
            do
            {
                position = cursor.getInt(1);
                month = cursor.getString(2);
                year = cursor.getString(3);
                text = cursor.getString(4);
                custom = cursor.getInt(5);

                if(custom != -1)
                    list.add(new ShiftNotesTemplate(position, month, year, text, custom));

            }while(cursor.moveToNext());

        }
        return list;
    }
*/











    public ArrayList<AccountTemplate> getAccounts() {

        String query = "SELECT  * FROM Accounts";

        ArrayList<AccountTemplate> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        AccountTemplate account = null;
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(1);
                String shiftSchemeGroup = cursor.getString(2);
                int shiftSchemeID = cursor.getInt(3);
                String color = cursor.getString(4);
                String desc = cursor.getString(5);
                Log.v("wd", "ddw");
                account = new AccountTemplate(name, shiftSchemeGroup, shiftSchemeID, color, desc);
                accounts.add(account);
            } while (cursor.moveToNext());
        }
        Log.v("dw", "dwd");
        return accounts;
    }

    public ArrayList<ShiftTemplate> getShifts() {

        ArrayList<ShiftTemplate> list = new ArrayList<ShiftTemplate>();
        String query = "SELECT * FROM Shifts";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String shortName = cursor.getString(2);
                String color = cursor.getString(3);
                String desc  = "dw";

                list.add(new ShiftTemplate(name, shortName, color, desc));
            } while (cursor.moveToNext());
        }
        return list;
    }


    /*
    ------------------------------------------------------------------------------------------------------------
                                            Update method
    ------------------------------------------------------------------------------------------------------------
    */

    public void updateSymbols(String name, String shortTitle, String color, int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String update ="UPDATE alternative SET kind='"+shortTitle+"', color='"+color+"' WHERE kind in (SELECT short FROM symbols WHERE id in (SELECT id FROM symbols LIMIT 1 OFFSET "+position+"))";

        String d ="UPDATE symbols SET name='"+name+"', short='"+shortTitle+"', color='"+color+"' WHERE id in (SELECT id FROM symbols LIMIT 1 OFFSET "+position+")";
        db.execSQL(update);
        db.execSQL(d);


        db.close();
    }



    public void updateAccounts(String name, String shiftSchemeGroup, int shiftSchemeID, String color, int positionInList)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String update ="UPDATE Accounts SET name='"+name+"', shiftSchemeGroup='"+shiftSchemeGroup+"', shiftSchemeID="+shiftSchemeID+", color="+color+" WHERE id in (SELECT id FROM Accounts LIMIT 1 OFFSET "+positionInList+")";

        db.execSQL(update);
        db.close();
    }

    public void updateNote(int day, String month, String year, int custom, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        String update ="UPDATE notes SET note='"+note+"' WHERE position="+day+" AND month='"+month+"' AND year='"+year+"' AND custom="+custom+"";

        db.execSQL(update);
        db.close();
    }


    /*
    ------------------------------------------------------------------------------------------------------------
                                            Delete method
    ------------------------------------------------------------------------------------------------------------
    */

    public void deleteAlter(int position, String month, String year, int positionOfCustom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM alternative WHERE position="+position+" and month='"+month+"' and year='"+year+"' and custom='"+positionOfCustom+"'";
        db.execSQL(d);

        db.close();
    }


    public void updateAlter(int day, String month, String year, int custom, String kind, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        String update ="UPDATE alternative SET kind='"+kind+"', color='"+color+"' WHERE position="+day+" AND month='"+month+"' AND year='"+year+"' AND custom="+custom;

        db.execSQL(update);
        db.close();
    }




    public void deleteAccounts(int positionInList) {

        SQLiteDatabase db = this.getWritableDatabase();

        String d = "DELETE FROM Accounts WHERE id in (SELECT id FROM Shifts LIMIT 1 OFFSET " + positionInList + ")";
        db.execSQL(d);

        db.close();
    }


    public void deleteSymbol(int position) {


        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM symbols WHERE id in (SELECT id FROM symbols LIMIT 1 OFFSET "+position+")";
        db.execSQL(d);
        db.close();


    }


    public void deleteNotes(int position, String month, String year, int custom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM notes WHERE position="+position+" and month='"+month+"' and year='"+year+"' and custom='"+custom+"'";
        db.execSQL(d);
        db.close();
    }

    public void repairAccountTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Accounts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, shiftSchemeGroup TEXT, shiftSchemeID INTEGER, color TEXT, desc TEXT)");

        String query = "SELECT * FROM shifts";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String  shiftSchemeGroup= cursor.getString(2);
                int shiftSchemeID= cursor.getInt(3);
                int color = cursor.getInt(4);

                String hexColor = String.format("#%06X", (0xFFFFFF & color));

                db.execSQL("INSERT INTO Accounts (id, title, shiftSchemeGroup, shiftSchemeID, color, desc) VALUES ("+id+", '"+name+"', '"+shiftSchemeGroup+"', "+shiftSchemeID+", '"+hexColor+"', '')");
            } while (cursor.moveToNext());
        }
        db.execSQL("DROP TABLE shifts");
    }

    public void renameSymbolsToShifts(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE symbols RENAME TO Shifts");
    }
    //TODO: zmenit position na day, zmenit kind na shiftID, zmenit custom na accountID, zjistit zda acc souhlasi s ID

    public void repairChangedShiftsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ChangedShifts (id INTEGER PRIMARY KEY AUTOINCREMENT, shiftID INTEGER, day INTEGER, month INTEGER, year INTEGER, accountID INTEGER, color TEXT ");

        String query = "SELECT * FROM alternative";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String kind = cursor.getString(1);
                int dayPosition = cursor.getInt(2);
                String monthString = cursor.getString(3);
                String yearString = cursor.getString(4);
                int acc = cursor.getInt(5);
                String color = cursor.getString(6);

                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);


                String q = "SELECT * FROM shifts WHERE short='"+kind+"'";
                Cursor c = db.rawQuery(q, null);
                int shiftID = c.getInt(0);

                int day = MonthView.get(dayPosition, month, year);

                db.execSQL("INSERT INTO ChangedShifts (id, shiftID, day, month, year, accountID, color) VALUES ("+id+", "+shiftID+", '"+day+"', "+month+", "+year+", "+acc+", '"+color+"')");
            } while (cursor.moveToNext());
        }
        db.execSQL("DROP TABLE alternative");
    }

    public void repairNotesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TEMP_TABLE (id integer primary key autoincrement, day INTEGER, month INTEGER, year INTEGER, note TEXT, acc INTEGER ");

        String query = "SELECT * FROM notes";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int dayPosition = cursor.getInt(1);
                String monthString = cursor.getString(2);
                String yearString = cursor.getString(3);
                String note = cursor.getString(4);
                int acc = cursor.getInt(5);

                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);

                int day = MonthView.get(dayPosition, month, year);

                db.execSQL("INSERT INTO TEMP_TABLE (id, position, month, year, note, custom) VALUES ("+id+", "+day+", "+month+", "+year+", '"+note+"', "+acc+")");
            } while (cursor.moveToNext());
        }
        db.execSQL("DROP TABLE notes");

        db.execSQL("ALTER TABLE TEMP_TABLE RENAME TO notes");
    }
}
