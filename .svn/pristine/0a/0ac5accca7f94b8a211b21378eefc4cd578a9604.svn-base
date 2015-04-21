package com.spendwell.activity;

import java.util.List;

import com.example.budgetwell.R;
import com.google.gson.Gson;
import com.spendwell.entity.BankAccount;
import com.spendwell.entity.OutGoingTransaction;
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This activity shows the detail of the outgoing Transaction, required the
 * intent pass transaction item
 * 
 * @author Yifei Gao
 * 
 */
public class OutgoingDetailActivity extends Activity {

	private TextView to_name, to_sortCode, to_iban, date, description, amount,
			from_acc;
	private SharedService ss;
	private List<BankAccount> accList;
	private OutGoingTransaction item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.outgoing_details_layout);
		// initialized all global variables
		to_name = (TextView) this.findViewById(R.id.to_name);
		to_sortCode = (TextView) this.findViewById(R.id.to_sortCode);
		to_iban = (TextView) this.findViewById(R.id.to_iban);
		date = (TextView) this.findViewById(R.id.date);
		description = (TextView) this.findViewById(R.id.description);
		amount = (TextView) this.findViewById(R.id.amount);
		from_acc = (TextView) this.findViewById(R.id.from_acc);
		ss = new SharedService(this);
		accList = ss.readAccountList();

		String json = getIntent().getExtras().getString("item");
		Gson gson = new Gson();
		item = gson.fromJson(json, OutGoingTransaction.class);
		// obtain the outgoing transaction item from the intent

		// set the content with given transaction item
		setContent();
		// set up the action bar
		setActionbar();
	}

	/**
	 * Set the layout contents with transaction item details
	 * 
	 * @author Yifei Gao
	 */
	public void setContent() {
		to_name.setText(item.getToName());
		to_sortCode.setText(item.getFormatSortCode());
		to_iban.setText(item.getBarIban());
		date.setText(item.getLongDate());
		description.setText(item.getDescription());
		amount.setText(item.getAmount() + "");
		for (int i = 0; i < accList.size(); i++) {
			if (accList.get(i).getId() == item.getFromAccount()) {
				from_acc.setText(accList.get(i).getAccountName());
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
		text.setText("Outgoing");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
