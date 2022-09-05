package com.myneuro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class DBHelper_MyNeuro extends SQLiteOpenHelper {

    //columns for user table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_USER_ID = "_id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_DOB = "dob";
    public static final String COLUMN_USER_CHI = "chi";
    public static final String COLUMN_USER_GP = "gp";

    //columns for seizure table
    public static final String TABLE_SEIZURE = "seizure";
    public static final String COLUMN_SEIZURE_ID = "_id";
    public static final String COLUMN_SEIZURE_DATE = "date";
    public static final String COLUMN_SEIZURE_TIME = "time";
    public static final String COLUMN_SEIZURE_LOCATION = "location";
    public static final String COLUMN_SEIZURE_TYPE = "type";
    public static final String COLUMN_SEIZURE_DURATION = "duration";
    public static final String COLUMN_SEIZURE_AWARENESS = "awareness";
    public static final String COLUMN_SEIZURE_FACIAL_EXPRESSION = "facial_exp";
    public static final String COLUMN_SEIZURE_HEAD_MOVEMENT = "head_mov";
    public static final String COLUMN_SEIZURE_JERKING_MOVEMENT = "jerking_mov";
    public static final String COLUMN_SEIZURE_FALL = "fall";
    public static final String COLUMN_SEIZURE_AFTER_EFFECT = "after_effect";
    public static final String COLUMN_SEIZURE_INJURY = "injury";
    public static final String COLUMN_SEIZURE_USER_USER_ID = "user_id";

    //columns for medication table
    public static final String TABLE_MEDICATION = "medication";
    public static final String COLUMN_MEDICATION_ID = "_id";
    public static final String COLUMN_MEDICATION_NAME = "med_name";
    public static final String COLUMN_MEDICATION_FREQUENCY = "med_freq";
    public static final String COLUMN_MEDICATION_DOSAGE = "med_dose";
    public static final String COLUMN_MEDICATION_DURATION = "med_duration";
    public static final String COLUMN_MEDICATION_START_DATE = "med_start_date";
    public static final String COLUMN_MEDICATION_DISCONTINUED = "med_discontinued";
    public static final String COLUMN_MEDICATION_USER_USER_ID = "_user_id";


    //columns for user table
    public static final String TABLE_REMINDER= "Reminder";
    public static final String COLUMN_REM_ID = "_id";
    public static final String COLUMN_REM_TITLE= "title";
    public static final String COLUMN_REM_DETAIL = "detail";
    public static final String COLUMN_REM_TIME = "time";
    public static final String COLUMN_REM_REPEAT= "repeat";
    public static final String COLUMN_REM_INTERVAL = "interval";
    public static final String COLUMN_REM_TYPE = "type";

    private static final String DATABASE_NAME = "myNeuro.db";
    private static final int DATABASE_VERSION = 3;
    Context context;

    //table creation - Users
    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_USERNAME + " TEXT NOT NULL,"
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_DOB + " TEXT,"
            + COLUMN_USER_CHI + " TEXT,"
            + COLUMN_USER_GP + " TEXT"
            + ");";

    //table creation - SEIZURE
    private static final String SQL_CREATE_TABLE_SEIZURE = "CREATE TABLE " + TABLE_SEIZURE + "("
            + COLUMN_SEIZURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SEIZURE_DATE + " TEXT,"
            + COLUMN_SEIZURE_TIME + " TEXT,"
            + COLUMN_SEIZURE_LOCATION + " TEXT,"
            + COLUMN_SEIZURE_TYPE + " TEXT,"
            + COLUMN_SEIZURE_DURATION + " TEXT,"
            + COLUMN_SEIZURE_AWARENESS + " TEXT,"
            + COLUMN_SEIZURE_FACIAL_EXPRESSION + " TEXT,"
            + COLUMN_SEIZURE_HEAD_MOVEMENT + " TEXT,"
            + COLUMN_SEIZURE_JERKING_MOVEMENT + " TEXT,"
            + COLUMN_SEIZURE_FALL + " TEXT,"
            + COLUMN_SEIZURE_AFTER_EFFECT + " TEXT,"
            + COLUMN_SEIZURE_INJURY + " TEXT,"
            + COLUMN_SEIZURE_USER_USER_ID + " INTEGER," + "FOREIGN KEY (" + COLUMN_SEIZURE_USER_USER_ID + ") REFERENCES " + TABLE_USERS + " (" + COLUMN_USER_USER_ID + "))";

    //table creation - MEDICATION
    private static final String SQL_CREATE_TABLE_MEDICATION = "CREATE TABLE " + TABLE_MEDICATION + "("
            + COLUMN_MEDICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MEDICATION_NAME + " TEXT, "
            + COLUMN_MEDICATION_FREQUENCY + " TEXT, "
            + COLUMN_MEDICATION_DOSAGE + " TEXT, "
            + COLUMN_MEDICATION_DURATION + " TEXT, "
            + COLUMN_MEDICATION_START_DATE + " TEXT, "
            + COLUMN_MEDICATION_DISCONTINUED + " TEXT, "
            + COLUMN_MEDICATION_USER_USER_ID + " INTEGER," + "FOREIGN KEY (" + COLUMN_MEDICATION_USER_USER_ID + ") REFERENCES " + TABLE_USERS + " (" + COLUMN_USER_USER_ID + "))";

    //table creation - REMINDER
    private static final String SQL_CREATE_TABLE_REMINDER = "CREATE TABLE " + TABLE_REMINDER + "("
            + COLUMN_REM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_REM_TITLE + " TEXT NOT NULL,"
            + COLUMN_REM_DETAIL + " TEXT NOT NULL,"
            + COLUMN_REM_TIME + " TEXT NOT NULL,"
            + COLUMN_REM_REPEAT + " TEXT,"
            + COLUMN_REM_INTERVAL + " TEXT,"
            + COLUMN_REM_TYPE + " TEXT"
            + ");";
    public DBHelper_MyNeuro(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_SEIZURE);
        db.execSQL(SQL_CREATE_TABLE_MEDICATION);
        db.execSQL(SQL_CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEIZURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

        onCreate(db);
    }

    //Login/Register SQL statements
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USERNAME, username);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_PASSWORD, password);
        long result = MyDB.insert(com.myneuro.DBHelper_MyNeuro.TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public Boolean updatepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_PASSWORD, password);
        long result = MyDB.update(com.myneuro.DBHelper_MyNeuro.TABLE_USERS, contentValues, "username = ?", new String[]{username});
        return result != -1;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    //profile page SQL statements
    public Boolean updateData(String username, String name, String DOB, String chi, String gp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_NAME, name);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_DOB, DOB);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_CHI, chi);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_GP, gp);
        long result = MyDB.update(com.myneuro.DBHelper_MyNeuro.TABLE_USERS, contentValues, "username = ?", new String[]{username});
        return result != -1;
    }

    public Cursor ViewData() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from " + TABLE_USERS, null);
        return cursor;
    }

    public int updateProfile(long id, String username, String name, String dob, String chi, String gp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USERNAME, username);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_NAME, name);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_DOB, dob);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_CHI, chi);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_USER_GP, gp);
        String whereArgs[] = {"" + id};
        int count = MyDB.update(com.myneuro.DBHelper_MyNeuro.TABLE_USERS, contentValues, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USER_ID + "=?", whereArgs);
        return count;
    }

    public SimpleCursorAdapter profileListViewFromDB() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String columns[] = {com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USER_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USERNAME, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_NAME, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_DOB,
                com.myneuro.DBHelper_MyNeuro.COLUMN_USER_CHI, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_GP};
        Cursor cursor = MyDB.query(com.myneuro.DBHelper_MyNeuro.TABLE_USERS, columns, null,
                null,
                null,
                null,
                null);
        String[] fromFieldNames = new String[]{com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USER_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_USERNAME, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_NAME, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_DOB,
                com.myneuro.DBHelper_MyNeuro.COLUMN_USER_CHI, com.myneuro.DBHelper_MyNeuro.COLUMN_USER_GP
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_username, R.id.item_name, R.id.item_dob1, R.id.item_chi1, R.id.item_gp1};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.viewprofile_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public Integer deleteData() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete("user", null, null);
    }

    //Homepage SQL statements
    public Cursor lastSeizure() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM seizure where date=(SELECT max(date) FROM seizure)", null);
        return cursor;
    }

    //seizure page SQL statements
    public Boolean addSeizure(String date, String time, String location, String type, String duration,
                              String awareness, String facial_exp, String head_mov,
                              String jerking, String fall,
                              String after_effect, String injury) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DATE, date);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TIME, time);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_LOCATION, location);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TYPE, type);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DURATION, duration);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AWARENESS, awareness);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FACIAL_EXPRESSION, facial_exp);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_HEAD_MOVEMENT, head_mov);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_JERKING_MOVEMENT, jerking);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FALL, fall);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AFTER_EFFECT, after_effect);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_INJURY, injury);
        long result = MyDB.insert(com.myneuro.DBHelper_MyNeuro.TABLE_SEIZURE, null, contentValues);
        return result != -1;
    }

    public SimpleCursorAdapter populateSeizureFromDB() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String columns[] = {com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DATE, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TIME, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_LOCATION,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TYPE, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DURATION, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AWARENESS, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FACIAL_EXPRESSION,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_HEAD_MOVEMENT, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_JERKING_MOVEMENT, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FALL, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AFTER_EFFECT,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_INJURY};
        Cursor cursor = MyDB.query(com.myneuro.DBHelper_MyNeuro.TABLE_SEIZURE, columns, null,
                null,
                null,
                null,
                null);
        String[] fromFieldNames = new String[]{
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DATE, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TIME, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_LOCATION, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_TYPE,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_DURATION, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AWARENESS, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FACIAL_EXPRESSION,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_HEAD_MOVEMENT, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_JERKING_MOVEMENT, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_FALL, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_AFTER_EFFECT,
                com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_INJURY};

        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_date, R.id.item_time, R.id.item_location, R.id.item_type};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.seizure_view,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public int deleteSeizure(long id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String whereArgs[] = {"" + id};
        int count = MyDB.delete(com.myneuro.DBHelper_MyNeuro.TABLE_SEIZURE, com.myneuro.DBHelper_MyNeuro.COLUMN_SEIZURE_ID + "=?", whereArgs);
        return count;
    }

    //Medication page SQL statements
    public Boolean addMedication(String name, String frequency, String dosage, String duration, String startdate) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_NAME, name);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_FREQUENCY, frequency);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DOSAGE, dosage);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DURATION, duration);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_START_DATE, startdate);
        long result = MyDB.insert(com.myneuro.DBHelper_MyNeuro.TABLE_MEDICATION, null, contentValues);
        return result != -1;
    }

    public SimpleCursorAdapter populateListViewFromDB() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String columns[] = {com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_NAME, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DOSAGE, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_FREQUENCY,
                com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DURATION, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_START_DATE};
        Cursor cursor = MyDB.query(com.myneuro.DBHelper_MyNeuro.TABLE_MEDICATION, columns, null,
                null,
                null,
                null,
                null);
        String[] fromFieldNames = new String[]{
                com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_NAME, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DOSAGE, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_FREQUENCY, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DURATION, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_START_DATE
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_med, R.id.item_dose, R.id.item_freq, R.id.item_dur, R.id.item_startDate};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public int updateMedNew(long id, String name, String dosage, String frequency, String duration, String date, String discontinue) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_NAME, name);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DOSAGE, dosage);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_FREQUENCY, frequency);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DURATION, duration);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_START_DATE, date);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DISCONTINUED, discontinue);
        String whereArgs[] = {"" + id};
        int count = MyDB.update(com.myneuro.DBHelper_MyNeuro.TABLE_MEDICATION, contentValues, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_ID + "=?", whereArgs);
        return count;
    }

    public SimpleCursorAdapter populateCurrentMed() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM medication WHERE med_discontinued!='Yes'", null);

        String[] fromFieldNames = new String[]{
                com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_NAME, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DOSAGE, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_FREQUENCY,
                com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_DURATION, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_START_DATE
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_med, R.id.item_dose, R.id.item_freq, R.id.item_dur, R.id.item_startDate};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public int deleteMedication(long id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String whereArgs[] = {"" + id};
        int count = MyDB.delete(com.myneuro.DBHelper_MyNeuro.TABLE_MEDICATION, com.myneuro.DBHelper_MyNeuro.COLUMN_MEDICATION_ID + "=?", whereArgs);
        return count;
    }

    //Reminder Page SQL statements
    public Boolean addReminder(String title, String detail, String time, String repeat, String interval, String type) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TITLE, title);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_DETAIL, detail);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TIME, time);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_REPEAT, repeat);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_INTERVAL, interval);
        contentValues.put(com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TYPE, type);
        long result = MyDB.insert(com.myneuro.DBHelper_MyNeuro.TABLE_REMINDER, null, contentValues);
        return result != -1;
    }

    public SimpleCursorAdapter populateReminderFromDB() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String columns[] = {com.myneuro.DBHelper_MyNeuro.COLUMN_REM_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TITLE, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_DETAIL, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TIME,
                com.myneuro.DBHelper_MyNeuro.COLUMN_REM_REPEAT, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_INTERVAL, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TYPE};
        Cursor cursor = MyDB.query(com.myneuro.DBHelper_MyNeuro.TABLE_REMINDER, columns, null,
                null,
                null,
                null,
                null);
        String[] fromFieldNames = new String[]{
                com.myneuro.DBHelper_MyNeuro.COLUMN_REM_ID, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TITLE, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_DETAIL, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TIME,
                com.myneuro.DBHelper_MyNeuro.COLUMN_REM_REPEAT, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_INTERVAL, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_TYPE};
        int[] toViewIDs = new int[]{R.id.id, R.id.title, R.id.time, R.id.repeat, R.id.interval, R.id.type};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.activity_rem_layout,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public int deleteReminder(long id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String whereArgs[] = {"" + id};
        int count = MyDB.delete(com.myneuro.DBHelper_MyNeuro.TABLE_REMINDER, com.myneuro.DBHelper_MyNeuro.COLUMN_REM_ID + "=?", whereArgs);
        return count;
    }


}
