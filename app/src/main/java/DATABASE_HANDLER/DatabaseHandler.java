package DATABASE_HANDLER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import INTERFACE.Database_value;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Notes_Detail";

    // Contacts table name
    private static final String Name = "name";
    private static final String TOPIC = "_topic";
    private static final String NOTES = "_NOTES";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String TITTLE = "tittle";
    private static final String TIME = "time";
    private static final String DETAIL = "details";
    private static final String DATE = "date";
    private static final String Name_ = "name_";
    private static final String Topic = "Topic_";
    private static final String Notes = "Notes_";
    private static final String Date = "Date_";
    private static final String Date_withid = "Date_With_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Name + "("
                + Name_ + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        String CREATE_TOPIC_TABLE = "CREATE TABLE " + TOPIC + "("
                + Topic + " TEXT" + ")";
        String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
                + Notes + " TEXT," + Date + " TEXT," + Date_withid + " TEXT" + ")";

        db.execSQL(CREATE_TOPIC_TABLE);
        db.execSQL(CREATE_NOTES);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Name);
        db.execSQL("DROP TABLE IF EXISTS " + TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new appointmentvalues
    public void Save(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name_, name);

        // Database_value Phone

        // Inserting Row
        db.insert(Name, null, values);
        db.close(); // Closing database connection
    }

    public void Save_notes(String notes, String Date_id,String Date_withid_) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notes, notes);
        values.put(Date, Date_id);
        values.put(Date_withid, Date_withid_);

        // Database_value Phone

        // Inserting Row
        db.insertWithOnConflict(NOTES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
    }

    public void Save_Topic(String toppic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Topic, toppic);

        // Database_value Phone

        // Inserting Row
        db.insert(TOPIC, null, values);
        db.close(); // Closing database connection
    }

    public void Delete_all() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Name);

        // Create tables again
        onCreate(db);
    }

    // Getting single contact
    public String GET_NOTES_BY_DATE(String date) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(NOTES, new String[]{Notes}, Date + "=?",
                    new String[]{String.valueOf(date)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            String Str_notes = cursor.getString(0);
            return Str_notes;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
//            Database_value appointmentvalues = new Database_value("",
//                    "", "", "", "");
            String Str_note = "";
            return Str_note;
        }
        // return appointmentvalues

    }

    public String Check_Date(String date) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(NOTES, new String[]{Date}, Date + "=?",
                    new String[]{String.valueOf(date)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            String Str_notes = cursor.getString(0);
            return Str_notes;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
//            Database_value appointmentvalues = new Database_value("",
//                    "", "", "", "");
            String Str_note = "";
            return Str_note;
        }
        // return appointmentvalues

    }

    public String GET_NOTES_and_date_BY_DATE(String date) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(NOTES, new String[]{Notes, Date}, Date + "=?",
                    new String[]{String.valueOf(date)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            String Str_notes = cursor.getString(0);
            //String Str_date
            return Str_notes;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
//            Database_value appointmentvalues = new Database_value("",
//                    "", "", "", "");
            String Str_note = "";
            return Str_note;
        }
        // return appointmentvalues

    }


    public List<Database_value> get_Tittle_from_date(String Str_Date) {
        final List<Database_value> appointmentvaluesList = new ArrayList<Database_value>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(Name, new String[]{KEY_ID, TITTLE,
                            DATE, TIME, DETAIL}, DATE + "=?",
                    new String[]{String.valueOf(Str_Date)}, null, null, null, null);
            if (cursor != null)


//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
                if (cursor.moveToFirst()) {
                    do {
                        Database_value appointmentvalues = new Database_value();
                        appointmentvalues.set_TITTLE(cursor.getString(1));
                        appointmentvalues.set_DATE(cursor.getString(2));
                        appointmentvalues.set_TIME(cursor.getString(3));
                        appointmentvalues.set_DETAIL(cursor.getString(4));
                        appointmentvalues.setID(cursor.getString(0));
                        // Adding appointmentvalues to list
                        appointmentvaluesList.add(appointmentvalues);
                    } while (cursor.moveToNext());
                }
            return appointmentvaluesList;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Database_value appointmentvalues = new Database_value();
            appointmentvalues.set_TITTLE("");
            appointmentvalues.set_DATE("");
            appointmentvalues.set_TIME("");
            appointmentvalues.set_DETAIL("");
            // Adding appointmentvalues to list
            appointmentvaluesList.add(appointmentvalues);
            return appointmentvaluesList;
        }
        // return appointmentvalues

    }

    //    // String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_APPOINTMENT + "("
//                + KEY_ID + " TEXT," + TITTLE + " TEXT,"
//            + TIME + " TEXT" + ","
//            + DETAIL + " TEXT" + ","
//            + DATE + " TEXT" + ")";
    public List<Database_value> get_Name() {
        final List<Database_value> appointmentvaluesList = new ArrayList<Database_value>();
        try {
            String selectQuery = "SELECT  * FROM " + Name;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (cursor.moveToFirst()) {
                do {
                    Database_value appointmentvalues = new Database_value();

                    appointmentvalues.setName(cursor.getString(0));
                    // Adding appointmentvalues to list
                    appointmentvaluesList.add(appointmentvalues);
                } while (cursor.moveToNext());
            }
            return appointmentvaluesList;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Database_value appointmentvalues = new Database_value();
            appointmentvalues.set_TITTLE("");
            appointmentvalues.set_DATE("");
            appointmentvalues.set_TIME("");
            appointmentvalues.set_DETAIL("");
            // Adding appointmentvalues to list
            appointmentvaluesList.add(appointmentvalues);
            return appointmentvaluesList;
        }
    }

    public List<Database_value> get_All_notes() {
        final List<Database_value> appointmentvaluesList = new ArrayList<Database_value>();
        try {
            String selectQuery = "SELECT  * FROM " + NOTES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (cursor.moveToFirst()) {
                do {
                    Database_value appointmentvalues = new Database_value();

                    //appointmentvalues.setName(cursor.getString(0));
                    appointmentvalues.set_DATE(cursor.getString(1));
                    appointmentvalues.set_TITTLE(cursor.getString(0));
                    appointmentvalues.set_DATE_with_month(cursor.getString(2));
                    // Adding appointmentvalues to list
                    appointmentvaluesList.add(appointmentvalues);
                } while (cursor.moveToNext());
            }
            return appointmentvaluesList;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Database_value appointmentvalues = new Database_value();
            appointmentvalues.set_TITTLE("");
            appointmentvalues.set_DATE("");
            appointmentvalues.set_TIME("");
            appointmentvalues.set_DETAIL("");
            // Adding appointmentvalues to list
            appointmentvaluesList.add(appointmentvalues);
            return appointmentvaluesList;
        }
    }

    public List<Database_value> get_Peoples() {
        final List<Database_value> appointmentvaluesList = new ArrayList<Database_value>();
        try {
            String selectQuery = "SELECT  * FROM " + Name;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (cursor.moveToFirst()) {
                do {
                    Database_value appointmentvalues = new Database_value();

                    appointmentvalues.setName(cursor.getString(0));
                    // Adding appointmentvalues to list
                    appointmentvaluesList.add(appointmentvalues);
                } while (cursor.moveToNext());
            }
            return appointmentvaluesList;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Database_value appointmentvalues = new Database_value();
            appointmentvalues.set_TITTLE("");
            appointmentvalues.set_DATE("");
            appointmentvalues.set_TIME("");
            appointmentvalues.set_DETAIL("");
            // Adding appointmentvalues to list
            appointmentvaluesList.add(appointmentvalues);
            return appointmentvaluesList;
        }
    }

    public List<Database_value> get_Topic_name() {
        final List<Database_value> appointmentvaluesList_topic = new ArrayList<Database_value>();
        try {
            String selectQuery = "SELECT  * FROM " + TOPIC;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

//            Database_value appointmentvalues = new Database_value(cursor.getString(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (cursor.moveToFirst()) {
                do {
                    Database_value appointmentvalues = new Database_value();

                    appointmentvalues.setName(cursor.getString(0));
                    // Adding appointmentvalues to list
                    appointmentvaluesList_topic.add(appointmentvalues);
                } while (cursor.moveToNext());
            }
            return appointmentvaluesList_topic;
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Database_value appointmentvalues = new Database_value();
            appointmentvalues.set_TITTLE("");
            appointmentvalues.set_DATE("");
            appointmentvalues.set_TIME("");
            appointmentvalues.set_DETAIL("");
            // Adding appointmentvalues to list
            appointmentvaluesList_topic.add(appointmentvalues);
            return appointmentvaluesList_topic;
        }
    }
    // return appointmentvalues


    // Getting All Contacts
    public List<Database_value> getAllappointment_list() {
        List<Database_value> appointmentvaluesList = new ArrayList<Database_value>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Database_value appointmentvalues = new Database_value();
                appointmentvalues.setID(cursor.getString(0));
                appointmentvalues.setName(cursor.getString(1));
                appointmentvalues.setPhoneNumber(cursor.getString(2));
                // Adding appointmentvalues to list
                appointmentvaluesList.add(appointmentvalues);
            } while (cursor.moveToNext());
        }

        // return contact list
        return appointmentvaluesList;
    }

    //   String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
    //       + Notes + " TEXT," + Date + " TEXT" + ")";
    // Updating single appointmentvalues
    public int update_Notes(String note, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notes, note);
        // values.put(Date, date);


        // updating row
        return db.update(NOTES, values, Date + " = ?",
                new String[]{String.valueOf(date)});
    }

    // Deleting single appointmentvalues
    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Name, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    // Getting contacts Count
    public int getCount() {
        String countQuery = "SELECT  * FROM " + Name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
