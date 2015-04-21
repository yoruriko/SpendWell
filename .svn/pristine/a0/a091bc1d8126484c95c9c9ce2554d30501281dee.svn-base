package com.spendwell.activity;

import com.example.budgetwell.R;
import com.spendwell.fragment.IncomingFragment;
import com.spendwell.fragment.OutgoingFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * This activity send request to server to obtain the transaction histories and
 * store them in the share service, display them in the listView
 * 
 * @author Yifei Gao
 * 
 */
public class ShowHistoryActivity extends FragmentActivity {

	private Fragment incoming;
	private Fragment outgoing;
	private ViewPager mViewPager;
	private ViewPagerAdapter mAdapter;
	private ImageButton incoming_btn, outgoing_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history_activity);
		// initialized all global variables
		incoming_btn = (ImageButton) this.findViewById(R.id.incoming_btn);
		outgoing_btn = (ImageButton) this.findViewById(R.id.outgoing_btn);
		mViewPager = (ViewPager) this.findViewById(R.id.pager);
		mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

		// set adapter and listener of the viewPager using the inner classes
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new HistroyListener());

		initListener();
		setActionbar();

	}

	/**
	 * Inner Class extends FragmentPagerAdapter which override the getItem
	 * method to reutrn incoming and ougoing fragment
	 * 
	 * @author Yifei Gao
	 * 
	 */
	public class ViewPagerAdapter extends FragmentPagerAdapter {
		private String[] titles = new String[] { "InComing", "OutGoing" };

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (incoming == null) {
					incoming = new IncomingFragment();
				}
				return incoming;
			case 1:
				if (outgoing == null) {
					outgoing = new OutgoingFragment();
				}
				return outgoing;
			default:
				return null;

			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

	}

	/**
	 * Override the on page selected method to change the viewPager correctly
	 * 
	 * @author Yifei Gao
	 * 
	 */
	public class HistroyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			if (position == 0) {
				incoming_btn.setImageResource(R.drawable.incoming_2);
				outgoing_btn.setImageResource(R.drawable.outgoing_2);
			}
			if (position == 1) {
				incoming_btn.setImageResource(R.drawable.incoming_1);
				outgoing_btn.setImageResource(R.drawable.outgoing_1);
			}
		}

	}

	/**
	 * Set the buttons on the actionBar to change the page in ViewPager
	 * 
	 * @author Yifei Gao
	 */
	public void initListener() {
		incoming_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
				incoming_btn.setImageResource(R.drawable.incoming_2);
				outgoing_btn.setImageResource(R.drawable.outgoing_2);
			}
		});

		outgoing_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
				incoming_btn.setImageResource(R.drawable.incoming_1);
				outgoing_btn.setImageResource(R.drawable.outgoing_1);
			}
		});
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionbar() {

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
