package com.upversionlab.keugasto.model.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vruzeda on 4/9/16.
 */
public class CategoryDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Category.db";

    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "Category";
        public static final String NAME_COLUMN_NAME = "name";
        public static final String LIMIT_COLUMN_NAME = "value";
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + "(" +
                CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                CategoryEntry.NAME_COLUMN_NAME + " TEXT," +
                CategoryEntry.LIMIT_COLUMN_NAME + " REAL" +
            ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;

    public CategoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public Category addCategory(String name, float limit) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.NAME_COLUMN_NAME, name);
        values.put(CategoryEntry.LIMIT_COLUMN_NAME, limit);

        long id = database.insert(
                CategoryEntry.TABLE_NAME,
                null,
                values
        );

        Category category = new Category();
        category.id = id;
        category.name = name;
        category.limit = limit;
        return category;
    }

    public Category getCategoryById(long categoryId) {
        String selection = CategoryEntry._ID + "= ?";
        String[] selectionArgs = {
                Long.toString(categoryId)
        };
        List<Category> categories = getCategories(selection, selectionArgs);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public List<Category> getCategories() {
        return getCategories(null, null);
    }

    private List<Category> getCategories(String selection, String[] selectionArgs) {
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                CategoryEntry._ID,
                CategoryEntry.NAME_COLUMN_NAME,
                CategoryEntry.LIMIT_COLUMN_NAME,
        };

        Cursor cursor = database.query(
                CategoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Category category = new Category();
            category.id = cursor.getLong(cursor.getColumnIndexOrThrow(CategoryEntry._ID));
            category.name = cursor.getString(cursor.getColumnIndexOrThrow(CategoryEntry.NAME_COLUMN_NAME));
            category.limit = cursor.getFloat(cursor.getColumnIndexOrThrow(CategoryEntry.LIMIT_COLUMN_NAME));
            categories.add(category);
        }

        return categories;
    }
}
