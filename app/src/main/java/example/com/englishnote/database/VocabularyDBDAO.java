package example.com.englishnote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import example.com.englishnote.model.Vocabulary;

/**
 * Created by KimYebon on 16. 6. 16..
 */
public class VocabularyDBDAO {

    private final static String TABLE_NAME = DBLiteral.VOCABULARY_TABLE;

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;

    private Context mContext;

    private String[] mColumnList;

    public VocabularyDBDAO(Context context) {
        this.mContext = context;
        mDbHelper = DatabaseHelper.getHelper(context);
        mColumnList = new String[]{DBLiteral.ID_COLUMN,
                DBLiteral.ENGLISH_COLUMN,
                DBLiteral.MEANS_COLUMN,
                DBLiteral.TIMESTAMP_COLUMN
        };
        open();
    }

    public void open() throws SQLException {
        if (mDbHelper == null) {
            mDbHelper = DatabaseHelper.getHelper(mContext);
        }
        mDatabase = mDbHelper.getWritableDatabase();
    }

    @NonNull
    protected ContentValues makeContentValues(@NonNull Vocabulary voca) {
        ContentValues values = new ContentValues();

        values.put(DBLiteral.ENGLISH_COLUMN, voca.getEnglish());
        values.put(DBLiteral.MEANS_COLUMN, voca.getMeans());
        values.put(DBLiteral.TIMESTAMP_COLUMN, voca.getTimestamp());

        return values;
    }

    public long insert(@NonNull Vocabulary voca) {
        ContentValues values = makeContentValues(voca);

        return mDatabase.insert(TABLE_NAME, null, values);
    }

    public long update(@NonNull Vocabulary voca) {
        ContentValues values = makeContentValues(voca);
        long result = 0;

        try {
            result = mDatabase.update(TABLE_NAME, values, DBLiteral.WHERE_ID_EQUALS,
                    new String[]{String.valueOf(voca.getId())});
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }

        return result;
    }

    @NonNull
    public List<Vocabulary> selectAll() {
        List<Vocabulary> datas = new ArrayList<Vocabulary>();
        Cursor cursor = mDatabase.query(TABLE_NAME, mColumnList, null, null,
                null, null,
                DBLiteral.TIMESTAMP_COLUMN + DBLiteral.ORDER_BY_DESC);

        if (cursor == null) {
            return datas;
        }

        if (cursor.moveToFirst() == false) {
            return datas;
        }

        do {
            datas.add(cursorToData(cursor));
        } while (cursor.moveToNext());

        cursor.close();

        return datas;
    }

    @Nullable
    public Vocabulary selectById(int id) {
        Cursor cursor = mDatabase.query(TABLE_NAME, mColumnList,
                DBLiteral.WHERE_ID_EQUALS, new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst() == false) {
            return null;
        }

        return cursorToData(cursor);
    }

    @NonNull
    public Vocabulary cursorToData(@NonNull Cursor cursor) {
        if (cursor.getCount() == 0)
            return null;

        Vocabulary voca = new Vocabulary();

        voca.setId(cursor.getInt(0));
        voca.setEnglish(cursor.getString(1));
        voca.setMeans(cursor.getString(2));
        voca.setTimestamp(cursor.getInt(3));

        return voca;
    }

    public int getCount() {
        return mDbHelper.getCount(TABLE_NAME);
    }
}
