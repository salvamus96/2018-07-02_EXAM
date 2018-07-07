package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {

	private Map <Integer, Airport> map;
	
	
	public AirportIdMap () {
		
		this.map = new HashMap <> ();
	}
	
	public Airport get (int airportId) {
		return map.get(airportId);
	}

	public Airport get (Airport airport) {
		Airport old = map.get(airport.getId());
		if (old == null) {
			map.put(airport.getId(), airport);
			return airport;
		}
		return old;
	}
	
	public void put (int airportId, Airport airport) {
		map.put(airportId, airport);
	}
}
