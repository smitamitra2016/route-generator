package com.example.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;

import com.example.dto.RouteData;
import com.example.dto.TelemetryData;

public class CommonUtils {

	/**
	 * Find speed of vehicle in mph
	 * 
	 * @param startTimeSecs
	 * @param endTimeSecs
	 * @param distance
	 * @return
	 */
	public static double findSpeed(double startTimeSecs, double endTimeSecs, double distance) {
		if (startTimeSecs != 0 && endTimeSecs != 0 && distance != 0) {
			double timeInHours = (endTimeSecs - startTimeSecs) / 3600;
			if ((endTimeSecs - startTimeSecs) > 900) {// If no telemetry in
														// 15mins
				return -1;
			}
			return distance / timeInHours;
		}
		return 0;
	}

	/**
	 * Filter part of a route based on start index
	 * 
	 * @param routedata
	 * @param start
	 * @param nextPoints
	 * @return
	 */
	public static List<RouteData> getPartialRoute(List<RouteData> routedata, int start, int nextPoints) {
		List<RouteData> partialRoute = new ArrayList<>();
		if (routedata != null && routedata.size() > (start + nextPoints)) {
			int startIndex = start - 1;
			int endIndex = startIndex + nextPoints;
			partialRoute.addAll(routedata.subList(startIndex, endIndex + 1));
		}
		return partialRoute;
	}

	/**
	 * Get difference in seconds between two timestamps
	 * 
	 * @param telemetry
	 * @return
	 */
	public static double getSecondsBetweenTimestamps(TelemetryData telemetry) {
		if (telemetry != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			try {
				Date receivedDate = dateFormat.parse(telemetry.getTstampView());
				Date ccuDate = dateFormat.parse(telemetry.getServerTstampView());
				return Math.abs((ccuDate.getTime() / 1000 - receivedDate.getTime() / 1000));// Millisecs
				// to
				// secs
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * Populate tablename for telemetry based on date
	 * @param date
	 * @return
	 */
	public static String getTelemetryTable(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			DateTime dateOfTable = new DateTime(dateFormat.parse(date).getTime());
			DateTime today = new DateTime();
			if (DateUtils.isSameDay(dateOfTable.toDate(), today.toDate())) {
				return "t_ccu";
			} else {
				dateFormat = new SimpleDateFormat("YYMMdd");
				String tableName = "t_ccu_" + dateFormat.format(dateOfTable.toDate());
				return tableName;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
