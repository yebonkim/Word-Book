package example.com.englishnote.database;

/**
 * Created by KimYebon on 18. 3. 30..
 */
public class DBLiteral {
    public static final String DATABASE_NAME = "note";
    public static final int DATABASE_VERSION = 1;

    public static final String VOCABULARY_TABLE = "vocabulary";

    public static final String ID_COLUMN = "id";
    public static final String ENGLISH_COLUMN = "name";
    public static final String MEANS_COLUMN = "image_name";
    public static final String TIMESTAMP_COLUMN = "timestamp";

    public static final String WHERE_ID_EQUALS = ID_COLUMN + " =?";

    public static final String ORDER_BY_DESC = " DESC";

    public static final String CREATE_VOCABULARY_TABLE = "CREATE TABLE IF NOT EXISTS "+
            VOCABULARY_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY," +
            ENGLISH_COLUMN + " TEXT UNIQUE, " +
            MEANS_COLUMN + " TEXT, " +
            TIMESTAMP_COLUMN + " INTEGER" +
            ");";
}
