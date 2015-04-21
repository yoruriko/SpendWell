package com.spendwell.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.budgetwell.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.spendwell.entity.AtmItem;
import com.spendwell.entity.AtmList;
import com.spendwell.entity.BranchItem;
import com.spendwell.entity.BranchMap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Map activity that display brahces and Atm on the google Map
 * 
 * @author Yifei Gao
 * 
 */
public class MapAcitivity extends Activity implements OnMapReadyCallback {

	private BranchMap bm;
	private AtmList al;
	private Map<String, BranchItem> data;
	private Map<String, Marker> branchMarkers;
	private Map<String, Marker> atmMarkers;
	private List<AtmItem> atms;
	private Button nearBranch, nearAtm;
	private MapFragment mapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map_layout);
		// init all Branch and Atm Items, Store them into list
		bm = new BranchMap();
		al = new AtmList();
		atms = al.getList();
		data = bm.getBranchMap();

		// initialized the Map fragment
		branchMarkers = new HashMap<String, Marker>();
		atmMarkers = new HashMap<String, Marker>();
		nearBranch = (Button) findViewById(R.id.branch);
		nearAtm = (Button) findViewById(R.id.atm);
		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		mapFragment.getMapAsync(this);
		initListener();
		setActionbar();
	}

	/**
	 * Set the camera to default position and add Markers of Atm and Branches on
	 * to the Map
	 * 
	 * @author Yifei Gao
	 */
	@Override
	public void onMapReady(GoogleMap map) {
		map.setMyLocationEnabled(true);

		Location myLocation = map.getMyLocation();
		LatLng def = new LatLng(54.97279, -1.60247);
		if (myLocation != null) {
			LatLng myLatLng = new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 13));
		} else {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(def, 13));
		}
		// set all Branch Markers onto the Map
		for (BranchItem item : data.values()) {
			Marker marker = map.addMarker(item.getMarker());
			branchMarkers.put(item.getTitle(), marker);
		}
		// set all ATM markers onto the Map
		for (AtmItem item : atms) {
			Marker marker = map.addMarker(item.getMarker());
			atmMarkers.put(item.getName(), marker);
		}

		// start BrachDetailActivity when the info box is instance of a
		// BranchItem's Marker
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				if (data.containsKey(marker.getTitle())) {
					Intent it = new Intent(getApplicationContext(),
							BranchDetailActivity.class);
					// put the brach name into the itent
					it.putExtra("branch", marker.getTitle());
					startActivity(it);
				}
			}
		});

	}

	/**
	 * Find Nearest BranchItem
	 * 
	 * @author Yifei Gao
	 * @param myLocation
	 *            user's current Loction
	 * @return BranchItem nearest to given position
	 */
	public BranchItem getNearestBranch(Location myLocation) {
		BranchItem[] items = data.values().toArray(new BranchItem[data.size()]);
		BranchItem nearest = items[0];

		// min=shorestLat & shorestLog
		double min = Math.abs(nearest.getLat() - myLocation.getLatitude())
				+ Math.abs(nearest.getLng() - myLocation.getLongitude());

		for (int i = 1; i < items.length; i++) {
			double distance = Math.abs(items[i].getLat()
					- myLocation.getLatitude())
					+ Math.abs(items[i].getLng() - myLocation.getLongitude());
			if (distance < min) {
				min = distance;
				nearest = items[i];
			}
		}
		return nearest;
	}

	/**
	 * Find Nearest AtmItem
	 * 
	 * @author Yifei Gao
	 * @param myLocation
	 *            user's current Loction
	 * @return AtmItem nearest to given position
	 */
	public AtmItem getNearestAtm(Location myLocation) {
		AtmItem nearest = atms.get(0);

		// min=shorestLat & shorestLog
		double min = Math.abs(nearest.getLat() - myLocation.getLatitude())
				+ Math.abs(nearest.getLng() - myLocation.getLongitude());

		for (int i = 1; i < atms.size(); i++) {
			double distance = Math.abs(atms.get(i).getLat()
					- myLocation.getLatitude())
					+ Math.abs(atms.get(i).getLng() - myLocation.getLongitude());
			if (distance < min) {
				min = distance;
				nearest = atms.get(i);
			}
		}
		return nearest;
	}

	public void initListener() {
		nearBranch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Location myLocation = mapFragment.getMap().getMyLocation();

				if (myLocation != null) {
					BranchItem item = getNearestBranch(myLocation);
					mapFragment.getMap().animateCamera(
							CameraUpdateFactory.newLatLngZoom(item.getLatLng(),
									18));
					branchMarkers.get(item.getTitle()).showInfoWindow();
					// move the camera to the nearest Branch item and open the
					// info window
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Unable to fetch the current location, please check your gps and internet state",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		nearAtm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Location myLocation = mapFragment.getMap().getMyLocation();
				if (myLocation != null) {
					AtmItem item = getNearestAtm(myLocation);
					mapFragment.getMap().animateCamera(
							CameraUpdateFactory.newLatLngZoom(item.getLatLng(),
									18));
					// move the camera to the nearest atm item and open the
					// info window
					atmMarkers.get(item.getName()).showInfoWindow();
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Unable to fetch the current location, please check your gps and internet state",
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
	public void setActionbar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Location");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
