package com.spendwell.entity;

import com.example.budgetwell.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * 
 * @author Yifei Gao
 *
 */
public class AtmItem {
	private String name, hours;
	private double lat, lng;

	public AtmItem(String name, String hours, double lat, double lng) {
		super();
		this.name = name;
		this.hours = hours;
		this.lat = lat;
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public LatLng getLatLng() {
		return new LatLng(getLat(), getLng());
	}

	public MarkerOptions getMarker() {
		return new MarkerOptions().title(getName()).snippet(getHours())
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.atm))
				.position(getLatLng()).anchor(1.0f, 0.0f);
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
