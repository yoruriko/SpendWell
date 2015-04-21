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
public class BranchItem {
	private double lat, lng;
	private String address, postCode, sortCode, title, tel, fax;
	private String snippet = "Clik to view more details";
	private String[] hour;

	public BranchItem(double lat, double lng, String title, String address,
			String postCode, String[] hour, String sortCode, String tel,
			String fax) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.postCode = postCode;
		this.hour = hour;
		this.title = title;
		this.sortCode = sortCode;
		this.tel = tel;
		this.fax = fax;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public LatLng getLatLng() {
		return new LatLng(getLat(), getLng());
	}

	public MarkerOptions getMarker() {
		return new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.branch))
				.title(getTitle()).snippet(snippet).position(getLatLng());
	}

	public void setHour(String[] hours) {
		this.hour = hours;
	}

	public String[] getHour() {
		return hour;
	}
}
