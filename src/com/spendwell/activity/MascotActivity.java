package com.spendwell.activity;

import java.util.Random;

import com.example.budgetwell.R;
import com.spendwell.entity.BankAccount;
import com.spendwell.service.SharedService;
import com.spendwell.utils.ReminderSQLServiceImpl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity that show the mascot Image and hint of remidner setting
 * 
 * @author Yifei Gao
 * 
 */
public class MascotActivity extends Activity {
	private ImageView meme_img;
	private TextView meme_mood, meme_punishment;
	private SharedService ss;
	private ReminderSQLServiceImpl impl;
	private int[] kitty;
	// index for punishClick
	private int punishClick = 0;
	// flag for punishClick
	private boolean isPunish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.meme_activity_layout);

		// initialized all global variables
		meme_img = (ImageView) findViewById(R.id.meme);
		meme_mood = (TextView) findViewById(R.id.meme_mood);
		meme_punishment = (TextView) findViewById(R.id.meme_punishment);
		ss = new SharedService(this);
		impl = new ReminderSQLServiceImpl(this);

		// Put all mascot images into the array
		kitty = new int[] { R.drawable.kitty_0, R.drawable.kitty_1,
				R.drawable.kitty_2, R.drawable.kitty_3, R.drawable.kitty_4,
				R.drawable.kitty_5, R.drawable.kitty_6, R.drawable.kitty_7,
				R.drawable.kitty_8, R.drawable.kitty_9, R.drawable.kitty_10 };
		// set the mascot hint and image depends on user's current reminder
		// setting
		setMeme();
		setActionBar();

	}

	/**
	 * Read unpaid reminder in the database and user's total balance to
	 * calculate the mascot's mood
	 * 
	 * @author Yifei Gao
	 * @return index that represents the mascot's mood, 0 happy to 10 sad
	 */
	public int getMood() {
		double total = 0;
		if (ss.readAccountList() != null) {
			for (BankAccount acc : ss.readAccountList()) {
				total += acc.getTotalBalance();
			}
			double unpaid = impl.getUnpaid();// get all unpaid in database
			int index = (int) (unpaid / total * 10);
			return index;
		}
		return 0;
	}

	/**
	 * set the mascot hint and image depends on user's current reminder setting
	 * 
	 * @author Yifei Gao
	 */
	public void setMeme() {
		int mood = getMood();
		if (mood <= 10)
			meme_img.setImageResource(kitty[mood]);
		else
			meme_img.setImageResource(kitty[10]);
		// meme_mood.setText("MeMe's Mood index is:" + mood);
		switch (mood) {
		case 0:
			meme_mood.setText("MeMe is very happy with your spending");
			break;
		case 1:
			meme_mood.setText("MeMe is very happy with your spending");
			break;
		case 2:
			meme_mood.setText("MeMe is happy with your spending");
			break;
		case 3:
			meme_mood.setText("MeMe is happy with your spending");
			break;
		case 4:
			meme_mood.setText("MeMe is a little unhappy with your spending");
			break;
		case 5:
			meme_mood.setText("MeMe is a little unhappy with your spending");
			break;
		case 6:
			meme_mood.setText("MeMe is worried about your spending");
			break;
		case 7:
			meme_mood.setText("MeMe is unhappy about your spending");
			break;
		case 8:
			meme_mood.setText("MeMe want you to reconsider your spending");
			break;
		case 9:
			meme_mood.setText("MeMe want you to stop spending");
			break;
		case 10:
			meme_mood.setText("MeMe is very upset with your spending");
			break;

		default:
			meme_mood.setText("MeMe is very upset with your spending");
			break;
		}
		if (mood >= 5) {
			meme_punishment.setVisibility(View.VISIBLE);
			isPunish = true;
			punish();
		} else {
			meme_punishment.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * When the mascot is unhappy, punish user by petting the mascot
	 * 
	 * @author Yifei Gao
	 */

	public void punish() {
		Random ran = new Random();
		punishClick = ran.nextInt((getMood() - 4) * 10);
		// produce a random number depends on mascot's mood

		meme_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (punishClick > 0) {
					punishClick--;
					// each click decrease the count by 1
				} else if (punishClick == 0 && isPunish) {
					meme_img.setImageResource(kitty[3]);
					meme_mood
							.setText("MeMe still abit worried, but not as sad");
					meme_punishment.setVisibility(View.INVISIBLE);
					isPunish = false;
					Toast.makeText(getApplicationContext(),
							"Meme Forgive you now, but keep spending well.",
							Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("MeMe");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
