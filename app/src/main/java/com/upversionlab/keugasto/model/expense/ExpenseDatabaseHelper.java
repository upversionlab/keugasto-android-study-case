package com.upversionlab.keugasto.model.expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.upversionlab.keugasto.model.category.Category;
import com.upversionlab.keugasto.model.category.CategoryDatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vruzeda on 4/8/16.
 */
public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Expense.db";

    private static abstract class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "Expense";
        public static final String CATEGORY_ID_COLUMN_NAME = "category_id";
        public static final String VALUE_COLUMN_NAME = "value";
        public static final String DATE_IN_MILLIS_COLUMN_NAME = "date_in_millis";
        public static final String USER_DESCRIPTION_COLUMN_NAME = "user_description";
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + ExpenseEntry.TABLE_NAME + "(" +
                ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                ExpenseEntry.CATEGORY_ID_COLUMN_NAME + " INTEGER NOT NULL," +
                ExpenseEntry.VALUE_COLUMN_NAME + " REAL," +
                ExpenseEntry.DATE_IN_MILLIS_COLUMN_NAME + " INTEGER," +
                ExpenseEntry.USER_DESCRIPTION_COLUMN_NAME + " TEXT," +
                "FOREIGN KEY (" + ExpenseEntry.CATEGORY_ID_COLUMN_NAME + ") REFERENCES "+ CategoryDatabaseHelper.CategoryEntry.TABLE_NAME + "("+ CategoryDatabaseHelper.CategoryEntry._ID + ")" +
            ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;

    private Context context;

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public Expense addExpense(Category category, float value, Calendar date, String userDescription) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseEntry.CATEGORY_ID_COLUMN_NAME, category.id);
        values.put(ExpenseEntry.VALUE_COLUMN_NAME, value);
        values.put(ExpenseEntry.DATE_IN_MILLIS_COLUMN_NAME, date.getTimeInMillis());
        values.put(ExpenseEntry.USER_DESCRIPTION_COLUMN_NAME, userDescription);

        long id = database.insert(
                ExpenseEntry.TABLE_NAME,
                null,
                values
        );

        Expense expense = new Expense();
        expense.id = id;
        expense.category = category;
        expense.value = value;
        expense.date = date;
        expense.userDescription = userDescription;
        return expense;
    }

    public List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                ExpenseEntry._ID,
                ExpenseEntry.CATEGORY_ID_COLUMN_NAME,
                ExpenseEntry.VALUE_COLUMN_NAME,
                ExpenseEntry.DATE_IN_MILLIS_COLUMN_NAME,
                ExpenseEntry.USER_DESCRIPTION_COLUMN_NAME,
        };

        Cursor cursor = database.query(
                ExpenseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry._ID));
            long categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.CATEGORY_ID_COLUMN_NAME));
            float value = cursor.getFloat(cursor.getColumnIndexOrThrow(ExpenseEntry.VALUE_COLUMN_NAME));
            long dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.DATE_IN_MILLIS_COLUMN_NAME));
            String userDescription = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.USER_DESCRIPTION_COLUMN_NAME));

            CategoryDatabaseHelper categoryDatabaseHelper = new CategoryDatabaseHelper(context);
            Category category = categoryDatabaseHelper.getCategoryById(categoryId);

            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(dateInMillis);

            Expense expense = new Expense();
            expense.id = id;
            expense.category = category;
            expense.value = value;
            expense.date = date;
            expense.userDescription = userDescription;
            expenses.add(expense);
        }

        return expenses;
    }

}
