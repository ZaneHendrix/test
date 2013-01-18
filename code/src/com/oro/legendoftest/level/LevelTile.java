package com.oro.legendoftest.level;

import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.TerrainType;

public class LevelTile
{
	// **********************************
	// Member variables
	// **********************************
	private int xPos;
	private int yPos;
	
	TerrainType terrain = TerrainType.EMPTY;
	IUsable usable = null;
	
	// ***********************************
	// Constructor
	// ***********************************
	public LevelTile(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	// **********************************
	// Public Member Functions (Methods)
	// **********************************
	public void setTerrainType(TerrainType terrainType)
	{
		terrain = terrainType;
	}
	
	public void setUsable(IUsable usable)
	{
		this.usable = usable;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public TerrainType getTerrainType()
	{
		return terrain;
	}

	public IUsable getUsable()
	{
		return usable;
	}
	
	public boolean isPassable()
	{
		return terrain.passable();
	}
}
