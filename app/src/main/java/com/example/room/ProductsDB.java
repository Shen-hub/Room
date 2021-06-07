package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities={Product.class}, version=2)
public abstract class ProductsDB extends RoomDatabase {
    abstract Manager manager();

    private static final String DB_NAME = "room_db.db";
    private static volatile ProductsDB INSTANCE = null;

    synchronized static ProductsDB get(Context ctxt) {

        if (INSTANCE == null) {
            INSTANCE = create(ctxt, false);
        }
        return (INSTANCE);
    }

    static ProductsDB create(Context ctxt, boolean memoryOnly) {
        RoomDatabase.Builder<ProductsDB> b;
        if (memoryOnly) {
            b = Room.inMemoryDatabaseBuilder(ctxt.getApplicationContext(),
                    ProductsDB.class);
        } else {
            b = Room.databaseBuilder(ctxt.getApplicationContext(), ProductsDB.class,
                    DB_NAME);
        }
        return (b.createFromAsset(DB_NAME).build());

    }
}

