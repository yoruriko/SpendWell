package com.spendwell.adapter;

import java.util.List;

import com.example.budgetwell.R;
import com.spendwell.entity.IncomingTransaction;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Yifei Gao
 *
 */
public class IncomingAdapter extends BaseAdapter {
	private Activity mActivity;
	private List<IncomingTransaction> list;

	public IncomingAdapter(Activity mActivity, List<IncomingTransaction> list) {
		this.mActivity = mActivity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Save holder in the view's tag, avoid reloading the information
	 * 
	 * @author Yifei Gao
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IncomingHolder holder;
		IncomingTransaction item = list.get(position);
		if (convertView == null) {
			convertView = mActivity.getLayoutInflater().inflate(
					R.layout.incoming_item_layout, null);
			holder = new IncomingHolder();
			holder.from_name = (TextView) convertView
					.findViewById(R.id.form_name);
			holder.iban = (TextView) convertView.findViewById(R.id.iban);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.balance = (TextView) convertView.findViewById(R.id.balance);
			convertView.setTag(holder);
		} else {
			holder = (IncomingHolder) convertView.getTag();
		}

		holder.from_name.setText(item.getFromName());
		holder.iban.setText(item.getFormatIban());
		holder.date.setText(item.getFormatDate());
		holder.amount.setText(item.getAmount() + "");
		holder.balance.setText(item.getBalance() + "");

		convertView.setTag(holder);

		return convertView;
	}

	/**
	 * Inner Class to hold informations in the view
	 * 
	 * @author Yifei Gao
	 * 
	 */
	public class IncomingHolder {
		private TextView from_name, iban, date, amount, balance;
	}

}
