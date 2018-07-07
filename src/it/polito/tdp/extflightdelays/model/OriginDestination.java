package it.polito.tdp.extflightdelays.model;


public class OriginDestination implements Comparable <OriginDestination>{
	
	private Airport origin;
	private Airport destination;
	private double avgDistance;
	
	public OriginDestination(Airport origin, Airport destination, Double avgDistance) {
		super();
		this.origin = origin;
		this.destination = destination;
		
		this.avgDistance = avgDistance;
	}

	public Airport getOrigin() {
		return origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public double getAvgDistance () {
		return this.avgDistance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OriginDestination other = (OriginDestination) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Peso: %.3f, da %3s a %3s", avgDistance, origin.getId(), destination.getId());
	}

	@Override
	public int compareTo(OriginDestination o) {
		return -Double.compare(this.avgDistance, o.avgDistance);
	}
	
}
