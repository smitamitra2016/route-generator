package com.example.dto;

public class RouteData {
	private double latitude;
	private double longitude;
	private String dateTime;
	private String additionalData;
	private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public RouteData(double latitude, double longitude, String dateTime, String additionalData, int counter) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.dateTime = dateTime;
		this.additionalData = additionalData;
		this.counter = counter;
	}
	
	public RouteData(){
		//
	}
	
}
