package com.example.dao;

import java.util.List;

import com.example.dto.TelemetryData;

public interface ITelemetryDAO {
	public List<TelemetryData> getTelemetrySavedLater(String ccu, String tableName, long delayInMinutes);
}
