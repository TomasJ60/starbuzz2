package co.edu.unipiloto.starbuzz2;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;



    public StarbuzzDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public StarbuzzDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public StarbuzzDatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabse(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS DRINK");
            onCreate(db);
        }
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        super.onDowngrade(db,oldVersion,newVersion);
        updateMyDatabse(db,oldVersion,newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    public void updateMyDatabse(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(db,"Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
            insertDrink(db, "Bumm", "mas lico :)", R.drawable.filter);
        }
        if (oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
    }
}
