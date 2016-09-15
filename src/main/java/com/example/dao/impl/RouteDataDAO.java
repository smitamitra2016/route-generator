package com.example.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.IRouteDataDAO;
import com.example.dto.RouteData;
import com.example.utils.CommonUtils;
import com.example.utils.DistanceCalculator;

@Repository
public class RouteDataDAO implements IRouteDataDAO {

	private static final Logger logger = LoggerFactory.getLogger(RouteDataDAO.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<RouteData> getRoutes(String tableName, String latColumn, String lonColumn, String timestamp,
			String ccu) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT  from_unixtime(").append(timestamp).append("/1000) as ").append(timestamp)
				.append("_disp,");
		query.append("(").append(timestamp).append(") as ").append(timestamp);
		query.append(",").append(latColumn).append(",").append(lonColumn);
		query.append(" FROM ").append(tableName).append(" WHERE ccu_desig = '").append(ccu).append("'");
		query.append(" ORDER BY ").append(timestamp);
		logger.info(query.toString());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<RouteData> data = new ArrayList<RouteData>();
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			RouteData routeData = null;
			double prevLat = 0;
			double prevLon = 0;
			double prevTimestamp = 0;
			double distance = 0;
			double speed = 0;
			int counter = 0;
			while (rs.next()) {
				counter++;
				routeData = new RouteData();
				if (prevLat != 0 && prevLon != 0) {
					distance = DistanceCalculator.distance(prevLat, prevLon, rs.getDouble(latColumn),
							rs.getDouble(lonColumn), "M");
					speed = CommonUtils.findSpeed(prevTimestamp, rs.getDouble(timestamp), distance);
				}
				routeData.setDateTime(rs.getString(timestamp + "_disp"));
				prevTimestamp = rs.getDouble(timestamp);
				routeData.setLatitude(rs.getDouble(latColumn));
				prevLat = routeData.getLatitude();
				routeData.setLongitude(rs.getDouble(lonColumn));
				routeData.setCounter(counter);
				prevLon = routeData.getLongitude();
				if (speed == -1) {
					speed = 0;
					logger.info("Missing telemtry between " + data.get(counter - 2).getDateTime() + " and "
							+ routeData.getDateTime());
				}
				if (distance != 0)
					routeData.setAdditionalData(
							ccu + " Distance :" + distance + " Speed :" + speed + "Index: " + counter);
				else
					routeData.setAdditionalData(ccu);
				if (distance > 10) {
					logger.info("Index :" + counter + " Time : " + routeData.getDateTime() + " Distance :" + distance
							+ " Speed :" + speed);
				}
				data.add(routeData);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return data;
	}

}
