package example.com.englishnote.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KimYebon on 16. 7. 28..
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if(sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        this(context, DBLiteral.DATABASE_NAME, null, DBLiteral.DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String dbName, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, dbName, cursorFactory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBLiteral.CREATE_VOCABULARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getCount(String tableName) {
        SQLiteDatabase db = getReadableDatabase();

        long cnt = DatabaseUtils.queryNumEntries(db, tableName);

        return (int)cnt;
    }
}
