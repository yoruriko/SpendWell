package com.spendwell.activity;

import java.util.Map;

import com.example.budgetwell.R;
import com.spendwell.entity.BranchItem;
import com.spendwell.entity.BranchMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity shows the branch details when user click the info window in the
 * map item
 * 
 * @author Yifei Gao
 * 
 */
public class BranchDetailActivity extends Activity {
	private BranchMap bm;
	private BranchItem item;
	// Contains all BranchItem in BranchMap
	private Map<String, BranchItem> map;
	private TextView title, address, postCode, sortCodes, tel, fax, mon, tue,
			wed, thu, fri, sat, sun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.branch_details_layout);
		// Initialized all global variables
		title = (TextView) this.findViewById(R.id.title);
		address = (TextView) this.findViewById(R.id.address);
		postCode = (TextView) this.findViewById(R.id.postCode);
		sortCodes = (TextView) this.findViewById(R.id.sortCode);
		tel = (TextView) this.findViewById(R.id.tel_no);
		fax = (TextView) this.findViewById(R.id.fax_no);
		mon = (TextView) this.findViewById(R.id.mon_txt);
		tue = (TextView) this.findViewById(R.id.tue_txt);
		wed = (TextView) this.findViewById(R.id.wed_txt);
		thu = (TextView) this.findViewById(R.id.thu_txt);
		fri = (TextView) this.findViewById(R.id.fri_txt);
		sat = (TextView) this.findViewById(R.id.sat_txt);
		sun = (TextView) this.findViewById(R.id.sun_txt);

		// Create new BrachMap Object and get all BranchItem in it
		bm = new BranchMap();
		map = bm.getBranchMap();

		// Get Branch's name form the intent start this activity
		Intent it = getIntent();
		String branhName = it.getExtras().getString("branch");
		item = map.get(branhName);

		// Set contents values
		initDetails();
		// Set the actionbar layout
		setActionBar();
	}

	/**
	 * Set all content values using given BranchItem
	 * 
	 * @author Yifei Gao
	 */
	public void initDetails() {
		title.setText(item.getTitle());
		address.setText(item.getAddress());
		postCode.setText(item.getPostCode());
		sortCodes.setText(item.getSortCode());
		tel.setText(item.getTel());
		fax.setText(item.getFax());
		String[] hours = item.getHour();
		mon.setText(hours[0]);
		tue.setText(hours[1]);
		wed.setText(hours[2]);
		thu.setText(hours[3]);
		fri.setText(hours[4]);
		sat.setText(hours[5]);
		sun.setText(hours[6]);

	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText(title.getText());

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
