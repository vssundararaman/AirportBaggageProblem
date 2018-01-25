package com.deneverairport.RoutingProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.deneverairport.RoutingProblem.model.Edge;
import com.deneverairport.RoutingProblem.model.Graph;
import com.deneverairport.RoutingProblem.model.Vertex;

import junit.framework.TestCase;

/**
 * 
 * @author vssundararaman Problem 1: Airport Baggage
 * 
 *         Denver International Airport has decided to give an automated baggage
 *         system another shot. The hardware and tracking systems from the
 *         previous attempt are still in place, they just need a system to route
 *         the baggage. The system will route baggage checked, connecting, and
 *         terminating in Denver.
 * 
 *         You have been asked to implement a system that will route bags to
 *         their flights or the proper baggage claim. The input describes the
 *         airport conveyor system, the departing flights, and the bags to be
 *         routed. The output is the optimal routing to get bags to their
 *         destinations. Bags with a flight id of “ARRIVAL” are terminating in
 *         Denver are routed to Baggage Claim.
 * 
 *         Input: The input consists of several sections. The beginning of each
 *         section is marked by a line starting: “# Section:” Section 1: A
 *         weighted bi-directional graph describing the conveyor system. Format:
 *         <Node 1> <Node 2> <travel_time> Section 2: Departure list Format:
 *         <flight_id> <flight_gate> <destination> <flight_time> Section 3: Bag
 *         list Format: <bag_number> <entry_point> <flight_id>
 * 
 *         Output: The optimized route for each bag <Bag_Number> <point_1>
 *         <point_2> [<point_3>, …] : <total_travel_time>
 * 
 *         The output should be in the same order as the Bag list section of the
 *         input.
 * 
 *         Example Input: # Section: Conveyor System Concourse_A_Ticketing A5 5
 *         A5 BaggageClaim 5 A5 A10 4 A5 A1 6 A1 A2 1 A2 A3 1 A3 A4 1 A10 A9 1
 *         A9 A8 1 A8 A7 1 A7 A6 1 # Section: Departures UA10 A1 MIA 08:00 UA11
 *         A1 LAX 09:00 UA12 A1 JFK 09:45 UA13 A2 JFK 08:30 UA14 A2 JFK 09:45
 *         UA15 A2 JFK 10:00 UA16 A3 JFK 09:00 UA17 A4 MHT 09:15 UA18 A5 LAX
 *         10:15 # Section: Bags 0001 Concourse_A_Ticketing UA12 0002 A5 UA17
 *         0003 A2 UA10 0004 A8 UA18 0005 A7 ARRIVAL
 * 
 *         Example Output: 0001 Concourse_A_Ticketing A5 A1 : 11 0002 A5 A1 A2
 *         A3 A4 : 9 0003 A2 A1 : 1 0004 A8 A9 A10 A5 : 6 0005 A7 A8 A9 A10 A5
 *         BaggageClaim : 12
 *
 * 
 */
public class RoutingAlgorithmTest extends TestCase {

	private List<Vertex> nodes;
	private List<Edge> edges;
	private Graph graph;
	private RoutingAlgorithm routingAlgorithm;
	private Map<String, Vertex[]> bags;
	private Map<String, Vertex> departures;

	public void testExcute() {
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

		// Add Vertexes
		for (int i = 0; i <= 11; i++) {
			if (i == 0) {
				Vertex location = new Vertex("Concourse_A_Ticketing", "Concourse_A_Ticketing");
				nodes.add(location);
			} else {
				if (i == 11) {
					Vertex location = new Vertex("BaggageClaim", "BaggageClaim");
					nodes.add(location);
				} else {
					Vertex location = new Vertex("A" + i, "A" + i);
					nodes.add(location);
				}
			}
		}

		// Add Edges
		addLane("Edge_0", 0, 5, 5);
		addLane("Edge_1", 5, 11, 5);
		addLane("Edge_2", 5, 10, 4);
		addLane("Edge_3", 5, 1, 6);
		addLane("Edge_4", 1, 2, 1);
		addLane("Edge_5", 2, 3, 1);
		addLane("Edge_6", 3, 4, 1);
		addLane("Edge_7", 10, 9, 1);
		addLane("Edge_8", 9, 8, 1);
		addLane("Edge_9", 8, 7, 1);
		addLane("Edge_10", 7, 6, 1);

		// Lets check from location Loc_1 to Loc_10
		graph = new Graph(nodes, edges);
		routingAlgorithm = new RoutingAlgorithm(graph);

		departures = new HashMap<>();
		departures.put("UA11", nodes.get(1));
		departures.put("UA12", nodes.get(1));
		departures.put("UA13", nodes.get(2));
		departures.put("UA14", nodes.get(2));
		departures.put("UA10", nodes.get(1));
		departures.put("UA15", nodes.get(2));
		departures.put("UA16", nodes.get(3));
		departures.put("UA17", nodes.get(4));
		departures.put("UA18", nodes.get(5));
		departures.put("ARRIVAL", nodes.get(11));

		bags = new LinkedHashMap<>();
		bags.put("0001", new Vertex[] { nodes.get(0), departures.get("UA12") });
		bags.put("0002", new Vertex[] { nodes.get(5), departures.get("UA17") });
		bags.put("0003", new Vertex[] { nodes.get(2), departures.get("UA10") });
		bags.put("0004", new Vertex[] { nodes.get(8), departures.get("UA18") });
		bags.put("0005", new Vertex[] { nodes.get(7), departures.get("ARRIVAL") });

		for (Map.Entry<String, Vertex[]> bag : bags.entrySet()) {
			getPathDistance(bag);
		}

	}

	private void getPathDistance(Map.Entry<String, Vertex[]> bag) {
		String bagName = bag.getKey();
		Vertex[] vertexes = bag.getValue();
		routingAlgorithm.execute(vertexes[0]);
		Map<Integer, LinkedList<Vertex>> path = routingAlgorithm.getPath(vertexes[1]);

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (Map.Entry<Integer, LinkedList<Vertex>> pathDistance : path.entrySet()) {
			System.out.println(bagName + "\t" + pathDistance.getValue().toString() + "\t" + pathDistance.getKey());
		}
	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {

		// Bidirectional Edges
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);

		lane = new Edge(laneId, nodes.get(destLocNo), nodes.get(sourceLocNo), duration);
		edges.add(lane);
	}

}
