package com.shardav.bigtext.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shardav.bigtext.R;
import com.shardav.bigtext.model.MessageEntry;

import java.util.List;

// Adapter for RecyclerView to define the how to map objects to views
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
	
	private Context context;
	private List<MessageEntry> messages;
	
	public MessageAdapter(Context context) {
		this.context = context;
	}
	
	@NonNull
	@Override
	public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
		
		return new MessageViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
		MessageEntry message = messages.get(position);
		String text = message.getMessage();
		float size = message.getFontSize();
		
		holder.message.setText(text);
		holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	}
	
	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	@Override
	public int getItemCount() {
		if (messages == null) {
			return 0;
		}
		return messages.size();
	}
	
	public void setMessages(List<MessageEntry> messages) {
		this.messages = messages;
		notifyDataSetChanged();
	}
	
	static class MessageViewHolder extends RecyclerView.ViewHolder {
		
		TextView message;
		
		public MessageViewHolder(@NonNull View itemView) {
			super(itemView);
			
			message = itemView.findViewById(R.id.messageTextView);
			
		}
	}

}
