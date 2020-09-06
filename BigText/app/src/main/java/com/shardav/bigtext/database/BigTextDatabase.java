package com.shardav.bigtext.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shardav.bigtext.model.MessageEntry;


// To access database functionality provided by Android Room Architecture using Singleton Pattern
@Database(entities = {MessageEntry.class}, version = 1, exportSchema = false)
public abstract class BigTextDatabase extends RoomDatabase {

	private static final Object LOCK = new Object();
	private static final String DATABASE_NAME = "messages";
	private static BigTextDatabase instance;
	
	public static BigTextDatabase instance(Context context) {
		if (instance == null ){
			synchronized (LOCK) {
				instance = Room.databaseBuilder(context.getApplicationContext(),
						BigTextDatabase.class,
						DATABASE_NAME)
						.build();
			}
		}
		return instance;
	}

	public abstract MessageDAO messageDAO();
	
}
