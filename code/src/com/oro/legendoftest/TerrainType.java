package com.oro.legendoftest;


public enum TerrainType
{
	EMPTY (false, TerrainTypeCategory.EMPTY),
	DIRT_FLOOR (true, TerrainTypeCategory.FLOOR),
	STONE_FLOOR (true, TerrainTypeCategory.FLOOR),
	SLUDGE (true, TerrainTypeCategory.FLOOR),
	WALL (false, TerrainTypeCategory.WALL),
	TERRAIN_TYPE_COUNT (false, TerrainTypeCategory.EMPTY);
	
	
	// ***********************************
	// Member variables
	// ***********************************
    private final boolean passable;

    private final TerrainTypeCategory category;
	// ***********************************
	// Constructor
	// ***********************************
    TerrainType(boolean passable, TerrainTypeCategory category) 
    {
        this.passable = passable;
        this.category = category;
    }
    
	// ***********************************
	// Public Member functions (methods)
	// ***********************************	
    public boolean passable() { return passable; }
    
   
    public TerrainTypeCategory getCategory()
    {
    	return category;
    }
    
    public boolean getPassable()
    {
    	return passable;
    }
    
}
