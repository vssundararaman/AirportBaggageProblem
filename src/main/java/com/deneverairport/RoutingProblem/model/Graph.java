/**
 * 
 */
package com.deneverairport.RoutingProblem.model;

import java.util.List;

/**
 * @author vssundararaman
 *
 */
public class Graph {

	private final List<Vertex> vertexes;
	private final List<Edge> edges;

	public Graph(List<Vertex> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

	/**
	 * @return the vertexes
	 */
	public List<Vertex> getVertexes() {
		return vertexes;
	}

	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

}
