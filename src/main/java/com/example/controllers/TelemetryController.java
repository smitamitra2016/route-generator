package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.ITelemetryDAO;
import com.example.dto.TelemetryData;
import com.example.utils.CommonUtils;

@RestController
public class TelemetryController {
	
	@Autowired
	ITelemetryDAO telemetryDAO;

	@RequestMapping("/getDelayedTelem/{ccu}/{date}")
	public List<TelemetryData> getTelemetrySavedLater(@PathVariable String ccu, @PathVariable String date){
		String tableName = CommonUtils.getTelemetryTable(date);
		return telemetryDAO.getTelemetrySavedLater(ccu, tableName, 1);
	}
}
