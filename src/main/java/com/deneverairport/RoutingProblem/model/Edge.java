/**
 * 
 */
package com.deneverairport.RoutingProblem.model;

/**
 * @author vssundararaman
 *
 */
public class Edge {
	
	private final String id;
	private final Vertex source;
	private final Vertex destination;
	private final int weight;
	
	public Edge(String id, Vertex source, Vertex destination, int weight) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the source
	 */
	public Vertex getSource() {
		return source;
	}

	/**
	 * @return the destination
	 */
	public Vertex getDestination() {
		return destination;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return source + " " + destination;
	}
}
