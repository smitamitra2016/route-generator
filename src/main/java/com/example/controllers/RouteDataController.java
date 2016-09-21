package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dao.IRouteDataDAO;
import com.example.dto.RouteData;
import com.example.utils.CommonUtils;

@Controller
public class RouteDataController {

	@Autowired
	private IRouteDataDAO routeDataDAO;

	@RequestMapping("/route/{train}/{date}")
	public String showRoute(@PathVariable String train, @PathVariable String date, Model model) {
		String tableName = CommonUtils.getTelemetryTable(date);
		List<RouteData> routeData = routeDataDAO.getRoutes(tableName, "gps_lat", "gps_long", "tstamp", train);
		model.addAttribute("routeData", routeData);
		return "journeyRoute";
	}
	
	
}
