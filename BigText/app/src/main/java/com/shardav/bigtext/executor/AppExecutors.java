package com.shardav.bigtext.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// To schedule database and UI updates while avoiding race condition.
public class AppExecutors {
	
	private final Executor database;
	private final Executor mainThread;
	private static final Object LOCK = new Object();
	private static AppExecutors instance;
	
	private AppExecutors(Executor database, Executor mainThread) {
		this.database = database;
		this.mainThread = mainThread;
	}
	
	public static AppExecutors getInstance() {
		if (instance == null ) {
			synchronized (LOCK) {
				instance = new AppExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
			}
		}
		return instance;
	}
	
	public Executor getDatabase() {
		return database;
	}
	
	public Executor getMainThread() {
		return mainThread;
	}
	
	private static class MainThreadExecutor implements Executor {
		private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
		
		/**
		 * Executes the given command at some time in the future in the main thread
		 *
		 * @param command the runnable task
		 */
		@Override
		public void execute(@NonNull Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}
