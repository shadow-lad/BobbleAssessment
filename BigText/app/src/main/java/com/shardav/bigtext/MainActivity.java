package com.shardav.bigtext;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shardav.bigtext.adapter.MessageAdapter;
import com.shardav.bigtext.database.BigTextDatabase;
import com.shardav.bigtext.executor.AppExecutors;
import com.shardav.bigtext.model.MessageEntry;
import com.shardav.bigtext.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
		View.OnLongClickListener, ValueAnimator.AnimatorUpdateListener {
	
	// Values for animation
	private static final float START_SIZE = 18.0f;
	private static final float END_SIZE = 52.0f;
	// Animator to animate the size of text
	private final ValueAnimator animator = ValueAnimator.ofFloat(START_SIZE, END_SIZE);
	// Variables for View being displayed
	private EditText messageEditText;
	private ImageButton sendButton;
	// Variables for handling recycler view
	private MessageAdapter adapter;
	private LinearLayoutManager layoutManager;
	// Object to interact with the database
	private BigTextDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Fetching View references
		sendButton = findViewById(R.id.sendButton);
		messageEditText = findViewById(R.id.messageEditText);
		RecyclerView messageListView = findViewById(R.id.messageListView);
		
		// Setting up adapter for listView
		adapter = new MessageAdapter(MainActivity.this);
		
		// Layout Manager for recycler view to stack items from the bottom
		layoutManager = new LinearLayoutManager(this);
		layoutManager.setStackFromEnd(true);
		
		// Setting up RecyclerView
		messageListView.setLayoutManager(layoutManager);
		messageListView.setAdapter(adapter);
		
		// Setting listener for click and long click
		sendButton.setOnClickListener(this);
		sendButton.setOnLongClickListener(this);
		
		// Setting up animator
		animator.setDuration(ViewConfiguration.getLongPressTimeout());
		animator.addUpdateListener(this);
		
		// Initiating database
		database = BigTextDatabase.instance(getApplicationContext());
		
		setupViewModel();
		setupEditText();
		
	}
	
	private void setupEditText() {
		messageEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// DO NOTHING
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// DO NOTHING
			}
			
			// Visual cues for editText
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().isEmpty()) {
					sendButton.setVisibility(View.GONE);
					messageEditText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redAccent, null)));
				} else {
					sendButton.setVisibility(View.VISIBLE);
					messageEditText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent, null)));
				}
			}
		});
		
		// When pressing the send button, the message should be sent as there is no option for long press on IME keyboard
		messageEditText.setOnEditorActionListener((v, actionId, event)->{
			boolean handled = false;
			if (actionId == EditorInfo.IME_ACTION_SEND) {
				onClick(sendButton);
				handled = true;
			}
			return handled;
		});
		
	}
	
	// To handle configuration changes such as rotation
	private void setupViewModel() {
		MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
		viewModel.getMessages().observe(this, (entries)->
				AppExecutors.getInstance().getMainThread().execute(()->{
					adapter.setMessages(entries);
					layoutManager.scrollToPosition(entries.size() - 1);
				}));
	}
	
	/**
	 * Called when send button has been clicked or when long press is released.
	 *
	 * @param v The view that was clicked, in this case it is always the send button.
	 */
	@Override
	public void onClick(View v) {
		String messageText = messageEditText.getText().toString().trim();
		if (!messageText.isEmpty()) {
			animator.cancel();
			float size = messageEditText.getTextSize();
			messageEditText.setText(null);
			messageEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, START_SIZE);
			MessageEntry message = new MessageEntry(messageText, size);
			AppExecutors.getInstance().getDatabase().execute(()->database.messageDAO().insertMessage(message));
		}
	}
	
	/**
	 * Called when the send button has been clicked and held.
	 * It will increase the size of the text in the editText
	 *
	 * @param v The view that was clicked and held, in this case it's always the send button.
	 * @return true if the callback consumed the long click, false otherwise.
	 * <p>
	 * If the method returns false, onClick method is executed.
	 */
	@Override
	public boolean onLongClick(View v) {
		if (!messageEditText.getText().toString().trim().isEmpty()) {
			animator.start();
		}
		return false;
	}
	
	/**
	 * Called whenever there is an update in the animator
	 *
	 * @param animator The animator to which the listener is attached
	 * */
	@Override
	public void onAnimationUpdate(ValueAnimator animator) {
		float currentSize = (float) animator.getAnimatedValue();
		messageEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentSize);
		if (currentSize == END_SIZE) {
			onClick(sendButton);
		}
	}
}