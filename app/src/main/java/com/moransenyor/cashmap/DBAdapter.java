package com.moransenyor.cashmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = "DBAdapter"; //used for logging database version changes

	public static final int DATABASE_VERSION = 1; // The version number must be incremented each time a change to DB structure occurs.
	// on version 1: id, price, currency, date, product, vendor, category, receipt, behalf, tags, notes

	// DataBase info:
	public static final String DATABASE_NAME = "dbCashMap";

	// Table names
	public static final String TABLE_RECIEPT = "main_table";
	public static final String TABLE_CURRENCY = "currency_table";
	public static final String TABLE_PRODUCT = "products_table";
	public static final String TABLE_VENDOR = "vendors_table";
	public static final String TABLE_CATEGORY = "category_table";
	public static final String TABLE_TAGS = "tags_table";

	// all tables shared
	public static final String KEY_ROWID = "_id";


	// TABLE_RECEIPT column names
	public static final String KEY_PRICE = "price";
	public static final String KEY_YEAR = "year";
	public static final String KEY_MONTH = "month";

	public static final String KEY_DATE = "date";
	public static final String KEY_PRODUCT = "product";
	public static final String KEY_VENDOR = "vendor";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_RECEIPT = "receipt";
	public static final String KEY_BEHALF = "behalf";
	public static final String KEY_TAGS = "tags";
	public static final String KEY_NOTES = "notes";


	public static final String KEY_CURRENCY = "currency";

	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_PRICE, KEY_CURRENCY,
			KEY_DATE, KEY_PRODUCT, KEY_VENDOR, KEY_CATEGORY, KEY_RECEIPT, KEY_BEHALF, KEY_TAGS,
			KEY_NOTES};
	
	// Column Numbers for each Field Name:
	public static final int COL_ROWID = 0;
	public static final int COL_PRICE = 1;
	public static final int COL_CURRENCY = 2;
	public static final int COL_DATE = 3;
	public static final int COL_PRODUCT = 4;
	public static final int COL_VENDOR = 5;
	public static final int COL_CATEGORY = 6;
	public static final int COL_RECEIPT = 7;
	public static final int COL_BEHALF = 8;
	public static final int COL_TAGS = 9;
	public static final int COL_NOTES = 10;




		
	//SQL statement to create database
	private static final String DATABASE_CREATE_SQL =
			"CREATE TABLE " + TABLE_RECIEPT +
					" (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_PRICE + " TEXT, "
					+ KEY_CURRENCY + " TEXT, "
					+ KEY_DATE + " TEXT, "
					+ KEY_PRODUCT + " TEXT, "
					+ KEY_VENDOR + " TEXT, "
					+ KEY_CATEGORY + " TEXT, "
					+ KEY_RECEIPT + " TEXT NOT NULL, "
					+ KEY_BEHALF + " TEXT, "
					+ KEY_TAGS + " TEXT, "
					+ KEY_NOTES + " TEXT"
					+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;


	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to be inserted into the database.
	public long insertRow(String price,
						  String currency,
						  String date,
						  String product,
						  String vendor,
						  String category,
						  String receipt,
						  String behalf,
						  String tags,
						  String notes) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PRICE, price);
		initialValues.put(KEY_CURRENCY, currency);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_PRODUCT, product);
		initialValues.put(KEY_VENDOR, vendor);
		initialValues.put(KEY_CATEGORY, category);
		initialValues.put(KEY_RECEIPT, receipt);
		initialValues.put(KEY_BEHALF, behalf);
		initialValues.put(KEY_TAGS, tags);
		initialValues.put(KEY_NOTES, notes);
				
		// Insert the data into the database.
		return db.insert(TABLE_RECIEPT, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(TABLE_RECIEPT, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, TABLE_RECIEPT, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, TABLE_RECIEPT, ALL_KEYS,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId,
							 String price,
							 String currency,
							 String date,
							 String product,
							 String vendor,
							 String category,
							 String receipt,
							 String behalf,
							 String tags,
							 String notes) {

		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_PRICE, price);
		newValues.put(KEY_CURRENCY, currency);
		newValues.put(KEY_DATE, date);
		newValues.put(KEY_PRODUCT, product);
		newValues.put(KEY_VENDOR, vendor);
		newValues.put(KEY_CATEGORY, category);
		newValues.put(KEY_RECEIPT, receipt);
		newValues.put(KEY_BEHALF, behalf);
		newValues.put(KEY_TAGS, tags);
		newValues.put(KEY_NOTES, notes);

		// Insert it into the database.
		return db.update(TABLE_RECIEPT, newValues, where, null) != 0;
	}

	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIEPT);
			
			// Recreate new database:
			onCreate(_db);
		}
	}


}
/*
DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

        // create new tables
        onCreate(db);
    }
 */
