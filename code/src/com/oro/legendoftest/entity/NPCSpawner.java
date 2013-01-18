package com.oro.legendoftest.entity; 

import java.util.Random;

import com.oro.legendoftest.level.LevelManager;
import com.oro.legendoftest.level.Level;
import com.oro.legendoftest.Direction;
import com.oro.legendoftest.TerrainTypeCategory;
import com.oro.legendoftest.entity.NPCFactory;



public class NPCSpawner 
{
	Random random = new Random();
	LevelManager levelManager = LevelManager.INSTANCE;
	//****************************
	//Constructor
	//****************************
	public NPCSpawner()
	{
	
	}
	
	public void spawnMonster(int monsterNumber, Level level)
	{
		for(int a = 0; a < monsterNumber; a++)
		{		
			Monster monster = NPCFactory.INSTANCE.generateMonster();
			int x;
			int y;
			do
			{
				x = random.nextInt(level.LEVEL_WIDTH_IN_TILES);
				y = random.nextInt(level.LEVEL_HEIGHT_IN_TILES);
			}
			while(level.getTerrainAt(x, y).getCategory() != TerrainTypeCategory.FLOOR);
			
			monster.setPosition(x, y);
			level.AddToActorArray(monster);
		}
	}
}