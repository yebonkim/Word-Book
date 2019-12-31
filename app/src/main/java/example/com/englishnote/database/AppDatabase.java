package example.com.englishnote.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import example.com.englishnote.model.Vocabulary;

@Database(entities = {Vocabulary.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public final static String DATABASE_NAME = "Voca.db";

    public abstract VocabularyDAO vocaDao();
}
