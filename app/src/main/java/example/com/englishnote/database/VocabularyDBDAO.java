package example.com.englishnote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import example.com.englishnote.database.DBLiteral;
import example.com.englishnote.database.DatabaseHelper;
import example.com.englishnote.model.Vocabulary;

import static example.com.englishnote.database.DBLiteral.WHERE_ID_EQUALS;

/**
 * Created by KimYebon on 16. 6. 16..
 */
public class VocabularyDBDAO {
    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String tableName;
    private String[] columnList;

    public VocabularyDBDAO(Context context) {
        this.mContext = context;
        dbHelper = DatabaseHelper.getHelper(context);
        tableName = DBLiteral.VOCABULARY_TABLE;
        columnList = new String[] { DBLiteral.ID_COLUMN,
                DBLiteral.ENGLISH_COLUMN,
                DBLiteral.MEANS_COLUMN,
                DBLiteral.TIMESTAMP_COLUMN
        };
        open();
    }

    public void open() throws SQLException {
        if(dbHelper == null) {
            dbHelper = DatabaseHelper.getHelper(mContext);
        }
        database = dbHelper.getWritableDatabase();
    }

    protected ContentValues makeContentValues(Vocabulary voca) {
        ContentValues values = new ContentValues();

        values.put(DBLiteral.ENGLISH_COLUMN, voca.getEnglish());
        values.put(DBLiteral.MEANS_COLUMN, voca.getMeans());
        values.put(DBLiteral.TIMESTAMP_COLUMN, voca.getTimestamp());

        return values;
    }

    public long insert(Vocabulary voca) {
        ContentValues values = makeContentValues(voca);

        return database.insert(tableName, null, values);
    }

    public long update(Vocabulary voca) {
        ContentValues values = makeContentValues(voca);
        long result = 0;

        try {
            result = database.update(tableName, values,
                    WHERE_ID_EQUALS, new String[] { String.valueOf(voca.getId()) });
        }catch(SQLiteConstraintException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Vocabulary> selectAll() {
        List<Vocabulary> datas = new ArrayList<Vocabulary>();
        Cursor cursor = database.query(tableName, columnList, null, null, null, null, null);

        if(cursor==null || cursor.getCount()==0)
            return datas;

        cursor.moveToFirst();

        do{
            datas.add(cursorToData(cursor));
        }while(cursor.moveToNext());

        cursor.close();

        return datas;
    }

    public Vocabulary cursorToData(Cursor cursor) {

        if(cursor.getCount()==0)
            return null;

        Vocabulary voca = new Vocabulary();
        voca.setId(cursor.getInt(0));
        voca.setEnglish(cursor.getString(1));
        voca.setMeans(cursor.getString(2));
        voca.setTimestamp(cursor.getInt(3));

        return voca;
    }

    public int getCount() {
        return dbHelper.getCount(tableName);
    }
}
