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

import com.example.dao.ITelemetryDAO;
import com.example.dto.TelemetryData;
import com.example.utils.CommonUtils;

@Repository
public class TelemetryDAO implements ITelemetryDAO {

	private static final Logger logger = LoggerFactory.getLogger(TelemetryDAO.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<TelemetryData> getTelemetrySavedLater(String ccu, String tableName, long delayInMinutes) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT  from_unixtime(tstamp/1000) as ").append("tstamp_disp,");
		query.append("tstamp");
		query.append(",gps_lat,gps_long,server_tstamp");
		query.append(" FROM ").append(tableName).append(" WHERE ccu_desig = '").append(ccu).append("'");
		query.append(" ORDER BY tstamp");
		logger.info(query.toString());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TelemetryData> data = new ArrayList<TelemetryData>();
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			TelemetryData telemData = null;
			double timeDiff = 0;
			double timeLimit = delayInMinutes * 60;// secs
			while (rs.next()) {
				telemData = new TelemetryData(ccu, rs.getDouble("tstamp"), rs.getString("tstamp_disp"),
						rs.getDouble("gps_lat"), rs.getDouble("gps_long"), rs.getTimestamp("server_tstamp"),
						rs.getString("server_tstamp"));
				timeDiff = CommonUtils.getSecondsBetweenTimestamps(telemData);
				if (timeDiff > timeLimit) {
					data.add(telemData);
				}
				telemData.setDelayInMinutes(timeDiff / 60);
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
