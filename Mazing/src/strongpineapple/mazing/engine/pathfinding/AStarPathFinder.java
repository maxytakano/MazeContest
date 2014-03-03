package strongpineapple.mazing.engine.pathfinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import strongpineapple.mazing.engine.utils.Point;

public class AStarPathFinder implements PathFinder {
	
	private TileMap map;
	
	public AStarPathFinder(TileMap map) {
		this.map = map;
	}
	
	@Override
	/*
	 * @return A list of points representing the path from the source to the destination, with the first point being source and the last point being destination
	 * 
	 */
	public List<Point> findPath(Point source, Point destination) {
		boolean[][] nodeMap = new boolean[map.getWidth()][map.getHeight()];
		
		// generateNodes()
		for(int x = 0; x < map.getWidth(); x++)
		{
			for(int y = 0; y < map.getHeight(); y++)
			{
				//if a pixel at (w,h) is blocked, possibly generate adjacent nodes
				if(map.isBlocked(x, y))
				{
					if(x+1 < map.getWidth())
					{
						if(y+1 < map.getHeight())
						{
							if(!map.isBlocked(x+1, y) && !map.isBlocked(x+1, y+1) && !map.isBlocked(x, y+1))
							{
								// Generate node at bottom right diag
								nodeMap[x + 1][y + 1] = true;
							}
						}
						if(y - 1 >= 0)
						{
							if(!map.isBlocked(x, y-1) && !map.isBlocked(x+1, y-1) && !map.isBlocked(x+1,y))
							{
								//gen node at top right diag
								nodeMap[x + 1][y - 1] = true;
							}
						}
					}
					if(x-1 >= 0)
					{
						if(y+1 < map.getHeight())
						{
							if(!map.isBlocked(x-1, y) && !map.isBlocked(x-1, y+1) && !map.isBlocked(x, y+1))
							{
								//gen node at bottom left
								nodeMap[x - 1][y + 1] = true;
							}
						}
						if(y-1 >= 0)
						{
							if(!map.isBlocked(x, y-1) && !map.isBlocked(x-1,y-1) && !map.isBlocked(x-1,y))
							{
								//gen node at top left
								nodeMap[x - 1][y - 1] = true;
							}
						}
					}
				}
			}
		}
		
		// Convert nodeMap to list of nodes
		
		List<Node> nodelist = new ArrayList<Node>();
		
		// Add start and end nodes manually
		nodeMap[source.getX()][source.getY()] = false;
		nodeMap[destination.getX()][destination.getY()] = false;
		
		Node sourceNode = new Node(source.getX(), source.getY());
		Node destNode = new Node(destination.getX(), destination.getY());
		
		nodelist.add(sourceNode);
		nodelist.add(destNode);
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if (nodeMap[x][y]) {
					nodelist.add(new Node(x, y));
				}
			}
		}

		// loops through Nodes and checks nodes' neighbors
		for (int i = 0; i < nodelist.size(); i++)
		{
			for (int j = i + 1; j < nodelist.size(); j++)
			{
				Node n1 = nodelist.get(i);
				Node n2 = nodelist.get(j);
				if (areNeighbors(n1, n2))
				{
					n1.neighbors.add(n2);
					n2.neighbors.add(n1);
				}
			}
		}
		
		
		return traverseGraph(sourceNode, destNode);
	}

	private List<Point> traverseGraph(Node start, Node end) {
		List<Node> closedSet = new ArrayList<Node>();
		PriorityQueue<Node> openSet = new PriorityQueue<Node>(10, new NodeComparer());

		openSet.add(start);

		while (!openSet.isEmpty()) {
			Node currentNode = openSet.remove();
			if (currentNode == end) {
				return constructPath(end);
			}

			closedSet.add(currentNode);

			for (Node neighbor : currentNode.neighbors) {
				if (closedSet.contains(neighbor))
					continue;
				
				// Calculate G score of neighbor along the path originating from currentNode
				double tentativeGScore = currentNode.gScore + Point.distance(currentNode.getPoint(), neighbor.getPoint());
				
				// If the new G score is less than the previous one (or if this node hasn't been evaluated yet), update the scores and parent
				if (!openSet.contains(neighbor) || tentativeGScore < neighbor.gScore) {
					neighbor.gScore = tentativeGScore;
					neighbor.fScore = neighbor.gScore + Point.distance(neighbor.getPoint(), end.getPoint());
					neighbor.parent = currentNode;
					
					if (!openSet.contains(neighbor))
					{
						openSet.add(neighbor);
					}
				}
			}
		}

		return null;
	}
	
	private List<Point> constructPath(Node end)
	{
		List<Point> path = new ArrayList<Point>();
		Node currentNode = end;
		while (currentNode.parent != null)
		{
			path.add(0, new Point(currentNode.x, currentNode.y));
			currentNode = currentNode.parent;
		}
		
		// Add the start node
		path.add(0, new Point(currentNode.x, currentNode.y));
		return path;
	}
	
	/*
	 * Returns true if n1 and n2 are neighbors, else false.
	 */
	private boolean areNeighbors(Node n1, Node n2)
	{
		Point p1 = new Point(n1.x, n1.y);
		Point p2 = new Point(n2.x, n2.y);
		
		// Strategy: Draw a 1x1 "line" (rectangle) from p1 to p2 and detect blocks along the path

		// If line is longer than tall, iterate over the integer x values on the line, else iterate over y values
		if (Math.abs(p2.getX() - p1.getX()) > Math.abs(p2.getY() - p1.getY()))
		{
			// Let p1 be the left point
			if (p1.getX() > p2.getX())
			{
				Point temp = p1;
				p1 = p2;
				p2 = temp;
			}
			
			// Change in y per increase in x
			float slope = (float)(p2.getY() - p1.getY()) / (float)(p2.getX() - p1.getX());
			
			float y = p1.getY();
			for (int x = p1.getX(); x < p2.getX(); x++)
			{
				// Line falls between two blocks; check both
				boolean bottomBlockExists = map.isBlocked(x, (int) y);
				boolean topBlockExists = map.isBlocked(x, (int) Math.ceil(y));
				if (bottomBlockExists || topBlockExists)
					return false;
				y += slope;
			}
		}
		else if (Math.abs(p2.getX() - p1.getX()) < Math.abs(p2.getY() - p1.getY()))
		{
			// Let p1 be the upper point
			if (p1.getY() > p2.getY())
			{
				Point temp = p1;
				p1 = p2;
				p2 = temp;
			}
			
			// Change in x per increase in y
			float slope = (float)(p1.getX() - p2.getX()) / (float)(p1.getY() - p2.getY());
			
			float x = p1.getX();
			for (int y = p1.getY(); y < p2.getY(); y++)
			{
				// Line falls between two blocks; check both
				boolean leftBlockExists = map.isBlocked((int) x, y);
				boolean rightBlockExists = map.isBlocked((int) Math.ceil(x), y);
				if (leftBlockExists || rightBlockExists)
					return false;
				x += slope;
			}
		}
		else
		{
			if (p1.getX() > p2.getX())
			{
				Point temp = p1;
				p1 = p2;
				p2 = temp;
			}
			
			// Change in y per increase in x; either 1 or -1
			int slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
			
			int y = p1.getY();
			for (int x = p1.getX(); x < p2.getX(); x++)
			{
				if (map.isBlocked(x + 1, y))
				{
					return false;
				}
				
				if (map.isBlocked(x, y + slope))
				{
					return false;
				}
				
				y += slope;
			}
		}
		
		return true;
	}
	
	private class Node {
		public int x;
		public int y;
		public List<Node> neighbors = new ArrayList<Node>();
		
		// Values for shortest path
		public Node parent;
		
		/**
		 * Distance traveled from start.
		 */
		public double gScore;
		
		/**
		 * The overall A* priority score of this node: distance traveled from start + distance estimate to end.
		 */
		public double fScore;
		
		public Node(int x, int y)
		{
			Node.this.x = x;
			Node.this.y = y;
		}
		
		public Point getPoint()
		{
			return new Point(x, y);
		}
	}
	
	/*
	 * A comparator that determines the order in which nodes should be explored.
	 */
	private class NodeComparer implements Comparator<Node> {
		@Override
		public int compare(Node n1, Node n2) {
			// n1 is higher priority (negative) if n1.fscore < n2.fscore
			return (int) (n1.fScore - n2.fScore);
		}
		
	}
}
