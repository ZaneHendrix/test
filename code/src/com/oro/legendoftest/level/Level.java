package com.oro.legendoftest.level;

import java.util.ArrayList;
import java.util.Random;

//import com.oro.legendoftest.Direction;
import com.oro.legendoftest.Entity;
import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.TerrainType;
import com.oro.legendoftest.TerrainTypeCategory;
import com.oro.legendoftest.entity.Actor;
import com.oro.legendoftest.entity.Hero;
import com.oro.legendoftest.entity.NPCSpawner;
import com.oro.legendoftest.entity.NPCFactory;
import com.oro.legendoftest.usables.BackExit;
import com.oro.legendoftest.usables.ForwardExit;
import com.oro.legendoftest.utility.Vector2;


public class Level
{
	
	// ***********************************
	// Constants
	// ***********************************
	public static final int WIDTH_IN_CHUNKS = 3;
	public static final int HEIGHT_IN_CHUNKS = 3;
	public static final float TERRAIN_COLLISION_FUDGE = 0.15f;

	
	// ***********************************
	// Member variables
	// ***********************************

	public static Random random = new Random();
	
	public int LEVEL_WIDTH_IN_TILES  = (WIDTH_IN_CHUNKS  * LevelChunk.WIDTH_IN_TILES);
	public int LEVEL_HEIGHT_IN_TILES = (HEIGHT_IN_CHUNKS * LevelChunk.HEIGHT_IN_TILES);
	
	private int index;
	//tunnel_value determines the average tunnel length
	public int tunnel_Value = 6;

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Actor> actors = new ArrayList<Actor>();
	private LevelTile[][] tiles = new LevelTile[LEVEL_WIDTH_IN_TILES][LEVEL_HEIGHT_IN_TILES];
	
	int backTileX;
	int backTileY;
	int forwardTileX;
	int forwardTileY;
	
	private NPCSpawner spawner = new NPCSpawner();
	NPCFactory factory = NPCFactory.INSTANCE;
	
	
	Hero hero;
	
	
	public void setTileTerrain(int levelX, int levelY, TerrainType terrain)
	{
		int chunkX = 0;
		int chunkY = 0;
		
		chunkX = levelX % LevelChunk.WIDTH_IN_TILES;
		chunkY = levelY % LevelChunk.HEIGHT_IN_TILES;
		setTerrainAt(levelX, levelY, terrain);
		if(chunkY == (LevelChunk.HEIGHT_IN_TILES - 1) && levelY != (LEVEL_HEIGHT_IN_TILES - 1))
		{
			setTerrainAt(levelX,  levelY + 1, terrain);
		}
		else if(chunkY == (0) && levelY != (0))
		{
			setTerrainAt(levelX,  levelY - 1, terrain);
		}
		if(chunkX == (LevelChunk.WIDTH_IN_TILES - 1) && levelX != (LEVEL_WIDTH_IN_TILES - 1))
		{
			setTerrainAt(levelX + 1,  levelY, terrain);
		}
		else if(chunkX == (0) && levelX != (0))
		{
			setTerrainAt(levelX - 1,  levelY, terrain);
		}
		if(chunkY == (LevelChunk.HEIGHT_IN_TILES - 1) && levelY != (LEVEL_HEIGHT_IN_TILES - 1) && 
		   chunkX == (LevelChunk.WIDTH_IN_TILES - 1) && levelX != (LEVEL_WIDTH_IN_TILES - 1))
		{
			setTerrainAt(levelX + 1,  levelY + 1, terrain);
		}
		else if(chunkY == 0 && levelY != 0 && chunkX == 0 && levelX != 0 )
		{
			setTerrainAt(levelX - 1,  levelY - 1, terrain);
		}
		else if(chunkY == (LevelChunk.HEIGHT_IN_TILES - 1) && levelY != (LEVEL_HEIGHT_IN_TILES - 1) && 
				chunkX == 0 && levelX != 0)
		{
			setTerrainAt(levelX - 1,  levelY + 1, terrain);
		}
		else if(chunkY == 0 && levelY != 0 && 
				chunkX == (LevelChunk.WIDTH_IN_TILES - 1) && levelX != (LEVEL_WIDTH_IN_TILES - 1) )
		{
			setTerrainAt(levelX + 1,  levelY - 1, terrain);
		}
	}

	
	
	// ***********************************
	// Constructor
	// ***********************************
	public Level(int index, Hero hero)
	{
		this.index = index;
		this.hero = hero;
		initialize();
		placeEndpoints();
		generate();
		spawner.spawnMonster(monstersInLevel(), this);
	}
	
	// ***********************************
	// Public Member Functions (methods)
	// ***********************************
	public int getIndex()
	{
		return index;
	}
	
	
	public void enterForward()
	{
		// If we enter going forward, we arrive at the back exit.
		hero.setPosition(backTileX, backTileY);

	}
	
	public void enterBack()
	{
		// If we enter going backward, we arrive at the forward exit.
		hero.setPosition(forwardTileX, forwardTileY);
	}
	
	public void setTerrainAt(int x, int y, TerrainType terrain) 
	{
		tiles[x][y].setTerrainType(terrain);
	}

	public TerrainType getTerrainAt(int x, int y) 
	{
		return tiles[x][y].getTerrainType();
	}

	public IUsable getUsableAt(int x, int y)
	{
		return tiles[x][y].getUsable();
	}
	
	public ArrayList<Entity> getEntities() 
	{
		return entities;
	}

	public ArrayList<Actor> getActors() 
	{
		return actors;
	}

	public void AddToActorArray(Actor npc) 
	{
		actors.add(npc);
	}
	
	public void updateMovement(int timeDelta) 
	{
		// Updates actions that must be updated on a per-frame basis.
		for (int i = 0; i < actors.size(); ++i) 
		{
			updateActorMovement(actors.get(i), timeDelta);
		}
		updateActorMovement(hero, timeDelta);
	}
	
	public void setUsableAt(int x, int y, IUsable usable) 
	{
		tiles[x][y].setUsable(usable);
	}

	//Crash begins between versions 50 and 51, which changes this code
	public void updateSimulation(int timeDelta) 
	{
		for (int i = 0; i < actors.size(); ++i) 
		{
				actors.get(i).findPath();
		}
	}

	public void updateActorMovement(Actor actor, int timeDelta)
	{
		TerrainType locationTerrain = getTerrainAt((int)Math.round(actor.getX()), (int)Math.round(actor.getY()));
		Vector2 direction = actor.getMovingDirection();
		float newX = actor.getX() + (direction.getX() * timeDelta*actor.getSpeed(locationTerrain));
		float newY = actor.getY() + (direction.getY() * timeDelta*actor.getSpeed(locationTerrain));
		
		for (int x = (int)Math.floor(newX + TERRAIN_COLLISION_FUDGE); 
				 x <= (int)Math.ceil(newX - TERRAIN_COLLISION_FUDGE); ++x) 
		{
			for (int y = (int)Math.floor(newY + TERRAIN_COLLISION_FUDGE); 
					 y <= (int)Math.ceil(newY - TERRAIN_COLLISION_FUDGE); ++y)
			{
 				if ((x < 0) || (x > LEVEL_WIDTH_IN_TILES - 1) ||
 						(y < 0) || (y > LEVEL_HEIGHT_IN_TILES - 1) ||
 						getTerrainAt(x, y).getPassable() != true)
				{
					return; // Can't move to destination.
				}
			}
		}		
		actor.setPosition(newX,  newY);
	}

	
	
	// ***********************************
	// Private Member Functions
	// ***********************************
	private void initialize()
	{
		for (int x = 0; x < LEVEL_WIDTH_IN_TILES; ++x) 
		{
			for (int y = 0; y < LEVEL_HEIGHT_IN_TILES; ++y) 
			{
				tiles[x][y] = new LevelTile(x, y);
			}
		}
	}
	public boolean testPath (int startX, int startY, int endX, int endY)
	{
		boolean isOK = true;
		isOK = isOK && (getTerrainAt(endX, endY).getCategory() == TerrainTypeCategory.WALL);
		
		int testX = endX - 1;
		int testY = endY;
		isOK = isOK && (((testX == startX) && (testY == startY)) || (getTerrainAt(testX, testY).getCategory() 
																				!= TerrainTypeCategory.FLOOR));
		
		testX = endX + 1;
		testY = endY;
		isOK = isOK && (((testX == startX) && (testY == startY)) || (getTerrainAt(testX, testY).getCategory() 
																			    != TerrainTypeCategory.FLOOR));
		
		testX = endX;
		testY = endY - 1;
		isOK = isOK && (((testX == startX) && (testY == startY)) || (getTerrainAt(testX, testY).getCategory() 
																				!= TerrainTypeCategory.FLOOR));
		
		testX = endX;
		testY = endY + 1;
		isOK = isOK && (((testX == startX) && (testY == startY)) || (getTerrainAt(testX, testY).getCategory() 
																				!= TerrainTypeCategory.FLOOR));
		
		isOK = isOK && ((startX != endX) || (((endX % LevelChunk.WIDTH_IN_TILES) != (LevelChunk.WIDTH_IN_TILES - 1)) 
									     && (( endX % LevelChunk.WIDTH_IN_TILES) != 0)));
		
		isOK = isOK && ((startY != endY) || (((endY % LevelChunk.HEIGHT_IN_TILES) != (LevelChunk.HEIGHT_IN_TILES - 1)) 
			     && (( endY % LevelChunk.HEIGHT_IN_TILES) != 0)));
		return isOK;
	}
	public void removeDeadEnd (int startX, int startY)
	{
		int count = 0;
		if(getTerrainAt(startX, startY).getCategory() == TerrainTypeCategory.FLOOR)
		{
			int testX = startX + 1;
			int testY = startY;
			if((getTerrainAt(testX, testY).getCategory() != TerrainTypeCategory.FLOOR))
			{
				count++;
			}
			testX = startX - 1;
			testY = startY;
			if((getTerrainAt(testX, testY).getCategory() != TerrainTypeCategory.FLOOR))
			{
				count++;
			}
			testX = startX;
			testY = startY + 1;
			if((getTerrainAt(testX, testY).getCategory() != TerrainTypeCategory.FLOOR))
			{
				count++;
			}
			testX = startX;
			testY = startY - 1;
			if((getTerrainAt(testX, testY).getCategory() != TerrainTypeCategory.FLOOR))
			{
				count++;
			}
			if(count >= 3)
			{
				int remove = random.nextInt(3);
				for (int i = 0; i < 3; i++)
				{
					int endX = startX;
					int endY = startY;
					switch ((remove + i) % 4)
					{
					case 0: endX = startX + 1;
							endY = startY;
							break;
					case 1: endX = startX - 1;
							endY = startY;
							break;
					case 2: endX = startX;
							endY = startY + 1;
							break;
					case 3: endX = startX;
							endY = startY - 1;
							break;
					}
					if(getTerrainAt(endX,endY) == TerrainType.WALL)
					{
						setTileTerrain(endX, endY, floorStyle());
						break;
					}
				}	
			}
			
		}
	}
	private void placeEndpoints()
	{
		int backChunkX, backChunkY, forwardChunkX, forwardChunkY;
		if (random.nextBoolean())
		{
			backChunkX = 0;
			backTileX = (random.nextInt((LevelChunk.WIDTH_IN_TILES / 2) - 1) + 1);
			forwardChunkX = WIDTH_IN_CHUNKS - 1;
			forwardTileX = (random.nextInt((LevelChunk.WIDTH_IN_TILES / 2) - 1) + (LevelChunk.WIDTH_IN_TILES * forwardChunkX) + (LevelChunk.WIDTH_IN_TILES / 2));
		}
		else
		{
			backChunkX = WIDTH_IN_CHUNKS - 1;
			backTileX = (random.nextInt((LevelChunk.WIDTH_IN_TILES / 2) - 1) + (LevelChunk.WIDTH_IN_TILES * backChunkX) + (LevelChunk.WIDTH_IN_TILES / 2));
			forwardChunkX = 0;
			forwardTileX = (random.nextInt((LevelChunk.WIDTH_IN_TILES / 2) - 1) + 1);
		}
		if (random.nextBoolean())
		{
			backChunkY = 0;
			backTileY = (random.nextInt((LevelChunk.HEIGHT_IN_TILES / 2) - 1) + 1);
			forwardChunkY = HEIGHT_IN_CHUNKS - 1;
			forwardTileY = (random.nextInt((LevelChunk.HEIGHT_IN_TILES / 2) - 1) + (LevelChunk.HEIGHT_IN_TILES * forwardChunkY) + (LevelChunk.HEIGHT_IN_TILES / 2));
		}
		else
		{
			backChunkY = HEIGHT_IN_CHUNKS - 1;
			backTileY = (random.nextInt((LevelChunk.HEIGHT_IN_TILES / 2) - 1) + (LevelChunk.HEIGHT_IN_TILES * backChunkY) + (LevelChunk.HEIGHT_IN_TILES / 2));
			forwardChunkY = 0;
			forwardTileY = (random.nextInt((LevelChunk.HEIGHT_IN_TILES / 2) - 1) + 1);
		}


		setUsableAt(backTileX, backTileY, new BackExit());
		setUsableAt(forwardTileX, forwardTileY, new ForwardExit());
	}
	
	//temporary code to test varying floor style
	private TerrainType floorStyle()
	{
		int test = random.nextInt(16);
		if(test <= 2)
		{
			return TerrainType.DIRT_FLOOR;
		}
		else if(test == 3)
		{
			return TerrainType.SLUDGE;
		}
		else
		{
			return TerrainType.STONE_FLOOR;
		}
	}
	public int monstersInLevel()
	{
		return 30;
	}
	private void generate()
	{
		int tunnel_Length = 0;
		TerrainType terrain = TerrainType.EMPTY;
		for (int levelX = 0; levelX < LEVEL_WIDTH_IN_TILES; levelX++)
		{
			for (int levelY = 0; levelY < LEVEL_HEIGHT_IN_TILES; levelY++)
			{	
				if ((levelX == LEVEL_WIDTH_IN_TILES - 1) || (levelX == 0) ||
					(levelY == LEVEL_HEIGHT_IN_TILES - 1) || (levelY == 0))
				{
					terrain = TerrainType.EMPTY; 
				}
				else
				{
					terrain = TerrainType.WALL;
				}
				setTileTerrain(levelX, levelY, terrain);
			}
		}

			int startX;
			int startY;
			int endX;
			int endY;
				startX = backTileX;
				startY = backTileY;
				setTileTerrain(startX, startY, TerrainType.STONE_FLOOR);
			int oldDirection = random.nextInt(3);	
		for (int x = 0; x < 50000; x++)
			{
				int startDirection;
				int endDirection = 0;
				int T = Math.max(tunnel_Value - tunnel_Length, 1);
				int NT = Math.max(tunnel_Length - tunnel_Value, 1);
				int weight = random.nextInt(T + (NT * 3));
				

				if(weight < T)
				{
					startDirection = oldDirection;
				}
				else
				{
					startDirection = (oldDirection + (((weight - T)+(NT-1)/NT)) % 4);
				}
				boolean found = false;
				for (int i = 0; i < 3; i++)
				{
					switch ((startDirection + i) % 4)
					{
					case 0: endX = startX + 1;
							endY = startY;
							endDirection = 0;
							break;
					case 1: endX = startX - 1;
							endY = startY;
							endDirection = 1;
							break;
					case 2: endX = startX;
							endY = startY + 1;
							endDirection = 2;
							break;
					case 3: endX = startX;
							endY = startY - 1;
							endDirection = 3;
							break;
					default: endX = startX;
							 endY = startY;
					}
					if (testPath (startX, startY, endX, endY) == true)
					{ 
						setTileTerrain(endX, endY, floorStyle());
						startX = endX;
						startY = endY;
						found = true;
						if(endDirection == oldDirection)
						{
							tunnel_Length++;
						}
						else
						{
							tunnel_Length = 1;
							oldDirection = endDirection;
						}
						break;
					}
				}
				if (found == false)
				{
					do
					{
					startX = random.nextInt(LEVEL_WIDTH_IN_TILES - 2) + 1;
					startY = random.nextInt(LEVEL_HEIGHT_IN_TILES - 2) + 1;
					} while (getTerrainAt(startX, startY).getCategory() != TerrainTypeCategory.FLOOR);		
				}
			}
		endX = forwardTileX;
		endY = forwardTileY;
		setTileTerrain(endX, endY, TerrainType.STONE_FLOOR);
		for (int levelX = 0; levelX < LEVEL_WIDTH_IN_TILES; levelX += random.nextInt(3))
		{
			for (int levelY = 0;  levelY < LEVEL_HEIGHT_IN_TILES; levelY += random.nextInt(3))
			{
				removeDeadEnd (levelX, levelY);
			}
		}
		for (int levelX = 0; levelX < LEVEL_WIDTH_IN_TILES; levelX++)
		{
			for (int levelY = 0; levelY < LEVEL_HEIGHT_IN_TILES; levelY++)
			{
				if ((levelX == LEVEL_WIDTH_IN_TILES - 1)  || (levelX == 0) ||
					(levelY == LEVEL_HEIGHT_IN_TILES - 1) || (levelY == 0))
				{
					setTileTerrain(levelX, levelY, TerrainType.WALL); 
				}
			}
		}
	}
}