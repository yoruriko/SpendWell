package com.spendwell.activity;

import com.example.budgetwell.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.spendwell.fragment.PayFragment;
import com.spendwell.fragment.ReminderFragment;
import com.spendwell.fragment.ShakeFragment;
import com.spendwell.service.NotificationServeice;
import com.spendwell.service.SharedService;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * The main page of he application, must have user's account detail and login
 * token in the shared Preference
 * 
 * @author Yifei Gao
 * 
 */
public class MainActivity extends FragmentActivity {
	private SharedService ss;
	private NotificationServeice ns;
	private NotificationServeice.MyBinder mBinder;
	private ResideMenuItem map_btn, notification_btn, mascot_btn, history_btn,
			help_btn, setting_btn, logout_btn, like_btn, product_btn;
	private long exitTime;
	private ResideMenu resideMenu;
	private Button balance_btn, reminder_btn, transaction_btn, left_menu,
			right_menu, focus_btn;

	public Fragment balanceFragment, reminderFragment, transactionFragment;

	// Bind the Notification Service with MainAcitivty and start loading
	// notifications
	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (NotificationServeice.MyBinder) service;
			mBinder.startLoading();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity_layout);

		ss = new SharedService(this);

		// Start Notification Service and bind the service with this activity
		Intent startService = new Intent(this, NotificationServeice.class);
		startService(startService);
		Intent bindService = new Intent(this, NotificationServeice.class);
		bindService(bindService, connection, BIND_AUTO_CREATE);

		// initialized all buttons in the layout
		balance_btn = (Button) this.findViewById(R.id.balance_btn);
		reminder_btn = (Button) this.findViewById(R.id.reminder_btn);
		transaction_btn = (Button) this.findViewById(R.id.transaction_btn);

		left_menu = (Button) this.findViewById(R.id.title_bar_left_menu);
		right_menu = (Button) this.findViewById(R.id.title_bar_right_menu);

		// Initialed fragments used in the viewpager
		balanceFragment = new ShakeFragment();
		reminderFragment = new ReminderFragment();
		transactionFragment = new PayFragment();

		// Set the default focus fragment to be the ShakeFragment
		setSelected();
		// Initialized all menu items in the reside menu
		initMenuItem();
		// Initialized all listeners in the Layout
		initListener();

		if (savedInstanceState == null) {
			changeFragment(balanceFragment);
		}
	}

	/**
	 * Initialized all menu items in the reside menu
	 * 
	 * @author Yifei Gao
	 */
	public void initMenuItem() {
		// Find reside menu in the layout and set its background, attach it to
		// the MainActivity
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setScaleValue(0.6f);

		map_btn = new ResideMenuItem(this, R.drawable.icon_location, "Location");
		map_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						MapAcitivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		notification_btn = new ResideMenuItem(this,
				R.drawable.icon_notification, "Notification");
		notification_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						NotificationListActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		mascot_btn = new ResideMenuItem(this, R.drawable.icon_mascot, "MeMe");
		mascot_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						MascotActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		history_btn = new ResideMenuItem(this, R.drawable.icon_history,
				"History");
		history_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						ShowHistoryActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		product_btn = new ResideMenuItem(this, R.drawable.icon_product,
				"Product");
		product_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						ProductActivity.class);
				startActivity(it);
				resideMenu.closeMenu();

			}
		});

		help_btn = new ResideMenuItem(this, R.drawable.icon_help, "Help");
		help_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						HelpActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		setting_btn = new ResideMenuItem(this, R.drawable.icon_setting,
				"Settings");
		setting_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		like_btn = new ResideMenuItem(this, R.drawable.icon_like_us, "Like Us");
		like_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						LikeUsActivity.class);
				startActivity(it);
				resideMenu.closeMenu();
			}
		});

		logout_btn = new ResideMenuItem(this, R.drawable.icon_logout, "Log Out");
		logout_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogoutDialog();
			}
		});

		// add MenuItem into given postions in the resideMenu
		resideMenu.addMenuItem(map_btn, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(notification_btn, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(mascot_btn, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(history_btn, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(product_btn, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(help_btn, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(setting_btn, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(like_btn, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(logout_btn, ResideMenu.DIRECTION_RIGHT);

	}

	/**
	 * Set the default selected Fragment as ShakeFragment, or set the selected
	 * Fragment to corresponding focused button
	 * 
	 * @author Yifei Gao
	 */
	public void setSelected() {
		if (focus_btn == null) {
			focus_btn = balance_btn;
		}
		balance_btn.setSelected(false);
		transaction_btn.setSelected(false);
		reminder_btn.setSelected(false);

		focus_btn.setSelected(true);
	}

	/**
	 * Initialized all listeners in the layout
	 * 
	 * @author Yifei Gao
	 */

	public void initListener() {

		left_menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});

		right_menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
			}
		});

		balance_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				focus_btn = balance_btn;
				setSelected();
				changeFragment(balanceFragment);
			}
		});

		transaction_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				focus_btn = transaction_btn;
				setSelected();
				changeFragment(transactionFragment);
			}
		});

		reminder_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				focus_btn = reminder_btn;
				changeFragment(reminderFragment);
				setSelected();
			}
		});
	}

	/**
	 * Show AlertDiaog when user press the log out button
	 * 
	 * @author Yifei Gao
	 */
	public void showLogoutDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.logout_message);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						resideMenu.closeMenu();
						return;
					}
				});
		builder.create().show();
	}

	private void changeFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_box, fragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();

	}

	/**
	 * Override the back button, when resideMenu is open close the resideMenu,
	 * User need to press back twice within time interval to exit the
	 * application
	 */
	@Override
	public void onBackPressed() {

		if (resideMenu.isOpened()) {
			resideMenu.closeMenu();
			return;
		}
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "Press back again to exit",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
			return;
		}
		super.onBackPressed();
	}
}
