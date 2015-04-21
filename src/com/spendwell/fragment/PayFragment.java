package com.spendwell.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.budgetwell.R;
import com.spendwell.entity.BankAccount;
import com.spendwell.service.SharedService;
import com.spendwell.utils.PullView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * Fragment that obtained in the MainActivity's View pager, handle user's
 * transactions
 * 
 * @author Yifei Gao
 * 
 */
public class PayFragment extends Fragment {
	private SharedService ss;
	private Spinner sp;
	private List<BankAccount> list;
	private EditText toAccName, toSortCode, toIban, description, amount;
	private PullView pullView;
	private Button pay;
	private RequestQueue mQueue;
	private String url = "http://spendwell.herokuapp.com/api/outgoing/";

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				pay.performClick();
			}
			if (msg.what == 2) {
				onSucess();
			}
			if (msg.what == 3) {
				onFail();
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_activity, null);

		mQueue = Volley.newRequestQueue(getActivity());
		ss = new SharedService(getActivity());
		sp = (Spinner) view.findViewById(R.id.from_acc);
		pullView = (PullView) view.findViewById(R.id.pullView);
		pullView.setHandler(mHandler);

		toAccName = (EditText) view.findViewById(R.id.toAccountName);
		toSortCode = (EditText) view.findViewById(R.id.toSortCode);
		toIban = (EditText) view.findViewById(R.id.toIban);
		amount = (EditText) view.findViewById(R.id.pay_amount);
		description = (EditText) view.findViewById(R.id.des_edt);
		pay = (Button) view.findViewById(R.id.pay_btn);

		list = ss.readAccountList();
		String[] acc = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			acc[i] = list.get(i).getAccountName();
		}
		// set adapter to the spinner so it contains all the accounts
		sp.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.select_acc_item, acc));

		pay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// check for empty inputs
				if (TextUtils.isEmpty(toAccName.getText())) {
					Toast.makeText(getActivity(),
							"Please Fill the to Account Name",
							Toast.LENGTH_SHORT).show();
					pullView.reset();
					return;
				} else if (TextUtils.isEmpty(toSortCode.getText())) {
					Toast.makeText(getActivity(),
							"Please fill the to sortcode", Toast.LENGTH_SHORT)
							.show();
					pullView.reset();
					return;
				} else if (TextUtils.isEmpty(toIban.getText())) {
					Toast.makeText(getActivity(), "Pelase fill the to Iban",
							Toast.LENGTH_SHORT).show();
					pullView.reset();
					return;
				} else if (TextUtils.isEmpty(amount.getText())) {
					Toast.makeText(getActivity(), "Please fill the amount",
							Toast.LENGTH_SHORT).show();
					pullView.reset();
					return;
				}
				// show the dialog if user's input is valid
				showDialog();
			}
		});

		return view;
	}

	/**
	 * send request to the server to process the transaction
	 * 
	 * @author Yifei Gao
	 */
	public void sendRequest() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("fromAccount", list.get(sp.getSelectedItemPosition())
				.getId() + "");
		params.put("toName", toAccName.getText().toString());
		params.put("toSortCode", toSortCode.getText().toString());
		params.put("toIban", toIban.getText().toString());
		params.put("amount", amount.getText().toString());
		String des = " ";
		if (!TextUtils.isEmpty(description.getText())) {
			des = description.getText().toString();
		}
		params.put("description", des);

		JSONObject json = new JSONObject(params);

		JsonObjectRequest request = new JsonObjectRequest(url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						mHandler.obtainMessage(2).sendToTarget();
						// show the Secuess page
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						mHandler.obtainMessage(3).sendToTarget();
						// show the fail page
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.putAll(super.getHeaders());
				headers.put("Authorization", "Token " + ss.getRequestToken());
				return headers;
			}
		};
		mQueue.add(request);
	}

	/**
	 * Show dialog for user to confirm the transaction
	 * 
	 * @author Yifei Gao
	 */
	public void showDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setMessage("Please Confirm your Transaction: To:"
				+ toAccName.getText().toString() + " "
				+ toSortCode.getText().toString() + " "
				+ toIban.getText().toString() + " with £ "
				+ amount.getText().toString());
		builder.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						sendRequest();// send request if user confirm the
										// transcation
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						pullView.reset();
					}
				});
		builder.create().show();
	}

	/**
	 * Display the Success popup window and clear the fragment inputs
	 * 
	 * @author Yifei Gao
	 */
	public void onSucess() {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.popup_success, null);
		PopupWindow window = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		window.setFocusable(true);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				clear();
				pullView.reset();
			}
		});
		window.showAtLocation(pullView, Gravity.CENTER, 0, 0);

	}

	// clear the contents in the fragment
	public void clear() {
		toAccName.setText("");
		toSortCode.setText("");
		toIban.setText("");
		amount.setText("");
		description.setText("");
	}

	/**
	 * Display the Fail popup window
	 * 
	 * @author Yifei Gao
	 */
	public void onFail() {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.popup_error, null);
		PopupWindow window = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		window.setFocusable(true);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				pullView.reset();
			}
		});
		window.showAtLocation(pullView, Gravity.CENTER, 0, 0);

	}
}
