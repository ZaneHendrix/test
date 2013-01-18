package com.oro.legendoftest.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import org.malkyne.game2d.Sprite;
import org.malkyne.game2d.SpriteManager;

import com.oro.legendoftest.Entity;
import com.oro.legendoftest.EntityResourceId;
import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.TerrainType;
import com.oro.legendoftest.entity.Actor;
import com.oro.legendoftest.entity.Hero;
import com.oro.legendoftest.level.Level;
import com.oro.legendoftest.level.LevelChunk;
import com.oro.legendoftest.level.LevelManager;
import com.oro.legendoftest.usables.BackExit;
import com.oro.legendoftest.usables.ForwardExit;

// View of our Dungeon
// For displaying a dungeon.
public class LevelSceneView extends Canvas
{
	static
	{
		// Uncomment this to see render debug messages.
		// System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.transaccel", "True");
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.d3d", "false"); //default on windows
		// System.setProperty("sun.java2d.ddforcevram", "true");
	}

	// *************************************
	// Constants
	// *************************************
	private static final long serialVersionUID = 0L;
	public static final int PIXELS_PER_UNIT = 32;
	
	// *************************************
	// Member variables
	// *************************************
	
	// Sprite Catalogs.
	private EnumMap<TerrainType, Sprite> terrainSprites = new EnumMap<TerrainType, Sprite>(TerrainType.class);
	private HashMap<Class<? extends IUsable>, Sprite> usableSprites = new HashMap<Class<? extends IUsable>, Sprite>();
	private EnumMap<EntityResourceId, Sprite> entitySprites = new EnumMap<EntityResourceId, Sprite>(EntityResourceId.class);
	
	// Rendering stuff.
	private BufferStrategy bufferStrategy;
	
	// Special entities.
	private Hero hero;
	
	// *************************************
	// Constructor
	// *************************************
	public LevelSceneView(Hero hero)
	{
		// We are doing our own painting, so turn off auto-repaint.
		this.setIgnoreRepaint(true);
		
		this.hero = hero;
		
		SpriteManager spriteMgr = SpriteManager.INSTANCE;
		
		// Fill our sprite catalogs with the sprites we need.
		terrainSprites.put(TerrainType.EMPTY, null);
		terrainSprites.put(TerrainType.DIRT_FLOOR, spriteMgr.getSprite("dirt_floor"));
		terrainSprites.put(TerrainType.STONE_FLOOR, spriteMgr.getSprite("stone_floor"));
		terrainSprites.put(TerrainType.SLUDGE, spriteMgr.getSprite("sludge"));
		terrainSprites.put(TerrainType.WALL, spriteMgr.getSprite("wall"));
		
		usableSprites.put(BackExit.class, spriteMgr.getSprite("back"));
		usableSprites.put(ForwardExit.class, spriteMgr.getSprite("forward"));
		
		entitySprites.put(EntityResourceId.ENTITY_PLACEHOLDER, spriteMgr.getSprite("entity_placeholder"));
		entitySprites.put(EntityResourceId.ACTOR_PLACEHOLDER, spriteMgr.getSprite("actor_placeholder"));
		entitySprites.put(EntityResourceId.HERO, spriteMgr.getSprite("avatar"));
		entitySprites.put(EntityResourceId.WORM, spriteMgr.getSprite("worm"));
		entitySprites.put(EntityResourceId.EYE, spriteMgr.getSprite("eye"));
		
	}
	
	// **************************************
	// Public Member functions (methods)
	// *************************************
	public void initBufferStrategy()
	{
		// Double Buffering
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
	}
	
	public void render()
	{
        // Get the graphics context for our backBuffer.
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		// Clear the backbuffer with black.
		g.setPaint(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		renderScene(g);
		
		// Release the back buffer graphics object.
		g.dispose();

		// Draw the backbuffer.
		bufferStrategy.show();

		// Sync display refresh.
		Toolkit.getDefaultToolkit().sync();
		
	}
		
	// **************************************
	// Private Member functions (methods)
	// **************************************	
	private void renderScene(Graphics2D g)
	{ 
		int screenX, screenY;
        Sprite sprite;
        IUsable usable;

        int chunkX = (int) Math.floor(Math.round(hero.getX()) / LevelChunk.WIDTH_IN_TILES);
        int chunkY = (int) Math.floor(Math.round(hero.getY()) / LevelChunk.HEIGHT_IN_TILES);
		
        int offsetY = chunkY * LevelChunk.HEIGHT_IN_TILES;
        int offsetX = chunkX * LevelChunk.WIDTH_IN_TILES;
        // Grab the current level.
        Level level = LevelManager.INSTANCE.getCurrentLevel();
        
        
        // Iterate through the tiles in the visible chunk, and draw whatever is there.
        for (int x = 0; x < LevelChunk.WIDTH_IN_TILES; ++x)
        {
        	int levelX = x + offsetX;
        	screenX = x * PIXELS_PER_UNIT;
        	for (int y = 0; y < LevelChunk.HEIGHT_IN_TILES; ++y)
        	{
        		screenY = y * PIXELS_PER_UNIT;
        		int levelY = y + offsetY;
        		// First, draw the terrain sprite.
        		sprite = terrainSprites.get(level.getTerrainAt(levelX , levelY));
        		if (sprite != null)
        		{
        			sprite.draw(g, screenX, screenY);
        		}
        		
        		// Then, draw the sprite for any usable on this tile.
        		usable = level.getUsableAt(levelX, levelY);
        		if (usable != null)
        		{
        			sprite = usableSprites.get(usable.getClass());
        			if (sprite != null)
        			{
        				sprite.draw(g, screenX, screenY);
        			}
        			else
        			{
        				System.err.println("Sprite for " + usable.getClass().toString() + " not found.");
        			}
        		}
        	}
        }

        // Iterate through the static entities, and draw them, too.
        ArrayList<Entity> entities = level.getEntities();
        for (int i = 0; i < entities.size(); ++i)
        {
        	renderEntity(g, entities.get(i), offsetX, offsetY);
        }
        
        // Iterate through the actors, and draw them.
        ArrayList<Actor> actors = level.getActors();
        for (int i = 0; i < actors.size(); ++i)
        {
        	renderEntity(g, actors.get(i), offsetX, offsetY);
        }
        
        // Last but not least, draw our hero.

        renderEntity(g, hero, offsetX, offsetY);
	}
	
	void renderEntity(Graphics2D g, Entity entity, int offsetX, int offsetY)
	{
		entitySprites.get(entity.getEntityResourceId()).draw(g,
        		Math.round((entity.getX() - offsetX) * PIXELS_PER_UNIT),
        		Math.round((entity.getY() - offsetY) * PIXELS_PER_UNIT));
	}

}
