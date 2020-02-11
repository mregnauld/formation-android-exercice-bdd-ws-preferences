package com.formationandroid.bddwspreferences;

import android.content.Context;

import androidx.room.Room;

class AppDatabaseHelper
{
	
	// Instance :
	private static AppDatabaseHelper databaseHelper = null;
	
	// Database :
	private AppDatabase database;
	
	
	/**
	 * Constructeur.
	 * @param context Context
	 */
	private AppDatabaseHelper(Context context)
	{
		database = Room
				.databaseBuilder(context, AppDatabase.class, "memos.db")
				.allowMainThreadQueries()
				.build();
	}
	
	/**
	 * Getter instance.
	 * @param context Context
	 * @return AppDatabase
	 */
	static synchronized AppDatabase getDatabase(Context context)
	{
		if (databaseHelper == null)
		{
			databaseHelper = new AppDatabaseHelper(context.getApplicationContext());
		}
		return databaseHelper.database;
	}
	
}
