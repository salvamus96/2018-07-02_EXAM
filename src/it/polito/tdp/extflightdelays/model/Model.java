package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO fdao;
	
	private List <Airline> airlines;
	private List <Airport> airports;
	
	private AirportIdMap airportIdMap;
	private AirlineIdMap airlineIdMap;
	
	private Graph <Airport, DefaultWeightedEdge> grafo;
	
	private List <OriginDestination> edges;

	private List <AirportDistance> soluzione;

	private double distanceTot;
	 
	public Model () {
		
		this.fdao = new ExtFlightDelaysDAO();
		this.airportIdMap = new AirportIdMap ();
		this.airlineIdMap = new AirlineIdMap();
		
		this.edges = new ArrayList<>();

		this.airports = new ArrayList<>();
		
	}
	
	public void creaGrafo(double minDistance) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// caricamento vertici
		this.airports = this.fdao.loadAllAirports(this.airportIdMap);
				
		Graphs.addAllVertices(grafo, this.airports);
				
		// caricamento archi
		this.edges = this.fdao.getAllEdges(this.airportIdMap);

		for (OriginDestination od : this.edges)
			
			// l'arco viene creato solo se la distanza media è > di minDistance
			if ((!od.getOrigin().equals(od.getDestination())) && (od.getAvgDistance() > minDistance))
				Graphs.addEdge(this.grafo, od.getOrigin(),  od.getDestination(), od.getAvgDistance());
			
		System.out.println(grafo.vertexSet().size() + " " + grafo.edgeSet().size());
			
	}
	
	public List<AirportDistance> getAdiacenti(Airport partenza){
		// lista degli aeroporti adiacenti a quello di partenza
		List <Airport> adiacenti = Graphs.neighborListOf(this.grafo, partenza);
		
		List <AirportDistance> result = new ArrayList<>();
		
		for (Airport a : adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(a, partenza);
			double distance = this.grafo.getEdgeWeight(e);
			result.add(new AirportDistance(a, distance));
			
		}
		
		return result;
	}
	
	public List<Airport> getAllAirport() {
		if (this.airports != null)
			return this.airports;
		
		return new ArrayList<>();
		}
	
	
	// 2° PUNTO
	
	
	public List<AirportDistance> getCamminoMassimo(Airport partenza, double maxDistanceTot) {
		this.soluzione = new ArrayList<>();
		
		List <AirportDistance> parziale = new ArrayList<>();
		
		parziale.add(new AirportDistance(partenza, 0));
		
		this.distanceTot = 0.0;
		
		this.ricorsiva (parziale, maxDistanceTot);
		
		return soluzione;
	}

	private void ricorsiva(List<AirportDistance> parziale, double maxDistanceTot) {
		
		if (this.calcoloDistanza(parziale) > this.distanceTot) {
			
			this.distanceTot = this.calcoloDistanza(parziale);
			this.soluzione = new ArrayList<>(parziale);
		}
		
		AirportDistance ultimo = parziale.get(parziale.size() - 1);
		List <AirportDistance> prossimi = this.getAdiacenti(ultimo.getAirport());
		
		for (AirportDistance a : prossimi) {
			if (controllaParziale(parziale, a, maxDistanceTot)) {
				parziale.add(new AirportDistance(a.getAirport(), a.getDistance()));
				this.ricorsiva(parziale, maxDistanceTot);
				parziale.remove(parziale.size() - 1);
			}
				
		}
			
		
	}

	private boolean controllaParziale(List<AirportDistance> parziale, AirportDistance a, double maxDistanceTot) {
		
		if (parziale.contains(a))
			return false;
		if (this.calcoloDistanza(parziale) + a.getDistance() > maxDistanceTot)
			return false;
		
		return true;
	}

	private double calcoloDistanza(List<AirportDistance> parziale) {
		double sum = 0.0;
		for (AirportDistance a : parziale) 
			sum += a.getDistance();
	
		return sum;
	}

	public double getDistanceTot() {
		return distanceTot;
	}
	
	
	

}
