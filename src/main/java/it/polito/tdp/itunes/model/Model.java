package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	List<Album> vertices;
	List<BilancioAlbum> bilancio;
	SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> graph;
	ItunesDAO dao;
	
	int bestScore;
	List<Album> bestPath;
	
	public Model() {
		bilancio = new ArrayList<>();
		vertices = new ArrayList<>();
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao = new ItunesDAO();
	}
	
	//ricorsione(cammino) - PUNTO 2
	
	public List<Album> getPath(Album source, Album target, int x) {
		
		List<Album> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		this.bestScore = 0;
		parziale.add(source);
		
		
		recursive(parziale, target, x);
		
		
		return this.bestPath;
			
	}
	
	
	public void recursive(List<Album> parziale, Album target, int x) {
		
		Album current = parziale.get(parziale.size()-1);
		
		//condizione di termine
		if(current.equals(target)) {
			
			if(getScore(parziale) > this.bestScore) {
				
				this.bestPath = new ArrayList<>(parziale);
				this.bestScore = getScore(parziale);
			}
			
			return;
		}
		
		
		//ricorsione
		
		List<Album> succ = Graphs.successorListOf(graph, current);
		
		for(Album a: succ) {
			
			if(graph.getEdgeWeight(graph.getEdge(current, a))>x && !parziale.contains(a)) {
				
				parziale.add(a);
				recursive(parziale, target, x);
				parziale.remove(a);
			}
			
		}
		
	}
	
	public int getScore(List<Album> parziale) {
		
		int score = 0;
		
		for(Album a: parziale) {
			
			if(getBilancio(a) > getBilancio(parziale.get(0))){
				
				score+=1;
			}
		}
		
		return score;
	}
	
	
	//grafo - PUNTO 1
	public void buildGraph(int durata) {
		
		loadNodes(durata);
		
		//aggiungo i vertici
		
		Graphs.addAllVertices(graph, vertices);
		
		System.out.println(this.graph.vertexSet().size());
		
		//archi
		
		for(Album a: vertices) {
			for(Album aa: vertices) {
				
				if(a.getDurata()!=aa.getDurata() & a.getDurata()+aa.getDurata()>=4*durata & a.getDurata()<aa.getDurata()) {
					
					Graphs.addEdge(graph, a, aa, a.getDurata()+aa.getDurata());
				}
			}
		}
		
		System.out.println(this.graph.edgeSet().size());
	}
	
	public int getBilancio(Album a) {
		
		int bilancio = 0;
		Set<DefaultWeightedEdge> entranti = this.graph.incomingEdgesOf(a);
		Set<DefaultWeightedEdge> uscenti = this.graph.outgoingEdgesOf(a);
		
		for(DefaultWeightedEdge e: entranti) {
			bilancio += graph.getEdgeWeight(e);
		}
		
		for(DefaultWeightedEdge e: uscenti) {
			bilancio -= graph.getEdgeWeight(e);
		}
		
		return bilancio;
	}
	
	public List<BilancioAlbum> getAdiacenze(Album albumInput) {
		
		List<Album> succ = Graphs.successorListOf(graph, albumInput);
		
		for(Album a: succ) {
			bilancio.add(new BilancioAlbum(getBilancio(a), a));
		}
		
		Collections.sort(bilancio);
		
		return bilancio;
	}
	
	public void loadNodes(int durata) {
		if(vertices.isEmpty()) {
			vertices = dao.getVert(durata);
		}
	}
	
	public int getNumVert() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumEdge() {
		return this.graph.edgeSet().size();
	}

	public List<Album> getVertici() {
		// TODO Auto-generated method stub
		return vertices;
	}
}
