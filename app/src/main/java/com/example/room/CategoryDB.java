package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities={Category.class}, version=2)
public abstract class CategoryDB extends RoomDatabase {
    abstract ManagerCategory manager_category();

    private static final String DB_NAME = "minecategories.db";
    private static volatile CategoryDB INSTANCE = null;

    synchronized static CategoryDB get(Context ctxt) {

        if (INSTANCE == null) {
            INSTANCE = create(ctxt, false);
        }
        return (INSTANCE);
    }

    static CategoryDB create(Context ctxt, boolean memoryOnly) {
        RoomDatabase.Builder<CategoryDB> b;
        if (memoryOnly) {
            b = Room.inMemoryDatabaseBuilder(ctxt.getApplicationContext(),
                    CategoryDB.class);
        } else {
            b = Room.databaseBuilder(ctxt.getApplicationContext(), CategoryDB.class,
                    DB_NAME);
        }
        return (b.createFromAsset("room_db.db").build());

    }
}
