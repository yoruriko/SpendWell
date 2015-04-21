package com.spendwell.activity;

import java.util.List;

import com.example.budgetwell.R;
import com.google.gson.Gson;
import com.spendwell.entity.BankAccount;
import com.spendwell.entity.IncomingTransaction;
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Activity display a incoming history, the intent starting this activity
 * must contain the incomingItem
 * 
 * @author Yifei Gao
 * 
 */
public class IncomingDetailActivity extends Activity {

	private TextView from_name, from_iban, date, description, amount, balance,
			to_acc;
	// Using shared service to get the Incoming list
	private SharedService ss;
	private List<BankAccount> accList;
	private IncomingTransaction item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.incoming_details_layout);
		ss = new SharedService(this);

		// Initialized all global variables
		from_name = (TextView) this.findViewById(R.id.from_name);
		from_iban = (TextView) this.findViewById(R.id.from_iban);
		date = (TextView) this.findViewById(R.id.date);
		description = (TextView) this.findViewById(R.id.description);
		amount = (TextView) this.findViewById(R.id.amount);
		balance = (TextView) this.findViewById(R.id.balance);
		to_acc = (TextView) this.findViewById(R.id.to_acc);
		accList = ss.readAccountList();

		// Get the Incoming item stored in the intent starting this activity
		String json = getIntent().getExtras().getString("item");
		Gson gson = new Gson();
		item = gson.fromJson(json, IncomingTransaction.class);

		setContent();
		setActionbar();
		super.onCreate(savedInstanceState);
	}

	/**
	 * Set Contents of the page using given Incoming Item
	 * 
	 * @author Yifei Gao
	 */
	public void setContent() {
		from_name.setText(item.getFromName());
		from_iban.setText(item.getBarIban());
		date.setText(item.getLongDate());
		description.setText(item.getDescription());
		amount.setText(item.getAmount() + "");
		balance.setText(item.getBalance() + "");

		for (int i = 0; i < accList.size(); i++) {
			if (item.getToAccount() == accList.get(i).getId()) {
				to_acc.setText(accList.get(i).getAccountName());
			}
		}
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionbar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Incoming");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
