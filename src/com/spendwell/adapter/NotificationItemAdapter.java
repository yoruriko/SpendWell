package com.spendwell.adapter;

import java.util.List;

import com.example.budgetwell.R;
import com.spendwell.entity.NotificaitonItem;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Yifei Gao
 *
 */
public class NotificationItemAdapter extends BaseAdapter {
	private Activity activity;
	private List<NotificaitonItem> list;

	public NotificationItemAdapter(Activity activity,
			List<NotificaitonItem> list) {
		super();
		this.activity = activity;
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
		NotificationItemHolder holder;
		NotificaitonItem item = list.get(position);
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.notification_list_item, null);
			holder = new NotificationItemHolder();
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.level_img = (ImageView) convertView
					.findViewById(R.id.level_img);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (NotificationItemHolder) convertView.getTag();
		}
		holder.date.setText(item.getFormatDate());
		holder.text.setText(item.getText());
		if (item.getLevel() == NotificaitonItem.INFO) {
			holder.title.setText("New Infomation");
			holder.level_img.setImageResource(R.drawable.info);
		} else if (item.getLevel() == NotificaitonItem.SUCCESS) {
			holder.title.setText("New Transaction");
			holder.level_img.setImageResource(R.drawable.success);
		} else if (item.getLevel() == NotificaitonItem.WARNING) {
			holder.title.setText("Warning");
			holder.level_img.setImageResource(R.drawable.warning);
		} else {
			holder.title.setText("Danger!");
			holder.level_img.setImageResource(R.drawable.danger);
		}
		convertView.setTag(holder);
		return convertView;
	}

	/**
	 * Inner Class to hold informations in the view
	 * 
	 * @author Yifei Gao
	 * 
	 */
	public class NotificationItemHolder {
		private TextView title, text, date;
		private ImageView level_img;
	}

}
