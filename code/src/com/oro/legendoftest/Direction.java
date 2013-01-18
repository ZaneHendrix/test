package com.oro.legendoftest;

import com.oro.legendoftest.utility.Constants;
import com.oro.legendoftest.utility.Vector2;

public enum Direction
{
	NONE (0f, 0f, false, false, false, false),
	N (0f, -1.0f, true, false, false, false),
	NE (Constants.ONE_OVER_SQRT_TWO, -Constants.ONE_OVER_SQRT_TWO, true, true, false, false),
	E (1.0f, 0, false, true, false, false),
	SE (Constants.ONE_OVER_SQRT_TWO, Constants.ONE_OVER_SQRT_TWO, false, true, true, false),
	S (0f, 1.0f, false, false, true, false),
	SW (-Constants.ONE_OVER_SQRT_TWO, Constants.ONE_OVER_SQRT_TWO, false, false, true, true),
	W (-1.0f, 0, false, false, false, true),
	NW (-Constants.ONE_OVER_SQRT_TWO, -Constants.ONE_OVER_SQRT_TWO, true, false, false, true);
	
	private Vector2 vector;
	private boolean hasNorth;
	private boolean hasEast;
	private boolean hasSouth;
	private boolean hasWest;
	
	
	
	private Direction(float xPart, float yPart, boolean hasNorth, boolean hasEast, boolean hasSouth, boolean hasWest)
	{
		this.vector = new Vector2(xPart, yPart);
		this.hasNorth = hasNorth;
		this.hasEast = hasEast;
		this.hasSouth = hasSouth;
		this.hasWest = hasWest;
	}
	
	public float getUpdatedX(float x, float distance)
	{
		return x + (vector.getX() * distance);
	}
	
	public float getUpdatedY(float y, float distance)
	{
		return y + (vector.getX() * distance);
	}
	
	public boolean hasNorthComponent()
	{
		return hasNorth;
	}
	
	public boolean hasEastComponent()
	{
		return hasEast;
	}
	
	public boolean hasSouthComponent()
	{
		return hasSouth;
	}
	
	public boolean hasWestComponent()
	{
		return hasWest;
	}
	public Vector2 getVector()
	{
		return vector;
	}
}
