package com.example.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dao.impl.RouteDataDAO;
import com.example.dto.RouteData;

@RunWith(SpringJUnit4ClassRunner.class)
public class RouteDataDAOTest {

	@Mock
	private RouteDataDAO routeDataDAO;

	@Mock
	private DataSource dataSource;

	private List<RouteData> routeData;

	@Before
	public void init() {
		routeData = new ArrayList<>();
		routeData.add(new RouteData(22.3, -17.5, "2016-08-09 00:01:10", "CCU: 1234", 1));
		routeData.add(new RouteData(22.35, -17.55, "2016-08-09 00:01:20", "CCU: 1234", 2));
		when(this.routeDataDAO.getRoutes("t_ccu_160908", "gps_lat", "gps_long", "tstamp", "1234"))
				.thenReturn(routeData);
	}

	@Test
	public void testGetRoutes() {
		List<RouteData> routeData = routeDataDAO.getRoutes("t_ccu_160908", "gps_lat", "gps_long", "tstamp", "1234");
		assertEquals(routeData.size(), 2);
		assertTrue(routeData.get(0).getAdditionalData().contains("1234"));
	}
}
