package it.polito.tdp.extflightdelays.model;

public class AirportDistance implements Comparable <AirportDistance> {

	private Airport airport;
	private double distance;
	
	public AirportDistance(Airport airport, double distance) {
		super();
		this.airport = airport;
		this.distance = distance;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return String.format("%s (%d - %s) - %.2f", this.airport.getName(), this.airport.getId(), this.airport.getIdCode(), this.distance);
	}

	@Override
	public int compareTo(AirportDistance o) {
		return -(Double.compare(this.distance, o.distance));
	}	
	

}
