package com.oro.legendoftest.level;

public class LevelChunk {
	// *****************************
	// Constants
	// *****************************
	public static final int WIDTH_IN_TILES = 24;
	public static final int HEIGHT_IN_TILES = 20;

	// *****************************
	// Member variables
	// *****************************
	LevelManager levelManager = LevelManager.INSTANCE;
	
	private int xPos;
	private int yPos;



	// ***********************************
	// Constructor
	// ***********************************
	public LevelChunk(int xPos, int yPos) 
	{
		this.xPos = xPos;
		this.yPos = yPos;

		// Create empty LevelTiles for each tile position in the LevelChunk.

	}

	// ***********************************
	// Public Member functions (methods)
	// ***********************************
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

}
