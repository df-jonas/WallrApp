package be.defrere.wallr.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import be.defrere.wallr.dao.EventDao;
import be.defrere.wallr.dao.TextDao;
import be.defrere.wallr.dao.UserDao;
import be.defrere.wallr.entity.Event;
import be.defrere.wallr.entity.Text;
import be.defrere.wallr.entity.User;

@Database(entities = {User.class, Event.class, Text.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract EventDao eventDao();

    public abstract TextDao textDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
