package com.shardav.bigtext.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// POJO for Android ROOM architecture for handling entries in database
@Entity(tableName = "message")
public class MessageEntry {
	
	@PrimaryKey(autoGenerate = true)
	private int id;
	private String message;
	@ColumnInfo(name = "size")
	private float fontSize;
	
	@Ignore
	public MessageEntry(String message, float fontSize) {
		this.message = message;
		this.fontSize = fontSize;
	}
	
	public MessageEntry(int id, String message, float fontSize) {
		this.id = id;
		this.message = message;
		this.fontSize = fontSize;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public float getFontSize() {
		return fontSize;
	}
}
