package com.example.dto;

import java.sql.Timestamp;

public class TelemetryData {

	private String ccu;
	private String tstampView;
	private double lat;
	private double lon;
	private Timestamp serverTstamp;
	private String serverTstampView;
	private double delayInMinutes;
	private double tstamp;

	public String getServerTstampView() {
		return serverTstampView;
	}

	public void setServerTstampView(String serverTstampView) {
		this.serverTstampView = serverTstampView;
	}

	public double getTstamp() {
		return tstamp;
	}

	public void setTstamp(double tstamp) {
		this.tstamp = tstamp;
	}

	public double getDelayInMinutes() {
		return delayInMinutes;
	}

	public void setDelayInMinutes(double delayInMinutes) {
		this.delayInMinutes = delayInMinutes;
	}

	public String getCcu() {
		return ccu;
	}

	public void setCcu(String ccu) {
		this.ccu = ccu;
	}

	public String getTstampView() {
		return tstampView;
	}

	public void setTstampView(String tstampView) {
		this.tstampView = tstampView;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Timestamp getServerTstamp() {
		return serverTstamp;
	}

	public void setServerTstamp(Timestamp serverTstamp) {
		this.serverTstamp = serverTstamp;
	}

	public TelemetryData(String ccu, double tstamp, String tstampView, double lat, double lon, Timestamp serverTstamp,
			String serverTstampView) {
		super();
		this.ccu = ccu;
		this.tstamp = tstamp;
		this.tstampView = tstampView;
		this.lat = lat;
		this.lon = lon;
		this.serverTstamp = serverTstamp;
		this.serverTstampView = serverTstampView;
	}

}
