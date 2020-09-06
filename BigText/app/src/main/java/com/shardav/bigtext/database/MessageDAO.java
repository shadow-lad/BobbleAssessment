package com.shardav.bigtext.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.shardav.bigtext.model.MessageEntry;

import java.util.List;

//Database Abstraction Object used to define the SQL operations to be performed

@Dao
public interface MessageDAO {

	@Query("SELECT * FROM message ORDER BY id")
	LiveData<List<MessageEntry>> loadAllMessages();
	
	@Insert
	void insertMessage(MessageEntry message);
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateMessage(MessageEntry message);

}
