package com.oro.legendoftest.utility;

import java.util.Vector;

import com.oro.legendoftest.level.Level;
import com.oro.legendoftest.level.LevelChunk;
import com.oro.legendoftest.level.LevelManager;

public class Pathing 
{
	//********************************************
	//Constructor
	//********************************************
	public Pathing()
	{
	}
		
	public class Node
	{
		public int X;
		public int Y;
		public boolean walkable = LevelManager.INSTANCE.getCurrentLevel().getTerrainAt(X, Y).getPassable();
		public int index;
		
		//A* pathing values
		public boolean open;
		public boolean closed;
		public Node cameFrom;
		
		public float g;
		public float h;
		public float f;
		
		public int depth;
		
		public int Compare(Node node1, Node node2)
        {
            if (node1.f < node2.f)
            {
            	return -1;
            }
            else if (node1.f > node2.f)
            {
            	return 1;
            }
            return 0;
        }
	}	
	public int xMin = 0;
	public int xMax = LevelChunk.WIDTH_IN_TILES * Level.WIDTH_IN_CHUNKS;
	public int yMin = 0;
	public int yMax = LevelChunk.HEIGHT_IN_TILES * Level.HEIGHT_IN_CHUNKS;
	
	private int widthNodes = LevelChunk.WIDTH_IN_TILES * Level.WIDTH_IN_CHUNKS;
	private int heightNodes = LevelChunk.HEIGHT_IN_TILES * Level.HEIGHT_IN_CHUNKS;
	private Node[][] mapNodes = null;
	private float xSpacing = 1;
	private float ySpacing = 1;
	
	/*private float NodeDistance(Node n1, Node n2)
	{
		return (float)Math.sqrt((n2.X - n1.X)*(n2.X - n1.X) + (n2.Y - n1.Y)*(n2.Y - n1.Y));
	}*/
	private float NodeDistanceManhattan(Node n1, Node n2)
	{
		return (n2.X - n1.X) + (n2.Y - n1.Y);	
	}
	
	private int findNearestX(float xLoc)
	{
		return Math.round((xLoc - xMin) / xSpacing);
	}
	
	private int findNearestY(float yLoc)
	{
		return Math.round((yLoc - yMin) / ySpacing);
	}
	
	private Vector<Node> openQueue = new Vector<Node>();

	
	
	private void resetPathing()
	{
		for (int y = 0; y < heightNodes; ++y)
		{
			for (int x = 0; x < widthNodes; ++x)
			{
				Node node = mapNodes[x][y];
				node.open = false;
				node.closed = false;
				node.cameFrom = null;
				node.g = 0;
				node.h = 0;
				node.f = 0;
				node.depth = 0;
			}
		}
		openQueue.clear();
	}
	
	public void findPath(float objX, float objY, float goalX, float goalY, Node[] finalPathBuff)
	{
		boolean tenativeBetter;
		int x;
		int y;
		int startX = findNearestX(objX);
		int startY = findNearestY(objY);
		int endX = findNearestX(goalX);
		int endY = findNearestY(goalY);
		
		resetPathing();
		
		Node startNode = mapNodes[startX][startY];
		Node endNode = mapNodes[endX][endY];
		
		startNode.g = 0;
		startNode.h = NodeDistanceManhattan(startNode, endNode);
		startNode.f = startNode.h;
		
	
		openQueue.add(startNode);
		startNode.open = true;
		while (!openQueue.isEmpty())
		{
			Node testNode = openQueue.remove(openQueue.size() - 1);			
			if (testNode.equals(endNode))
			{
				ReconstructPath(endNode.cameFrom, finalPathBuff);
				return;
			}
	
			testNode.open = false;
			testNode.closed = true;
			
			int left = Math.max(testNode.X - 1, 0);
			int right = Math.min(testNode.X + 2, widthNodes);
			int top = Math.max(testNode.Y - 1, 0);
			int bottom = Math.min(testNode.Y + 2, heightNodes);
			
			for (y = top; y < bottom; ++y)
			{
				for (x = left; x < right; ++x)
				{
					if ((x == testNode.X) && (y == testNode.Y)) 
					{
						continue;
					}
				
					Node neighborNode = mapNodes[x][y];
					
					if (!neighborNode.walkable) 
					{
						continue;
					}
					
					if (neighborNode.closed)
					{
						continue;
					}
					
					float tenativeG = testNode.g + xSpacing;
					
					if (!neighborNode.open)
					{
						neighborNode.open = true;
						openQueue.add(neighborNode);
						tenativeBetter = true;
					}
					else if (tenativeG < neighborNode.g)
					{
						tenativeBetter = true;	
					}
					else
					{
						tenativeBetter = false;
					}
					
					if (tenativeBetter)
					{
						neighborNode.cameFrom = testNode;
						neighborNode.depth = testNode.depth + 1;
						neighborNode.g = tenativeG;
						neighborNode.h = NodeDistanceManhattan(neighborNode, endNode);
						neighborNode.f = neighborNode.g + neighborNode.h;
						openQueue.remove(neighborNode);
						openQueue.add(neighborNode);
					}
				}
			}
		}
	}
	private void ReconstructPath(Node currentNode, Node[] finalPathBuff)
	{
		Node node = currentNode;
		
		for (int i = currentNode.depth; i >= 0; --i)
		{
			finalPathBuff[i] = node;
			node = node.cameFrom;
		}
	}
	
	
}