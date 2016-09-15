package com.example.dao;

import java.util.List;

import com.example.dto.RouteData;

public interface IRouteDataDAO {

	public List<RouteData> getRoutes(String tableName,String latColumn,String lonColumn, String timestamp, String ccu);
}
