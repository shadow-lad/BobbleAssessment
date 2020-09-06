package com.shardav.bigtext.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shardav.bigtext.database.BigTextDatabase;
import com.shardav.bigtext.model.MessageEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
	
	private LiveData<List<MessageEntry>> messages;
	
	public MainViewModel(@NonNull Application application) {
		super(application);
		BigTextDatabase database = BigTextDatabase.instance(this.getApplication());
		messages = database.messageDAO().loadAllMessages();
	}
	
	public LiveData<List<MessageEntry>> getMessages() {
		return messages;
	}
	
}
